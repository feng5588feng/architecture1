package com.sishuok.architecture1.customermgr.dao;

import com.sishuok.architecture1.common.dao.BaseDAO;
import com.sishuok.architecture1.customermgr.vo.CustomerModel;
import com.sishuok.architecture1.customermgr.vo.CustomerQueryModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerDAO extends BaseDAO<CustomerModel,CustomerQueryModel>{

	public CustomerModel getByCustomerId(String customerId);

}
