package com.baeldung.SpringBeanInjectionExample;

import com.baeldung.SpringBeanInjectionExample.controller.BaeldungController;
import com.baeldung.SpringBeanInjectionExample.dao.BaeldungDao;
import com.baeldung.SpringBeanInjectionExample.service.BaeldungService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(locations = {"file:src/main/resources/Beans.xml"})
public class SpringBeanInjectionExampleApplicationTests {

	@Autowired
	private ApplicationContext context;
	//
	private BaeldungDao baeldungDao;
	//
	private BaeldungService baeldungService;
	//
	private BaeldungController baeldungController;

	@Test
	public void contextLoads() {
//		context = new FileSystemXmlApplicationContext("src/main/resources/Beans.xml");
		assertNotNull(context);
	}

	@Test
	public  void testDao(){
		BaeldungDao baeldungDao = (BaeldungDao)context.getBean("baeldungDao");
		assertNotNull(baeldungDao);
		assertNotNull(baeldungDao.getWordpress());
		assertEquals(baeldungDao.getWordpress(),"www.baeldung.com");
	}

	@Test
	public  void testService(){
		baeldungService = (BaeldungService)context.getBean("baeldungService");
		assertNotNull(baeldungService);
	}

	@Test
	public  void testController(){
		baeldungController = (BaeldungController)context.getBean("baeldungController");
		assertNotNull(baeldungController);
	}



}
