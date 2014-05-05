package org.baeldung.persistence.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.baeldung.persistence.model.Foo;
import org.baeldung.persistence.service.IFooService;
import org.baeldung.spring.PersistenceConfig;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.hibernate.Criteria;
import org.hibernate.NullPrecedence;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.common.util.StringHelper;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.junit.BeforeClass;
import org.junit.Test;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class }, loader = AnnotationConfigContextLoader.class)



public class FooSortingServiceTest {

	
	@Test
	public final void whenHQlSortingByOneAttribute_thenPrintSortedResults() {
		Session sess = null;
		List<Foo> fooList = new ArrayList();
		
	    try{
	      SessionFactory sf = new Configuration().
	          configure().buildSessionFactory();
	      sess = sf.openSession();
	      String hql = "FROM Foo f ORDER BY f.name";	      
	      Query query = sess.createQuery(hql);
	      fooList = query.list();
	      for(Foo foo: fooList){
	        System.out.println(
	            "Name: " + foo.getName()
	            + ", Id: " + foo.getId()	            
	          );
	      }
	      Transaction tr = sess.beginTransaction();
	      tr.commit();
	    }catch(Exception ex){
	      ex.printStackTrace();
	    }finally{
	      if(sess != null){
	        sess.close();
	      }
	    }
	   
	}
	@Test
	public final void whenHQlSortingByOneAttribute_andOrderDirection_thenPrintSortedResults() {
		Session sess = null;
		List<Foo> fooList = new ArrayList();
		
	    try{
	      SessionFactory sf = new Configuration().
	          configure().buildSessionFactory();
	      sess = sf.openSession();
	      String hql = "FROM Foo f ORDER BY f.name ASC";	      
	      Query query = sess.createQuery(hql);
	      fooList = query.list();
	      for(Foo foo: fooList){
	        System.out.println(
	            "Name: " + foo.getName()
	            + ", Id: " + foo.getId()
	            
	          );
	      }
	      Transaction tr = sess.beginTransaction();
	      tr.commit();
	    }catch(Exception ex){
	      ex.printStackTrace();
	    }finally{
	      if(sess != null){
	        sess.close();
	      }
	    }
	   
	}
	
	@Test
	public final void whenHQlSortingByMultipleAttributes_thenSortedResults() {
		Session sess = null;
		List<Foo> fooList = new ArrayList();
		
	    try{
	      SessionFactory sf = new Configuration().
	          configure().buildSessionFactory();
	      sess = sf.openSession();
	      String hql = "FROM Foo f ORDER BY f.name, f.id";
	      Query query = sess.createQuery(hql);
	      fooList = query.list();
	      for(Foo foo: fooList){
	        System.out.println(
	            "Name: " + foo.getName()
	            + ", Id: " + foo.getId()
	            
	          );
	      }
	      Transaction tr = sess.beginTransaction();
	      tr.commit();
	    }catch(Exception ex){
	      ex.printStackTrace();
	    }finally{
	      if(sess != null){
	        sess.close();
	      }
	    }
	   
	}
	
