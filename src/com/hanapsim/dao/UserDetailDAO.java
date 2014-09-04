package com.hanapsim.dao;

import com.hanapsim.model.UserDetail;

public class UserDetailDAO extends GenericDAO<UserDetail> {

	private static final long serialVersionUID = 1L;

	public UserDetailDAO() {
		super(UserDetail.class);
	}

//	public User findUserByUsername(String userName) {
//		Map<String, Object> parameters = new HashMap<String, Object>();
//		parameters.put("userName", userName);
//
//		return super.findOneResult(User.FIND_BY_USERNAME, parameters);
//	}

	// Start Added by EET July 8, 2013 for store filtering
//	public List<User> findAllMatchResultForMultiStore() {
//		return super.listAllMatchResultForMultiStore();
//	}
//
//	public List<User> findAllMatchResultForSingleStore() {
//		return super.listAllMatchResultForSingleStore();
//	}
	// End Added
}