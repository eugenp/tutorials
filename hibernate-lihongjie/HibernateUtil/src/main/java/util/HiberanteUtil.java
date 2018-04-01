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
 * 封装好了的工具类
 * 来源：网上OPEN经验库
 * @author lhj
 *
 */
public class HiberanteUtil 
{
	private static SessionFactory sessionFactory;
	
	/**
	 * 
	 * @return 获取会话工厂
	 */
	public static SessionFactory getSessionFactory()
	{
		//第一步：读取Hibernate的配置文件,  hibernate.cfg.xml文件
		Configuration con = new Configuration().configure();
		//第二步：创建服务注册构建器对象，通过配置对象中加载所有的配置信息,Hibernate 4.0新特性,之前的创建SessionFactory已经不建议使用
		ServiceRegistryBuilder regbuilder = new ServiceRegistryBuilder().applySettings(con.getProperties());
		//创建注册服务
		ServiceRegistry reg = regbuilder.buildServiceRegistry();
		//第三步：创建会话工厂
		sessionFactory = con.buildSessionFactory(reg);
		
		return sessionFactory;
	}
	
	/**
	 * 
	 * @return 获取会话对象
	 */
	public static Session getSession()
	{
		return getSessionFactory().openSession();
	}
	/**
	 * 
	 * @param obj  添加数据
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
				//事务回滚
				tran.rollback();
			}
		}
		finally
		{
			if(session != null)
			{
				//关闭session
				session.close();
			}
		}
		return result;
	}
	/**
	 * 
	 * @param object
	 * @return 更新数据，参数为修改的主键id对象
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
				//事务回滚
				tran.rollback();
			}
		}
		finally
		{
			if(session != null)
			{
				//关闭session
				session.close();
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param c
	 * @param obj 查询一条数据，根据主键的id号
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
	 * @return 删除数据
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
	 * @param <T> 查询多条记录
	 * @param sql sql 语句
	 * @param param 参数数组
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
	 * 查询单条记录
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
	 * @return 实现分页查询
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
			//筛选条数
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
	 * @return 返回数据个数
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























