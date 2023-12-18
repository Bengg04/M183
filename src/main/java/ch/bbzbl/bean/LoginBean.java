package ch.bbzbl.bean;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import ch.bbzbl.entity.Role;
import ch.bbzbl.entity.User;
import ch.bbzbl.facade.UserFacade;

import java.util.UUID;

//@RequestScoped
//@SessionScoped //---Changed by Marco

@ManagedBean
@RequestScoped
public class LoginBean extends AbstractBean {
	public static final String ATTR_USER = "user";

	@ManagedProperty(value = UserBean.DI_NAME)
	private UserBean userBean;

	private String csrfToken;
	private String username;
	private String password;

	public LoginBean(){
		csrfToken = UUID.randomUUID().toString(); //create new Token
	}

	UserFacade userFacade = new UserFacade();

	public String login() {
		//check if Token is valid
		String sessionToken = (String) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("csrfToken");
		if (!this.csrfToken.equals(sessionToken)) {
			//invalid Token
			return null;
		}
		else{
			// HACK
			// you have to implement a safe login mechanism
			User user = userFacade.getUserIfExists(this.username, this.password);

			if (user != null) {
				userBean.setLoggedInUser(user);
				FacesContext context = FacesContext.getCurrentInstance();
				HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
				req.getSession().setAttribute(ATTR_USER, user);
				return "/pages/protected/index.xhtml?faces-redirect=true";

			} else {
				keepDialogOpen();
				displayErrorMessageToUser("Wrong Username/Password. Try again");

			}
			return null;
		}
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public String getCsrfToken(){
		return csrfToken;
	}

	public void setCsrfToken(String csrfToken){
		this.csrfToken = csrfToken;
	}

}
