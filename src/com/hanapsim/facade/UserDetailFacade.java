package com.hanapsim.facade;

import java.security.MessageDigest;
import java.util.List;

import com.hanapsim.dao.UserDetailDAO;
import com.hanapsim.model.UserDetail;

public class UserDetailFacade {

	private UserDetailDAO userDetailDAO = new UserDetailDAO();

//	public UserDetail isValidLogin(String userName, String password) {
//		userDAO.beginTransaction();
//		User user = userDAO.findUserByUsername(userName);
//
//		if (user == null || !user.getPassword().equals(convertToSHA(password))) {
//			return null;
//		}
//
//		return user;
//	}

	public List<UserDetail> listAll() {
		userDetailDAO.beginTransaction();
		List<UserDetail> result = userDetailDAO.findAll();
		userDetailDAO.closeTransaction();
		return result;
	}

	// public void updateUserPassword(User user, String password) {cha
	//
	// userDAO.beginTransaction();
	//
	// User changePasswordUser = userDAO.find(user.getUserId());
	//
	// System.out.println(changePasswordUser
	// + " #########################*************************");
	//
	// changePasswordUser.setUserName(maintainUser.getUserName());
	// changePasswordUser.setPassword(convertToSHA(password));
	// persistedMaintainUser.setFullName(maintainUser.getFullName());
	// persistedMaintainUser.setEmailAddress(maintainUser.getEmailAddress());
	// persistedMaintainUser.setRole(maintainUser.getRole());
	// userDAO.update(changePasswordUser);
	// maintainUserDAO.persist(persistedMaintainUser);
	// auditTrail = new AuditTrail();
	// auditTrail.setModuleName(moduleName);
	// auditTrail.setAction(updateAct + " " + maintainUser.getUserName());
	// auditTrailFacade.createAuditTrail(auditTrail);
	// userDAO.commitAndCloseTransaction();
	// }

	// Start Added by EET July 8, 2013 for store filtering
//	public List<User> listAllMatchResultForMultiStore() {
//		userDAO.beginTransaction();
//		List<User> result = userDAO.findAllMatchResultForMultiStore();
//		userDAO.closeTransaction();
//		return result;
//	}

//	public List<User> listAllMatchResultForSingleStore() {
//		userDAO.beginTransaction();
//		List<User> result = userDAO.findAllMatchResultForSingleStore();
//		userDAO.closeTransaction();
//		return result;
//	}

	// End Added

	public String convertToSHA(String password) {
		String sha = null;

		try {
			MessageDigest d = MessageDigest.getInstance("SHA-256");
			d.update(password.getBytes());

			byte b[] = d.digest();

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < b.length; i++) {
				sb.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));
			}

			sha = sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return sha;
	}
}