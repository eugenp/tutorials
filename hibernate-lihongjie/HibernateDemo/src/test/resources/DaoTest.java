package test;

import org.vo.User;

import dao.UserDao;
import dao.impl.UserDaoImpl;

/**
 * 增删改查 测试类
 * @author Administrator
 *
 */
public class DaoTest {
	public static void main(String[] args) {
		UserDao dao = new UserDaoImpl();
		User user = new User();
		//新增
		user.setUsername("zhangsan");
		user.setPassword("111111");
		dao.saveUser(user);
		System.out.println(user.getUsername()+" "+user.getPassword());
		System.out.println("新增成功！");
		//修改
		user.setUsername("newname");
		user.setPassword("22222");
		dao.updateUser(user);
		System.out.println(user.getUsername()+" "+user.getPassword());
		System.out.println("修改成功！");
		//通过用户名查询
//		User u = dao.findUserByName(user.getUsername());
//		System.out.println("查询出来的user的password为： "+u.getPassword());
		//通过用户名查询2
		User uu = dao.findByName2(user.getUsername());
		System.out.println("查询出来的新的user的password为： "+uu.getPassword());
		
	}


}
