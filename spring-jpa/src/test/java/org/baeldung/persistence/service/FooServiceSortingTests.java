package org.baeldung.persistence.service;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cc.jpa.example.Foo;
public class FooServiceSortingTests {
private EntityManager entityManager;	
  
	@BeforeClass
	public static void before(){
		
	       
	}

	 @After
	    public final void after() {
	       
	 }
	
    @Test
    public final void whenSortingByOneAttributeDefault_thenSortedResult() {
			
        Query sortQuery = entityManager.createQuery
            ("Select f from Foo as f order by f.id");
        List<Foo> fooList = sortQuery.getResultList();
        for(Foo foo:fooList){
				System.out.println("Name:"+foo.getName()+"-------Id:"+foo.getId());
        }
			
	}
	 
	@Test
	public final void whenSortingByOneAttribute_thenSortedResult() {
		
		Query sortQuery = entityManager.createQuery
        	    ("Select f from Foo as f order by f.id desc");
		List<Foo> fooList = sortQuery.getResultList();
		for(Foo foo:fooList){
			System.out.println("Name:"+foo.getName()+"-------Id:"+foo.getId());
		}
		
	}
	
	@Test
	public final void whenSortingByTwoAttributes_thenSortedResult() {
		
		Query sortQuery = entityManager.createQuery
        	    ("Select f from Foo as f order by f.name asc, f.id desc");
		List<Foo> fooList = sortQuery.getResultList();
		for(Foo foo:fooList){
			System.out.println("Name:"+foo.getName()+"-------Id:"+foo.getId());
		}
		
	}
    
     @Test
     public final void whenSortingFooWithCriteria_thenSortedFoos(){
         
         Root<Foo> from = criteriaQuery.from(Foo.class); 
         CriteriaQuery<Foo> select = criteriaQuery.select(from);
         criteriaQuery.orderBy(criteriaBuilder.asc(from.get("name")));
         TypedQuery<Foo> typedQuery = entityManager.createQuery(select);
         List<Foo>fooList = typedQuery.getResultList();
         for(Foo foo:fooList){
             System.out.println("Name:"+foo.getName()+"--------Id:"+foo.getId());
         }
     
     }
    
     @Test
     public final void whenSortingFooWithCriteriaAndMultipleAttributes_thenSortedFoos(){
     
         Root<Foo> from = criteriaQuery.from(Foo.class);
         CriteriaQuery<Foo> select = criteriaQuery.select(from);
         criteriaQuery.orderBy(criteriaBuilder.asc(from.get("name")), criteriaBuilder.desc(from.get("id")));
         TypedQuery<Foo> typedQuery = entityManager.createQuery(select);
         List<Foo>fooList = typedQuery.getResultList();
         for(Foo foo:fooList){
            System.out.println("Name:"+foo.getName()+"-------Id:"+foo.getId());
         }
     }
    
    

}
