package com.sishuok.architecture1.goodsmgr.dao;

import com.sishuok.architecture1.common.dao.BaseDAO;
import com.sishuok.architecture1.goodsmgr.vo.GoodsModel;
import com.sishuok.architecture1.goodsmgr.vo.GoodsQueryModel;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsDAO extends BaseDAO<GoodsModel,GoodsQueryModel>{
//	public Page<GoodsModel> getByCondition(GoodsQueryModel qm);
}
