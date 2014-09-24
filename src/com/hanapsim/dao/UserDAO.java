package com.hanapsim.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hanapsim.model.User;

public class UserDAO extends GenericDAO<User> {

	private static final long serialVersionUID = 1L;

	public UserDAO() {
		super(User.class);
	}

//	public User findUserByUsername(String userName) {
//		Map<String, Object> parameters = new HashMap<String, Object>();
//		parameters.put("userName", userName);
//
//		return super.findOneResult(User.FIND_BY_USERNAME, parameters);
//	}
	
	
	public User findUserBySimNumber(String simNumber) {
	Map<String, Object> parameters = new HashMap<String, Object>();
	parameters.put("simNumber", simNumber);

	return super.findOneResult(User.FIND_BY_SIMNUMBER, parameters);
}
	

	// Start Added by EET July 8, 2013 for store filtering
	public List<User> findAllMatchResultForMultiStore() {
		return super.listAllMatchResultForMultiStore();
	}

	public List<User> findAllMatchResultForSingleStore() {
		return super.listAllMatchResultForSingleStore();
	}
	// End Added
}