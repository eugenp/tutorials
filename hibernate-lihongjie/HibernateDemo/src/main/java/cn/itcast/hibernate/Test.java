package cn.itcast.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.vo.User;

public class Test {	
	public static void main(String[] args) {
		Session s = HibernateUtil.getSession();
		//开启事物
		Transaction tx = s.beginTransaction();
		
		User user = new User();
		user.setPassword("123456");
		user.setUsername("lihongjie");
		addUser(user);
		System.out.println("id: "+user.getId());
		User u = getUser(user.getId());
		System.out.println("name: "+ u.getUsername());
	}
	static void addUser(User user) {
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			session.save(user);
			tx.commit();
		}catch(HibernateException e) {
			if(tx != null) 
				tx.rollback();//回滚
			throw e;//抛出异常
		}finally{
			if(session != null)
				session.close();
		}
	}
	public static User getUser(int id) {
		Session session = null;
		
		try{
			session = HibernateUtil.getSession();
			Class userClass = User.class;
			User user = (User)session.get(userClass, id);
			return user; 
		}finally{
			if(session != null)
				session.close();
		}

	}
	
}
