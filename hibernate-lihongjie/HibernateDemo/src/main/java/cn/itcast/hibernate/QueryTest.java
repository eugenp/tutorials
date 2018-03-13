package cn.itcast.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.vo.User;

public class QueryTest {
	public static void main(String[] args) {
		User user = new User();
		user.setPassword("fsdl");
		user.setUsername("nameee");
		HibernateUtil.add(user);
		
		query(user.getUsername());
	}
	//根据条件查询
	static void query(String name){
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			String hql = "From User as user where user.username=?";
//			String hql = "From User as user where user.username=:name";//
			Query query = session.createQuery(hql);
			query.setString(0, name); //索引号 0  与 hql 语句中的问号？的位子一一对应，起始为0
//			query.setString("name", name);
			List<User> list = query.list();//executeQuery()
			
			User u = (User) query.uniqueResult();
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
