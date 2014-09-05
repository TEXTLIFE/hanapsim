package com.hanapsim.facade;

import java.security.MessageDigest;
import java.util.List;

import com.hanapsim.dao.UserDAO;
import com.hanapsim.model.Role;
import com.hanapsim.model.Subscription;
import com.hanapsim.model.User;

public class UserFacade {

	private UserDAO userDAO = new UserDAO();

	public User isValidLogin(String simNumber, String password) {
		userDAO.beginTransaction();
		User user = userDAO.findUserBySimNumber(simNumber);

		if (user == null || !user.getPassword().equals(convertToSHA(password))) {
			return null;
		}

		return user;
	}

	public List<User> listAll() {
		userDAO.beginTransaction();
		List<User> result = userDAO.findAll();
		userDAO.closeTransaction();
		return result;
	}
	
	
	public void createUser(User user) {
		userDAO.beginTransaction();
//			user.setSimNumber(getSimNumber());
//			user.setPassword(getPassword());
			user.setPassword(convertToSHA(user.getPassword()));
			user.setRole(Role.STANDARD);
			user.setSubscription(Subscription.FREE);
			user.setIsActive(true);
		userDAO.save(user);
		userDAO.commitAndCloseTransaction();
	
	}
	
	
//	private String getSimNumber() {
//		FacesContext context = FacesContext.getCurrentInstance();
//		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
//		String simNumber = (String) request.getSession().getAttribute("simNumber");
//		return simNumber;
//	}
//	
//	private String getPassword() {
//		FacesContext context = FacesContext.getCurrentInstance();
//		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
//		String password = (String) request.getSession().getAttribute("password");
//		return password;
//	}


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
	public List<User> listAllMatchResultForMultiStore() {
		userDAO.beginTransaction();
		List<User> result = userDAO.findAllMatchResultForMultiStore();
		userDAO.closeTransaction();
		return result;
	}

	public List<User> listAllMatchResultForSingleStore() {
		userDAO.beginTransaction();
		List<User> result = userDAO.findAllMatchResultForSingleStore();
		userDAO.closeTransaction();
		return result;
	}

	// End Added

	public String convertToSHA(String password) {
		String sha = null;

		try {
			MessageDigest d = MessageDigest.getInstance("SHA-256");

			d.update(password.getBytes());

			byte b[] = d.digest();

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < b.length; i++) {
				sb.append(Integer.toString((b[i] & 0xff) + 0x100, 16)
						.substring(1));
			}

			sha = sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return sha;
	}
}