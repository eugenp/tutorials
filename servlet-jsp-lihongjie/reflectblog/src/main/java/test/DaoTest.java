package test;

import bean.User;
import dao.DaoFactory;
import dao.UserDao;

import java.util.List;

/**
 * 数据访问层的测试类
 * DaoFactory :工厂类
 * GenerateDao:泛型
 *
 * @author Administrator
 */
public class DaoTest {
    public static void main(String[] args) {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        List<User> list = userDao.findAll();
        for (User user : list) {
            System.out.println(user.getUsername());
        }
//		BlogDao blogDao = DaoFactory.getInstance().getBlogDao();
//		List<Blog> blogList = blogDao.findAll();
//		for (Blog blog:blogList) {
//			System.out.println(blog.getName());
//		}
    }
}
