package ch.bbzbl.facade;

import java.io.Serializable;

import ch.bbzbl.dao.EntityManagerHelper;
import ch.bbzbl.dao.UserDAO;
import ch.bbzbl.entity.User;
import ch.bbzbl.util.PasswordUtil;

public class UserFacade implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private final UserDAO userDAO = new UserDAO();

	public User getUserIfExists(String username, String password) {
		EntityManagerHelper.beginTransaction();
		User user = userDAO.findUserIfExists(username);
		EntityManagerHelper.commitAndCloseTransaction();
		if (PasswordUtil.verifyPassword(password, user.getPassword())) {
			return user;
		} else {
			return null;
		}
	}
}
