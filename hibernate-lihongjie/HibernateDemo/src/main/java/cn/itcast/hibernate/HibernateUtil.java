package cn.itcast.hibernate;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate ��װ���˵Ĺ�����
 * @���ܣ�hibernateUtil�����ࣺʵ�ֶ�session�ĳ�ʼ��
 * 		��ȡsessionFactory ��session
 * @author lhj
 * ��Դ�����ǲ�����Ƶѧϰ
 *
 */
public final class HibernateUtil {
	private static SessionFactory sessionFactory;
	
	private static ThreadLocal<Session> session = new ThreadLocal<Session>();
	
	private HibernateUtil(){
		
	}
	static{
		Configuration cfg = new Configuration();
		cfg.configure();//����Ĭ����Ϊ��hibernate.cfg.xml�ļ�
//		cfg.configure("filename");//�����������������ļ�
		sessionFactory = cfg.buildSessionFactory();
		
	}
	/**
	 * 
	 * @return ��ȡ�Ự����
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	//���session
	public static Session getSession(){
		return sessionFactory.openSession();
	}
	
	/**
	 * ��������
	 * @param entity
	 */
	//���Ӳ���
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
				tx.rollback();//�ع�
			throw e;//�׳��쳣
		}finally{
			if(session != null)
				session.close();
		}
	}
	//����
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
				tx.rollback();//�ع�
			throw e;//�׳��쳣
		}finally{
			if(session != null)
				session.close();
		}
	}
	//ɾ��
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
				tx.rollback();//�ع�
			throw e;//�׳��쳣
		}finally{
			if(session != null)
				session.close();
		}
	}
	//����id ��ѯ
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
	
	////////////////////////��������
	public static Session getThreadLocalSession(){
		Session s = (Session)session.get();
		if (s == null) {
			s = getSession();
			session.set(s);
		}
		return s;
	}
	///��������
	public static void closeSession() {
		Session s = (Session)session.get();
		if (s != null) {
			s.close();
			session.set(null);
		}
	}
}

























