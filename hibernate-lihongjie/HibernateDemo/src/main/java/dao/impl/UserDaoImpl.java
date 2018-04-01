package dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.vo.User;

import cn.itcast.hibernate.HibernateUtil;

import dao.UserDao;
/**
 * 增删改查的实现类
 * @author lihongjie
 * 时间：2014-7-9 21:40:15
 *
 */
public class UserDaoImpl implements UserDao {

	@Override
	public void saveUser(User user) {
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			session.save(user);
		    tx.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session != null){
				session.close();
			}
		}
	}

	@Override
	public User findUserById(int id) {
		Session session = null;
		User user = null;
		try{
			session = HibernateUtil.getSession();
		    user = (User) session.get(User.class, id);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session != null){
				session.close();
			}
		}
		return user;
	}

	/**
	 * 根据名称查询：
	 * ①使用Criteria 查询
	 * ②使用HQL的条件查询
	 * 
	 * 返回username 唯一的一行
	 * 如果用户名不唯一，那么使用List<User> findUserByName(String username)
	 */
	@Override
	public User findUserByName(String username) {
		Session session = null;
		User user = null;
		try{
			session = HibernateUtil.getSession();
			Criteria c = session.createCriteria(User.class);
			c.add(Restrictions.eq("username", username));
			user = (User) c.uniqueResult();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session != null){
				session.close();
			}
		}
		return user;
	}
	/**
	 * 通过HQL条件名称查询
	 */
	public User findByName2(String username) {
		Session session = null;
		User user = null;
		try{
			session = HibernateUtil.getSession();
			String hql = "from User  as user where user.username=:n";
			Query query = session.createQuery(hql);
			query.setString("n", username);//将hql中的n 替换为username
			user = (User) query.uniqueResult();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session != null){
				session.close();
			}
		}
		return user;
	}

	@Override
	public void updateUser(User user) {
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			session.update(user);
		    tx.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session != null){
				session.close();
			}
		}

	}
	/**
	 * 通过对象删除，必须通过id先查询出对象，再删除，因为delete方法的参数是object对象。
	 */
	@Override
	public void remove(User user) {
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			session.delete(user);
		    tx.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session != null){
				session.close();
			}
		}
	}

}
