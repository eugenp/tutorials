package com.lhj.web.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.lhj.web.hibernate.HibernateSessionFactory;
import com.lhj.web.vo.PageBean;


public abstract class AGenericHibernateDao<T extends Serializable, ID extends Serializable>
		implements IGenericDao<T, ID> {

	private Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	public AGenericHibernateDao() {
		this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public T create(T entity) {
		Session session = HibernateSessionFactory.getSession();
		Transaction trans = null;
		try{
			trans = session.beginTransaction();
			session.save(entity);
			trans.commit();
			return entity;
		} catch (RuntimeException ex){
			ex.printStackTrace();
			trans.rollback();
			return null;
		} finally {
			HibernateSessionFactory.closeSession();
		}		
	}

	@SuppressWarnings("unchecked")

	public T findById(ID id) {
		Session session = HibernateSessionFactory.getSession();
		try{
			return (T)session.get(this.persistentClass, id);
		} catch (RuntimeException ex){
			ex.printStackTrace();
			return null;
		}
	}


	public void delete(ID id) {
		Session session = HibernateSessionFactory.getSession();
		Transaction trans = null;
		try{
			trans = session.beginTransaction();
			session.delete(findById(id));
			trans.commit();
		} catch (RuntimeException ex){
			ex.printStackTrace();
			trans.rollback();
		} finally {
			HibernateSessionFactory.closeSession();
		}		
	}

	public void update(T entity) {
		Session session = HibernateSessionFactory.getSession();
		Transaction trans = null;
		try{
			trans = session.beginTransaction();
			session.update(entity);
			trans.commit();
		} catch (RuntimeException ex){
			ex.printStackTrace();
			trans.rollback();
		} finally {
			HibernateSessionFactory.closeSession();
		}	
		
	}


	public List<T> findAll() {		
		return findByCriteria();
	}

	@SuppressWarnings("unchecked")
	public List<T> findByHQL(String strHQL, Object[] params) {
		Session session = HibernateSessionFactory.getSession();
		try{
			Query query = session.createQuery(strHQL);
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
			return query.list();
		} catch (RuntimeException ex){
			ex.printStackTrace();
			return null;
		} finally {
			HibernateSessionFactory.closeSession();
		}
	}

	public PageBean findByPage(String strHQL, int currentPage, int pageSize,
			Object[] params) {

		PageBean pageBean = new PageBean();

		Session session = HibernateSessionFactory.getSession();
		try{

			Query query = session.createQuery(strHQL);

			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}

			query.setFirstResult((currentPage-1)*pageSize);

			query.setMaxResults(pageSize);

			pageBean.setData(query.list());
			

			strHQL = "select count(*) "+ strHQL.substring(strHQL.toLowerCase().indexOf("from"));

			query = session.createQuery(strHQL);

			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}

			pageBean.setTotalRows(Integer.parseInt(query.uniqueResult().toString()));
		} catch (RuntimeException ex){
			ex.printStackTrace();
			return null;
		} finally {

			HibernateSessionFactory.closeSession();
		}		

		pageBean.setCurrentPage(currentPage);
		pageBean.setPageSize(pageSize);
		
		return pageBean;
	}

	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(){
		Session session = HibernateSessionFactory.getSession();
		try{
			Criteria criteria = session.createCriteria(this.persistentClass);
			return criteria.list();
		} catch (RuntimeException ex){
			ex.printStackTrace();
			return null;
		} finally {
			HibernateSessionFactory.closeSession();
		}
	}
}
