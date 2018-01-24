package com.sishuok.architecture1.goodsmgr.dao;

import org.springframework.stereotype.Repository;
import com.sishuok.architecture1.common.dao.BaseDAO;

import com.sishuok.architecture1.goodsmgr.vo.GoodsModel;
import com.sishuok.architecture1.goodsmgr.vo.GoodsQueryModel;

@Repository
public interface GoodsDAO extends BaseDAO<GoodsModel,GoodsQueryModel>{
	
}
