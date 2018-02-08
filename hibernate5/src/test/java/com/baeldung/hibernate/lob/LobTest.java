package com.baeldung.hibernate.lob;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.hibernate.lob.model.User;

public class LobTest {

	private EntityManager em;
	
	@Before
	public void init(){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("lob-example"); 
		this.em = factory.createEntityManager();
	}
	
	@After
	public void close(){
		if(this.em != null) em.close();
	}
	
	@Test
	public void givenValidInsertLobObject_whenQueried_returnSameDataAsInserted(){
		User user = new User(); 
		try(InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("profile.png");) { 
			// Get Image file from the resource 
			if(inputStream == null) fail("Unable to get resources"); 
			user.setId("1"); 
			user.setName("User"); 
			user.setPhoto(IOUtils.toByteArray(inputStream)); 
			
			em.getTransaction().begin(); 
			em.persist(user); 
			em.getTransaction().commit(); 
		} catch (IOException e) { 
			fail("Unable to read input stream"); 
		}
		
		User result = em.find(User.class, "1");
		
		assertNotNull("Query result is null", result); 
		assertEquals("User's name is invalid", user.getName(), result.getName() ); 
		assertTrue("User's photo is corrupted", Arrays.equals(user.getPhoto(), result.getPhoto()) );
	}
}
