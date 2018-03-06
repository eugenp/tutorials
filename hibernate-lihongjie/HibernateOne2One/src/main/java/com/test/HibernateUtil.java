package com.test;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.Serializable;

public final class HibernateUtil {
	private static SessionFactory sessionFactory;
	private HibernateUtil(){
		
	}
	static{
		Configuration cfg = new Configuration();
		cfg.configure();//����Ĭ����Ϊ��hibernate.cfg.xml�ļ�
//		cfg.configure("filename");//�����������������ļ�
		sessionFactory = cfg.buildSessionFactory();
		
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static Session getSession(){
		return sessionFactory.openSession();
	}

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
				tx.rollback();
			throw e;
		}finally{
			if(session != null)
				session.close();
		}
	}

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
				tx.rollback();
			throw e;
		}finally{
			if(session != null)
				session.close();
		}
	}

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
				tx.rollback();
			throw e;
		}finally{
			if(session != null)
				session.close();
		}
	}

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
}
