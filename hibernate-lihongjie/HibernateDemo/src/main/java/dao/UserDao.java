package dao;

import org.vo.User;

/**
 * CRUD ²Ù×÷
 * @author Administrator
 *
 */
public interface UserDao {
	public void saveUser(User user);
	public User findUserById(int id);
	public User findUserByName(String username);
	public User findByName2(String username);
	public void updateUser(User user);
	public void remove(User user);
}
