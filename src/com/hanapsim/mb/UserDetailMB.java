package com.hanapsim.mb;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.hanapsim.facade.UserDetailFacade;
import com.hanapsim.model.UserDetail;
@ViewScoped
@ManagedBean
//@Named
public class UserDetailMB implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<UserDetail> userDetails;
	private UserDetailFacade userDetailFacade;
	
	public List<UserDetail> getAllUsers() {
		if (userDetails == null) {
			loadUserDetails();
		}

		return userDetails;
	}

//	private HttpServletRequest getHttpServletRequest() {
//		FacesContext facesContext = FacesContext.getCurrentInstance();
//		HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext
//				.getExternalContext().getRequest();
//		return httpServletRequest;
//	}

	private void loadUserDetails() {
		userDetails = getUserDetailFacade().listAll();
	}
	
	public UserDetailFacade getUserDetailFacade() {
		if (userDetailFacade == null) {
			userDetailFacade = new UserDetailFacade();
		}

		return userDetailFacade;
	}

}
