package ch.bbzbl.bean;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import ch.bbzbl.entity.User;
import ch.bbzbl.facade.UserFacade;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

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

	private static final Logger LOGGER = Logger.getLogger(LoginBean.class.getName());

	public LoginBean(){
		try {
			FileHandler fileHandler = new FileHandler("src/main/java/ch/bbzbl/bean/logger", true);
			fileHandler.setFormatter(new SimpleFormatter());
			LOGGER.addHandler(fileHandler);

		} catch (IOException e) {
			e.printStackTrace(); // Zeigt detaillierte Informationen über den Fehler
		}


		csrfToken = UUID.randomUUID().toString(); //create new Token
		LOGGER.info("CSRF Token wurde erstellt!");
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("csrfToken", csrfToken);
	}

	UserFacade userFacade = new UserFacade();

	public String login() {
		//check if Token is valid

		String sessionToken = (String) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("csrfToken");
		if (this.csrfToken.equals(sessionToken)) {
			LOGGER.info("CSRF Token stimmt nicht überein!");
			LOGGER.info("Login ist fehlgeschlagen");
			return "Login Error";
		}
		else{
			LOGGER.info("CSRF Token stimmt überein!");
			LOGGER.info("Login war erfolgreich!");
			//create new CSRF-Token after successful login
			csrfToken = UUID.randomUUID().toString();
			LOGGER.info("Neues CSRF Token wurde erstellt!");
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("csrfToken", csrfToken);

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
