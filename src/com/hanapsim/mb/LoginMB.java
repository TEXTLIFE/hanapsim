package com.hanapsim.mb;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.hanapsim.facade.UserFacade;
import com.hanapsim.model.User;

//@RequestScoped
@SessionScoped
@ManagedBean
public class LoginMB extends AbstractMB implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = UserMB.INJECTION_NAME)
	private UserMB userMB;

	private String simNumber;
	private String password;
	private int userId;
//	private String validationCode;

//	private StoreFacade storeFacade = new StoreFacade();
//	private List<Store> stores;
//	private RegistrationTypeFacade registrationTypeFacade = new RegistrationTypeFacade();
//	private List<RegistrationType> registrationTypes;

	public String getSimNumber() {
		return simNumber;
	}

	public void setSimNumber(String simNumber) {
		this.simNumber = simNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	
//	public String getValidationCode() {
//		return validationCode;
//	}
//
//	public void setValidationCode(String validationCode) {
//		this.validationCode = validationCode;
//	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public UserMB getUserMB() {
		return userMB;
	}

	public String login() {
		UserFacade userFacade = new UserFacade();
		User user = userFacade.isValidLogin(simNumber, password);

		if (user != null) {
			userMB.setUser(user);

			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

			request.getSession().setAttribute("user", user);
			request.getSession().setAttribute("userStatId", user.getId());
			request.getSession().setAttribute("userId", user.getId());
			request.getSession().setAttribute("simNumber", user.getSimNumber());
//			request.getSession().setAttribute("emailAddress", user.getEmailAddress());
			request.getSession().setAttribute("role", user.getRole());

			request.getSession().setAttribute("startDate", new Date());

//			if (user.getRole().toString() == "STANDARD") {
//				request.getSession().setAttribute("simNumber", 0);
////				request.getSession().setAttribute("storeIdTwo", 0);
//				
////				request.getSession().setAttribute("storeName", "Overall");
//			} else if (user.getRole().toString() == "SUBSCRIPTION" || user.getRole().toString() == "GUEST" ) {
//				stores = storeFacade.listAll();
//				registrationTypes = registrationTypeFacade.listAll();
				
//				System.out.println(stores + " stores:debug"); //debug
				
				
//				for (int i = 0; i < registrationTypes.size(); i++) {
//					if (user.getId() == registrationTypes.get(i).getLoginId()) {
//						if (registrationTypes.get(i).getIsExpired() == true) {
//							displayErrorMessageToUser("Your license has already expired!");
//							return null;
//						} else {
////							System.out.println(stores.get(i).getStoreName() + " storeName:debug"); //debug
//							request.getSession().setAttribute("simNumber", user.getSimNumber());
//							
//						}
//					}
//				}
//			} else {
//				System.out.println("Reserved code here!");
//			}

			return "/protected/index.xhtml";
		}

		displayErrorMessageToUser("Check your username/password");

		return null;
	}

	public void setUserMB(UserMB userMB) {
		this.userMB = userMB;
	}

	public void displayForgotPassword() {
		displayInfoMessageToUser("Please contact support@hanapsim.com");
	}
	
	
//	 @PostConstruct
//	    public void myPostConstruct(){
//		 FacesContext ctx = FacesContext.getCurrentInstance();
//		 ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
//		 HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
//        String renderKitId = FacesContext.getCurrentInstance().getViewRoot().getRenderKitId();        
//        if(renderKitId.equalsIgnoreCase("PRIMEFACES_MOBILE")){
//	       System.out.println("mobile detected");//debug
//	       NavigationHandler navigationHandler = ctx.getApplication().getNavigationHandler();
//	       navigationHandler.handleNavigation(ctx, null, "/mobile/login.xhtml?faces-redirect=true");	
//		   
//	    }
//	 }
}