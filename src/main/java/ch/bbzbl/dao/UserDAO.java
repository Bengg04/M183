package ch.bbzbl.dao;

import java.util.HashMap;
import java.util.Map;

import ch.bbzbl.entity.User;

public class UserDAO extends GenericDAO<User> {
	private static final long serialVersionUID = 1L;

	public UserDAO() {
		super(User.class);
	}
	
	public User findUserIfExists(String username) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("username", username);

		return super.findOneResult(User.FIND_BY_USERNAMEPWD, parameters);	
	}

	//...
}
