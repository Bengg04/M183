package ch.bbzbl.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import ch.bbzbl.dao.EntityManagerHelper;
import ch.bbzbl.dao.UserDAO;
import ch.bbzbl.entity.Role;
import ch.bbzbl.entity.User;
import ch.bbzbl.facade.UserFacade;
import ch.bbzbl.util.PasswordUtil;

@ApplicationScoped
@ManagedBean(eager = true)
public class StartupBean {

	private static final Logger LOGGER = Logger.getLogger(StartupBean.class.getName());
	/**
	 * initialize EntityManagerFactory at application startup
	 */
	@PostConstruct
	public void init() {
		try{
			EntityManagerHelper.init();

			UserFacade facade = new UserFacade();
			String PWa = "admin";
			String PWaH = PasswordUtil.hashPassword(PWa);
			String PWu = "user";
			String PWuH = PasswordUtil.hashPassword(PWu);


			// Create dummy ADMIN user if not exist
			User a = facade.getUserIfExists("admin", PWa);
			if (a == null) {
				UserDAO dao = new UserDAO();
				EntityManagerHelper.beginTransaction();
				dao.save(new User("admin", PWaH, Role.ADMIN));
				EntityManagerHelper.commitAndCloseTransaction();
			}
			// Create dummy USER user if not exist
			User u = facade.getUserIfExists("user", PWu);
			if (u == null) {
				UserDAO dao = new UserDAO();
				EntityManagerHelper.beginTransaction();
				dao.save(new User("user", PWuH, Role.USER));
				EntityManagerHelper.commitAndCloseTransaction();
			}
		} catch (Exception e){
			LOGGER.log(Level.SEVERE, "Fehler bei der Initialisierung des StartupBean", e);
		}

	}
}
