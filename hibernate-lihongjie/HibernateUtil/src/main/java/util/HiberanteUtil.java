package util;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
/**
 * ��װ���˵Ĺ�����
 * ��Դ������OPEN�����
 * @author lhj
 *
 */
public class HiberanteUtil 
{
	private static SessionFactory sessionFactory;
	
	/**
	 * 
	 * @return ��ȡ�Ự����
	 */
	public static SessionFactory getSessionFactory()
	{
		//��һ������ȡHibernate�������ļ�,  hibernate.cfg.xml�ļ�
		Configuration con = new Configuration().configure();
		//�ڶ�������������ע�ṹ��������ͨ�����ö����м������е�������Ϣ,Hibernate 4.0������,֮ǰ�Ĵ���SessionFactory�Ѿ�������ʹ��
		ServiceRegistryBuilder regbuilder = new ServiceRegistryBuilder().applySettings(con.getProperties());
		//����ע�����
		ServiceRegistry reg = regbuilder.buildServiceRegistry();
		//�������������Ự����
		sessionFactory = con.buildSessionFactory(reg);
		
		return sessionFactory;
	}
	
	/**
	 * 
	 * @return ��ȡ�Ự����
	 */
	public static Session getSession()
	{
		return getSessionFactory().openSession();
	}
	/**
	 * 
	 * @param obj  �������
	 * @return 
	 * 
	 */
	public static boolean add(Object obj)
	{
		Session session = null;
		Transaction tran = null;
		boolean result = false;
		try
		{
			session = getSession();
			tran = session.beginTransaction();
			session.save(obj);
			tran.commit();
			result = true;
		}
		catch (Exception e)
		{
			if(tran != null)
			{
				//����ع�
				tran.rollback();
			}
		}
		finally
		{
			if(session != null)
			{
				//�ر�session
				session.close();
			}
		}
		return result;
	}
	/**
	 * 
	 * @param object
	 * @return �������ݣ�����Ϊ�޸ĵ�����id����
	 */
	public static boolean update(Object object)
	{
		Session session = null;
		Transaction tran = null;
		boolean result = false;
		try
		{
			session = getSession();
			tran = session.beginTransaction();
			session.update(object);
			tran.commit();
			result = true;
		}
		catch (Exception e)
		{
			if(tran != null)
			{
				//����ع�
				tran.rollback();
			}
		}
		finally
		{
			if(session != null)
			{
				//�ر�session
				session.close();
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param c
	 * @param obj ��ѯһ�����ݣ�����������id��
	 * @return
	 */
	public static Object get(Class c,int obj)
	{
		Session session = null;
		Object object = null;
		try
		{
			session = getSession();
			object = session.get(c, obj);
		}
		catch (Exception e)
		{
			
		}
		finally
		{
			if(session != null)
			{
				//
				session.close();
			}
		}
		return object;
	}
	/**
	 * 
	 * @param obj
	 * @return ɾ������
	 */
	public static boolean delete(Object obj)
	{
		Session session = null;
		Transaction tran = null;
		boolean result = false;
		try
		{
			session = getSession();
			tran = session.beginTransaction();
			session.delete(obj);
			tran.commit();
			result = true;
		}
		catch (Exception e)
		{
			if(tran != null)
			{
				//
				tran.rollback();
			}
		}
		finally
		{
			if(session != null)
			{
				session.close();
			}
		}
		return result;
	}
	
	
	/**
	 * @param <T> ��ѯ������¼
	 * @param sql sql ���
	 * @param param ��������
	 * @return
	 */
	public static <T> List<T> query(String sql,String[] param)
	{
		List<T> list = new ArrayList<T>();
		Session session = null;
		try
		{
			session = getSession();
			Query query = session.createQuery(sql);
			if(param != null) {
				for(int i = 0 ; i < param.length ; i++) {
					query.setString(i, param[i]);
				}
			}
		}
		catch (Exception e)
		{
			
		}
		finally
		{
			if(session != null)
			{
				session.close();
			}
		}
		return list;
	}
	
	/**
	 * ��ѯ������¼
	 * @param sql
	 * @param param
	 * @return
	 */
	public static Object queryOne(String sql,String[] param)
	{
		Object object = null;
		Session session = null;
		try
		{
			session = getSession();
			Query query = session.createQuery(sql);
			if(param != null) {
				for (int i = 0 ; i < param.length ; i++) {
					query.setString(0, param[i]);
				}
			}
		}
		catch (Exception e)
		{
			
		}
		finally
		{
			if(session != null)
			{
				session.close();
			}
		}
		return object;
	}
	
	/**
	 * 
	 * @param sql
	 * @param param
	 * @param page
	 * @param size
	 * @return ʵ�ַ�ҳ��ѯ
	 */
	public static <T> List<T> queryByPage(String sql,String[] param,int page,int size)
	{
		List<T> list = new ArrayList<T>();
		Session session = null;
		try
		{
			session = getSession();
			Query query =session.createQuery(sql);
			if(param != null)
			{
				for(int i = 0 ; i < param.length ; i++) {
					query.setString(i, param[i]);
				}
			}
			//ɸѡ����
			query.setFirstResult((page - 1)*size);
			query.setMaxResults(size);
			list = query.list();
		}
		catch (Exception e) 
		{
			
		}
		finally
		{
			if(session != null) 
			{
				session.close();
			}
		}
		return list;
	}
	
	/**
	 * 
	 * @param hql
	 * @param pras
	 * @return �������ݸ���
	 */
	public static int getCount(String hql , String[] pras) {
		int count = 0;
		Session s = null;
		try {
			s = getSession();
			Query q = s.createQuery(hql);
			if (pras != null) {
				for (int i = 0 ; i < pras.length ; i++) {
					q.setString(i, pras[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (s != null) {
				s.close();
			}
		}
		return count;
	}
	
}























