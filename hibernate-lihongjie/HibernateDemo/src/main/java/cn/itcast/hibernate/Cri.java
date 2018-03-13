package cn.itcast.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.vo.User;

public class Cri {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		cri("name");
	}
	static void cri(String name) {
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Criteria c = session.createCriteria(User.class);
			//条件查询   eq : 等于   ，name 为约束
			c.add(Restrictions.eq("name", name));
			
			//分页
			c.setFirstResult(0);
			c.setMaxResults(10);
//			c.add(Restrictions.ge(propertyName, value))//
			List<User> list = c.list();//executeQuery()
			
			User u = (User) c.uniqueResult();
			System.out.println("---: "+u);
			
			for(User user : list){
				System.out.println(user.getUsername());
			}
		}finally{
			if(session != null)
				session.close();
		}
	}

}
