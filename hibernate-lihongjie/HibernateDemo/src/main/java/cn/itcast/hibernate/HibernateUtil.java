package cn.itcast.hibernate;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate 封装好了的工具类
 * @功能：hibernateUtil工具类：实现对session的初始化
 * 		获取sessionFactory 和session
 * @author lhj
 * 来源：传智播客视频学习
 *
 */
public final class HibernateUtil {
	private static SessionFactory sessionFactory;
	
	private static ThreadLocal<Session> session = new ThreadLocal<Session>();
	
	private HibernateUtil(){
		
	}
	static{
		Configuration cfg = new Configuration();
		cfg.configure();//调用默认名为：hibernate.cfg.xml文件
//		cfg.configure("filename");//调用其他名的配置文件
		sessionFactory = cfg.buildSessionFactory();
		
	}
	/**
	 * 
	 * @return 获取会话工厂
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	//获得session
	public static Session getSession(){
		return sessionFactory.openSession();
	}
	
	/**
	 * 公共方法
	 * @param entity
	 */
	//增加操作
	public static void add(Object entity) {
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			session.save(entity);
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
	//更新
	public static void update(Object entity){
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			session.update(entity);
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
	//删除
	public static void  delete(Object entity) {
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			session.delete(entity);
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
	//根据id 查询
	public static Object get(Class clazz,Serializable id) {
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Object obj = session.get(clazz, id);
			return obj;
		}finally{
			if(session != null)
				session.close();
		}
	}
	
	////////////////////////后来加入
	public static Session getThreadLocalSession(){
		Session s = (Session)session.get();
		if (s == null) {
			s = getSession();
			session.set(s);
		}
		return s;
	}
	///后来加入
	public static void closeSession() {
		Session s = (Session)session.get();
		if (s != null) {
			s.close();
			session.set(null);
		}
	}
}

