	@Test
	public final void whenHQlSortingByMultipleAttributes_andOrderDirection_thenPrintSortedOrderedResults() {
		Session sess = null;
		List<Foo> fooList = new ArrayList();
		
	    try{
	      SessionFactory sf = new Configuration().
	          configure().buildSessionFactory();
	      sess = sf.openSession();
	      String hql = "FROM Foo f ORDER BY f.name DESC, f.id ASC";
	      Query query = sess.createQuery(hql);
	      fooList = query.list();
	      for(Foo foo: fooList){
	        System.out.println(
	            "Name: " + foo.getName()
	            + ", Id: " + foo.getId()
	            
	          );
	      }
	      Transaction tr = sess.beginTransaction();
	      tr.commit();
	    }catch(Exception ex){
	      ex.printStackTrace();
	    }finally{
	      if(sess != null){
	        sess.close();
	      }
	    }
	   
	}
	@Test
	public final void whenCriteriaSortingByOneAttr_thenPrintSortedResults() {
		Session sess = null;
		SessionFactory sf = new Configuration().
				configure().buildSessionFactory();
		sess = sf.openSession();
		List<Foo> fooList = new ArrayList();  
		try{
			sess.beginTransaction();
			Criteria criteria = sess.createCriteria(Foo.class, "FOO");
			criteria.addOrder(Order.asc("id"));
			fooList = criteria.list();
			assertEquals(1,fooList.get(0).getId());
			assertEquals(100,fooList.get(fooList.toArray().length-1).getId());
			for(Foo foo: fooList){
				System.out.println(
						"Id: " + foo.getId()
						+ ", FirstName: " + foo.getName()

						);
			}

			sess.getTransaction().commit();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(sess != null){
				sess.close();
			}
		}
	}
	@Test
	public final void whenCriteriaSortingByMultipAttr_thenSortedResults() {
		Session sess = null;
		SessionFactory sf = new Configuration().
				configure().buildSessionFactory();
		sess = sf.openSession();
		List<Foo> fooList = new ArrayList();  
		try{
			sess.beginTransaction();
			Criteria criteria = sess.createCriteria(Foo.class, "FOO");
			criteria.addOrder(Order.asc("name"));
			criteria.addOrder(Order.asc("id"));
			fooList = criteria.list();
			for(Foo foo: fooList){
			System.out.println(
				"Id: " + foo.getId()
				+ ", FirstName: " + foo.getName()

				);
				
			}

			sess.getTransaction().commit();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(sess != null){
				sess.close();
			}
		}
	}
	@Test
	public final void whenCriteriaSortingStringNullsLastAsc_thenNullsLast() {
		Session sess = null;
		SessionFactory sf = new Configuration().
				configure().buildSessionFactory();
		sess = sf.openSession();
		List<Foo> fooList = new ArrayList();  
		try{
			sess.beginTransaction();
			Criteria criteria = sess.createCriteria(Foo.class, "FOO");
			criteria.addOrder(Order.asc("name").nulls(NullPrecedence.LAST));
			fooList = criteria.list();
			assertNull(fooList.get(fooList.toArray().length-1).getName());
			for(Foo foo: fooList){
			System.out.println(
				"Id: " + foo.getId()
				+ ", FirstName: " + foo.getName()

				);
				
			}
			sess.getTransaction().commit();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(sess != null){
				sess.close();
			}
		}
	}
	@Test
	public final void whenCriteriaSortingStringNullsFirstDesc_thenNullsFirst() {
		Session sess = null;
		SessionFactory sf = new Configuration().
				configure().buildSessionFactory();
		sess = sf.openSession();
		List<Foo> fooList = new ArrayList();  
		try{
			sess.beginTransaction();
			Criteria criteria = sess.createCriteria(Foo.class, "FOO");
			criteria.addOrder(Order.desc("name").nulls(NullPrecedence.FIRST));
			fooList = criteria.list();
			assertNull(fooList.get(0).getName());
			for(Foo foo: fooList){
			System.out.println(
				"Id: " + foo.getId()
				+ ", FirstName: " + foo.getName()

				);
				
			}
			sess.getTransaction().commit();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(sess != null){
				sess.close();
			}
		}
	}
	@Test
	public final void whenHQlSortingByStringNullLast_thenLastNull() {
		Session sess = null;
		List<Foo> fooList = new ArrayList();
		
	    try{
	      SessionFactory sf = new Configuration().
	          configure().buildSessionFactory();
	      sess = sf.openSession();
	      String hql = "FROM Foo f ORDER BY f.name NULLS LAST";
	      
	      Query query = sess.createQuery(hql);
	      fooList = query.list();
	      assertNull(fooList.get(fooList.toArray().length-1).getName());
	      for(Foo foo: fooList){
	        System.out.println(
	            "Name: " + foo.getName()
	            + ", Id: " + foo.getId()
	            
	          );
	      }
	      Transaction tr = sess.beginTransaction();
	      tr.commit();
	    }catch(Exception ex){
	      ex.printStackTrace();
	    }finally{
	      if(sess != null){
	        sess.close();
	      }
	    }
	   
	}
	
	
	@Test
	public final void whenSortingBars_thenBarsWithSortedFoos(){
		Session sess = null;
		Set <Foo> fooList = new TreeSet();
	    List <Bar> barList = new ArrayList();
		
	    try{
	      SessionFactory sf = new Configuration().
	          configure().buildSessionFactory();
	      sess = sf.openSession();
	      String hql = "FROM Bar b ORDER BY b.id";
	      Query query = sess.createQuery(hql);
	      barList = query.list();
	     
	      for(Bar bar:barList){
	    	  
	    	System.out.println("Bar Id:"+bar.getId());
			for(Foo foo:bar.getFooList()){
				System.out.println("FooName:"+foo.getName());
					
			 }
		  }
	      Transaction tr = sess.beginTransaction();
	      tr.commit();
	    }catch(Exception ex){
	      ex.printStackTrace();
	    }finally{
	      if(sess != null){
	        sess.close();
	      }
	    }
	    
	
	}
	@Test
	public final void whenSortingPrimitiveNulls_thenException(){
		Session sess = null;
		List <Foo> fooList = new ArrayList();
	    List <Bar> barList = new ArrayList();
		
	    try{
	      SessionFactory sf = new Configuration().
	          configure().buildSessionFactory();
	      sess = sf.openSession();
	      String hql = "FROM Foo f ORDER BY f.idx";
	      Query query = sess.createQuery(hql);
	      boolean thrown = false;
			try {
				fooList = criteria.list();
			} catch (org.hibernate.PropertyAccessException e) {
			    thrown = true;
			}
			assertTrue(thrown);
		 
	      Transaction tr = sess.beginTransaction();
	      tr.commit();
	    }catch(Exception ex){
	      ex.printStackTrace();
	    }finally{
	      if(sess != null){
	        sess.close();
	      }
	    }
	    
	
	}
	@Test
	public final void whenSortingStringNullsLast_thenReturnNullsLast(){
		Session sess = null;
		List <Foo> fooList = new ArrayList();
	    List <Bar> barList = new ArrayList();
		
	    try{
	      SessionFactory sf = new Configuration().
	          configure().buildSessionFactory();
	      sess = sf.openSession();
	      String hql = "FROM Foo f ORDER BY f.name NULLS LAST";
	      Query query = sess.createQuery(hql);
	      fooList = query.list();
	      assertNull(fooList.get(fooList.toArray().length-1).getName());
	      for(Foo foo:fooList){
	    	  System.out.println("FooIDX:"+foo.getName());

	      }
		 
	      Transaction tr = sess.beginTransaction();
	      tr.commit();
	    }catch(Exception ex){
	      ex.printStackTrace();
	    }finally{
	      if(sess != null){
	        sess.close();
	      }
	    }
	    
	
	}
	@Test
	public final void whenNullPrimitiveValueCriteriaSortingByMultipAttr_thenException() {
		Session sess = null;
		SessionFactory sf = new Configuration().
				configure().buildSessionFactory();
		sess = sf.openSession();
		List<Foo> fooList = new ArrayList();  
		try{
			sess.beginTransaction();
			Criteria criteria = sess.createCriteria(Foo.class, "FOO");
			criteria.addOrder(Order.desc("name").nulls(NullPrecedence.FIRST));
			criteria.addOrder(Order.asc("idx"));
			boolean thrown = false;
			try {
				fooList = criteria.list();
			} catch (org.hibernate.PropertyAccessException e) {
			    thrown = true;
			}
			assertTrue(thrown);
			

			sess.getTransaction().commit();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(sess != null){
				sess.close();
			}
		}
	}
}

}
