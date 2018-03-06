package com.test;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.domain.IdCard;
import com.domain.Person;

/**
 * 
 * @author Administrator
 *
 */
public class One2One {
	public static void main(String[] args) {
		add();
		query(1);
	}
	static Person add(){
		Session s = null;
		Transaction tx = null;
		try{
			s = HibernateUtil.getSession();
			
			IdCard idCard = new IdCard();
			idCard.setUsefulLife(new Date());
			
			Person p1 = new Person();
			p1.setName("p1");
			p1.setIdCard(idCard);
//			Person p2 = new Person();
//			p2.setName("p2");
//			p2.setIdCard(idCard);
			
//			idCard.setPerson(p2);
			
			tx = s.beginTransaction();
			s.save(p1);
//			s.save(p2);
			s.save(idCard);
			tx.commit();
			return p1;
		}finally {
			if(s!=null)
				s.close();
		}
	}
	static Person query(int id){
		Session s = null;
		Transaction tx = null;
		try{
			s = HibernateUtil.getSession();	
			tx = s.beginTransaction();
//			Person p = (Person) s.get(Person.class, id);  //查询主控类 对象
//			System.out.println(p.getIdCard().getUsefulLife());
			IdCard idCard = (IdCard) s.get(IdCard.class, id);//查询被控类对象
			System.out.println(idCard.getUsefulLife());
			tx.commit();
			return null;
		}finally {
			if(s!=null)
				s.close();
		}
	}
}
