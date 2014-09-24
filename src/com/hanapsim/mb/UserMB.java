package com.hanapsim.mb;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.hanapsim.facade.UserFacade;
import com.hanapsim.model.User;

@SessionScoped
//@RequestScoped
@ManagedBean(name = "userMB")
public class UserMB extends AbstractMB implements Serializable {
	public static final String INJECTION_NAME = "#{userMB}";
	private static final long serialVersionUID = 1L;

	private User user;
	private List<User> users;
	private UserFacade userFacade;
	private String password;
	private String confirmPassword;
	private int userId;
	
	
	
	
	public int getUserId() {
		System.out.println(userId + "  userId:debug");
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	private String logOut;
	
	public String getLogOut() {
		logOut();
		return logOut;
	}

	public void setLogOut(String logOut) {
		this.logOut = logOut;
	}

	public String getPassword() {
		return password;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public UserFacade getUserFacade() {
		if (userFacade == null) {
			userFacade = new UserFacade();
		}

		return userFacade;
	}

	public User getUser() {
		if (user == null) {
			user = new User();
		}

		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

//	public boolean isAdmin() {
//		return user.isAdmin();
//	}
//
//	public boolean isActivata() {
//		return user.isActivata();
//	}
//
//	public boolean isAztec() {
//		return user.isAztec();
//	}
//
//	public boolean isMultiStore() {
//		return user.isMultiStore();
//	}
//
//	public boolean isSingleStore() {
//		return user.isSingleStore();
//	}

	public String logOut() {
		getRequest().getSession().invalidate();
		return "/public/login.xhtml";
	}

	private HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	// public void updateUserPassword() {
	// try {
	// if (password.matches(confirmPassword)) {
	// getUserFacade().updateUserPassword(user, password);
	// closeDialog();
	// displayInfoMessageToUser("User successfully updated!");
	// loadUsers();
	// resetUser();
	// } else {
	// keepDialogOpen();
	// displayErrorMessageToUser("Password and Confirm Password did not match!");
	// }
	// } catch (Exception e) {
	// keepDialogOpen();
	// displayErrorMessageToUser("Cannot update user!");
	// e.printStackTrace();
	// }
	// }

	// private void loadUsers() {
	// users = getUserFacade().listAll();
	// }

	public void resetUser() {
		user = new User();
	}

}