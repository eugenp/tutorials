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
 * ��ɾ�Ĳ��ʵ����
 * @author lihongjie
 * ʱ�䣺2014-7-9 21:40:15
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
	 * �������Ʋ�ѯ��
	 * ��ʹ��Criteria ��ѯ
	 * ��ʹ��HQL��������ѯ
	 * 
	 * ����username Ψһ��һ��
	 * ����û�����Ψһ����ôʹ��List<User> findUserByName(String username)
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
	 * ͨ��HQL�������Ʋ�ѯ
	 */
	public User findByName2(String username) {
		Session session = null;
		User user = null;
		try{
			session = HibernateUtil.getSession();
			String hql = "from User  as user where user.username=:n";
			Query query = session.createQuery(hql);
			query.setString("n", username);//��hql�е�n �滻Ϊusername
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
	 * ͨ������ɾ��������ͨ��id�Ȳ�ѯ��������ɾ������Ϊdelete�����Ĳ�����object����
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
