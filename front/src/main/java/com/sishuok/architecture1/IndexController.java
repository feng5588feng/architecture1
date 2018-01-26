package com.sishuok.architecture1;

import com.sishuok.architecture1.cartmgr.service.ICartService;
import com.sishuok.architecture1.cartmgr.vo.CartModel;
import com.sishuok.architecture1.cartmgr.vo.CartQueryModel;
import com.sishuok.architecture1.goodsmgr.service.IGoodsService;
import com.sishuok.architecture1.goodsmgr.vo.GoodsModel;
import com.sishuok.architecture1.goodsmgr.vo.GoodsQueryModel;
import com.sishuok.architecture1.ordermgr.service.IOrderDetailService;
import com.sishuok.architecture1.ordermgr.service.IOrderService;
import com.sishuok.architecture1.ordermgr.vo.OrderDetailModel;
import com.sishuok.architecture1.ordermgr.vo.OrderModel;
import com.sishuok.architecture1.ordermgr.vo.OrderQueryModel;
import com.sishuok.architecture1.storemgr.service.IStoreService;
import com.sishuok.architecture1.storemgr.vo.StoreModel;
import com.sishuok.pageutil.Page;
import com.sishuok.util.format.DateFormatHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class IndexController {

	@Autowired
	private IGoodsService igs = null;
	@Autowired
	private ICartService ics = null;
	@Autowired
	private IOrderService ios = null;
	@Autowired
	private IOrderDetailService iods = null;
	@Autowired
	private IStoreService iss = null;


	@RequestMapping(value="/toIndex",method=RequestMethod.GET)
	public String toIndex(Model model){

		GoodsQueryModel gqm = new GoodsQueryModel();
		gqm.getPage().setPageShow(100);

		Page<GoodsModel> page = igs.getByConditionPage(gqm);

		model.addAttribute("page",page);
		return "index";
	}

	@RequestMapping(value="/toGoodsDesc/{goodsUuid}",method=RequestMethod.GET)
	public String toGoodsDesc(Model model,@PathVariable("goodsUuid")int goodsUuid){
		GoodsModel gm = igs.getByUuid(goodsUuid);

		model.addAttribute("m",gm);
		return "goods/desc";
	}

	@RequestMapping(value="/addToCart/{goodsUuid}",method=RequestMethod.GET)
	public String addToCart(Model model,@PathVariable("goodsUuid")int goodsUuid,@CookieValue("MyLogin")String myLogin){
		int customerUuid = Integer.parseInt( myLogin.split(",")[0]);

		CartModel cm = new CartModel();
		cm.setBuyNum(1);
		cm.setCustomerUuid(customerUuid);
		cm.setGoodsUuid(goodsUuid);

		ics.create(cm);
		///////////////////////////
		CartQueryModel cqm = new CartQueryModel();
		cqm.getPage().setPageShow(1000);
		cqm.setCustomerUuid(customerUuid);


		Page<CartModel>  page = ics.getByConditionPage(cqm);

		model.addAttribute("page",page);

		return "cart/myCart";
	}

	@RequestMapping(value="/toCart",method=RequestMethod.GET)
	public String toCart(Model model,@CookieValue("MyLogin")String myLogin){
		int customerUuid = Integer.parseInt( myLogin.split(",")[0]);

		CartQueryModel cqm = new CartQueryModel();
		cqm.getPage().setPageShow(1000);
		cqm.setCustomerUuid(customerUuid);

		Page<CartModel>  page = ics.getByConditionPage(cqm);

		model.addAttribute("page",page);

		return "cart/myCart";
	}

	@RequestMapping(value="/order",method=RequestMethod.GET)
	public String order(@CookieValue("MyLogin")String myLogin){
		//1:查出这个人购物车所有的信息
		int customerUuid = Integer.parseInt( myLogin.split(",")[0]);

		CartQueryModel cqm = new CartQueryModel();
		cqm.getPage().setPageShow(1000);
		cqm.setCustomerUuid(customerUuid);

		Page<CartModel>  page = ics.getByConditionPage(cqm);

		//2：生成订单order
		float totalMoney = 0.0f;
		for(CartModel cm : page.getResult()){
			totalMoney += 10;
		}

		OrderModel order = new OrderModel();
		order.setCustomerUuid(customerUuid);
		order.setOrderTime(DateFormatHelper.long2str(System.currentTimeMillis()));
		order.setSaveMoney(0.0f);
		order.setTotalMoney(totalMoney);
		order.setState(1);

		ios.create(order);

		OrderQueryModel oqm = new OrderQueryModel();
		oqm.setOrderTime(order.getOrderTime());

		Page<OrderModel> orderPage = ios.getByConditionPage(oqm);
		order = orderPage.getResult().get(0);

		//3:生成订单详情orderDetail
		for(CartModel cm : page.getResult()){
			OrderDetailModel odm = new OrderDetailModel();
			odm.setGoodsUuid(cm.getGoodsUuid());
			odm.setOrderNum(cm.getBuyNum());
			odm.setPrice(10.0f);
			odm.setMoney(odm.getPrice() * odm.getOrderNum());
			odm.setSaveMoney(0.0f);
			odm.setOrderUuid(order.getUuid());
			iods.create(odm);

			//4.生成库存，减一下
			StoreModel sm = iss.getByGoodsUuid(cm.getGoodsUuid());
			sm.setStoreNum(sm.getStoreNum() - cm.getBuyNum());
			iss.update(sm);

			//5.删除购物车
			ics.delete(cm.getUuid());
		}


		return "success";
	}


}
