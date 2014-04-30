package org.baeldung.persistence.service;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.OrderBy;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.common.util.StringHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cc.jpa.example.Foo;
import com.cc.jpa.example.Bar;


public class FooServiceSortingTests {
	private EntityManager entityManager;	

	@BeforeClass
	public static void before(){
    
	} 

	@After
	public final void after() {

	}

	@Test
	public final void whenSortingByOneAttributeDefaultOrder_thenPrintSortedResult() {

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("punit");
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction entityTransaction = entityManager
				.getTransaction();
		entityTransaction.begin();
		String jql = "Select f from Foo as f order by f.id";
		Query sortQuery = entityManager.createQuery
				(jql);
		List<Foo> fooList = sortQuery.getResultList();
		assertEquals(1,fooList.get(0).getId());
		assertEquals(100,fooList.get(fooList.toArray().length-1).getId());
		for(Foo foo:fooList){
			System.out.println("Name:"+foo.getName()+"-------Id:"+foo.getId());
		}

	}

	@Test
	public final void whenSortingByOneAttributeSetOrder_thenSortedPrintResult() {

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("punit");
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction entityTransaction = entityManager
				.getTransaction();
		entityTransaction.begin();
		String jql = "Select f from Foo as f order by f.id desc";
		Query sortQuery = entityManager.createQuery
				(jql);
		List<Foo> fooList = sortQuery.getResultList();
		assertEquals(100,fooList.get(0).getId());
		assertEquals(1,fooList.get(fooList.toArray().length-1).getId());
		for(Foo foo:fooList){
			System.out.println("Name:"+foo.getName()+"-------Id:"+foo.getId());
		}

	}

	@Test
	public final void whenSortingByTwoAttributes_thenPrintSortedResult() {

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("punit");
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction entityTransaction = entityManager
				.getTransaction();
		entityTransaction.begin();
		String jql = "Select f from Foo as f order by f.name asc, f.id desc";
		Query sortQuery = entityManager.createQuery
				(jql);
		List<Foo> fooList = sortQuery.getResultList();
		for(Foo foo:fooList){
			System.out.println("Name:"+foo.getName()+"-------Id:"+foo.getId());
		}

	}
	
	@Test
	public final void whenSortinfBar_thenPrintBarsSortedWithFoos(){
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("punit");
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction entityTransaction = entityManager
				.getTransaction();
		entityTransaction.begin();
		String jql = "Select b from Bar as b order by b.id";
		
		Query barQuery = entityManager.createQuery(jql);
		List<Bar>barList = barQuery.getResultList();
		for(Bar bar:barList){
			System.out.println("Bar Id:"+bar.getId());
			for(Foo foo:bar.getFooList()){
				System.out.println("FooName:"+foo.getName());
			}
		}
	
	}
	
	@Test
	public final void whenSortingByStringNullLast_thenLastNull() {

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("punit");
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction entityTransaction = entityManager
				.getTransaction();
		entityTransaction.begin();
		String jql = "Select f from Foo as f order by f.name desc NULLS LAST";
		Query sortQuery = entityManager.createQuery
				(jql);
		List<Foo> fooList = sortQuery.getResultList();
		assertNull(fooList.get(fooList.toArray().length-1).getName());
		for(Foo foo:fooList){
			System.out.println("Name:"+foo.getName());
		}
	}
	@Test
	public final void whenSortingByStringNullFirst_thenFirstNull() {

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("punit");
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction entityTransaction = entityManager
				.getTransaction();
		entityTransaction.begin();
		String jql = "Select f from Foo as f order by f.name desc NULLS FIRST";
		Query sortQuery = entityManager.createQuery
				(jql);
		List<Foo> fooList = sortQuery.getResultList();
		assertNull(fooList.get(0).getName());
		for(Foo foo:fooList){
			System.out.println("Name:"+foo.getName()+"-------Id:"+foo.getTest_Null());
		}
	}
	@Test
	public final void whenSortingByIntNull_thenException() {

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("punit");
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction entityTransaction = entityManager
				.getTransaction();
		entityTransaction.begin();
		String jql = "Select f from Foo as f order by f.test_Null desc NULLS FIRST";
		Query sortQuery = entityManager.createQuery
				(jql);
		boolean thrown = false;
		try {
			List<Foo> fooList = sortQuery.getResultList();
		} catch (javax.persistence.PersistenceException e) {
		    thrown = true;
		}
		assertTrue(thrown);
	}
	
	@Test
	public final void whenSortingFooWithCriteria_thenPrintSortedFoos(){
		
		EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("punit");
        EntityManager entityManager = emf.createEntityManager();          
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Foo> criteriaQuery = criteriaBuilder
				.createQuery(Foo.class);
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
	public final void whenSortingFooWithCriteriaAndMultipleAttributes_thenPrintSortedFoos(){
		
		EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("punit");
        EntityManager entityManager = emf.createEntityManager();          
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Foo> criteriaQuery = criteriaBuilder
				.createQuery(Foo.class);
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
