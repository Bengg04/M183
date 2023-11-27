package ch.bbzbl.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import ch.bbzbl.dao.EntityManagerHelper;
import ch.bbzbl.dao.UserDAO;
import ch.bbzbl.entity.Role;
import ch.bbzbl.entity.User;
import ch.bbzbl.facade.UserFacade;

@ApplicationScoped
@ManagedBean(eager = true)
public class StartupBean {

	/**
	 * initialize EntityManagerFactory at application startup
	 */
	@PostConstruct
	public void init() {
		EntityManagerHelper.init();

		// Create dummy ADMIN user if not exist
		UserFacade facade = new UserFacade();
		User u = facade.getUserIfExists("admin", "admin");
		if (u == null) {
			UserDAO dao = new UserDAO();
			EntityManagerHelper.beginTransaction();
			dao.save(new User("admin", "admin", Role.ADMIN));
			EntityManagerHelper.commitAndCloseTransaction();
		}
	}
}
