package com.sishuok.architecture1.goodsmgr.dao;

import com.sishuok.architecture1.goodsmgr.vo.GoodsModel;
import com.sishuok.architecture1.goodsmgr.vo.GoodsQueryModel;

import java.util.List;

//@Repository
public interface GoodsMapperDAO extends GoodsDAO {
	public List<Integer> getIdsByConditionPage(GoodsQueryModel gqm);
	public List<GoodsModel> getByIds(String ids);
}
