package com.habuma.spitter.persistence;

import static org.junit.Assert.*;
import static org.springframework.test.jdbc.SimpleJdbcTestUtils.*;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.habuma.spitter.domain.Spitter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:persistence-context.xml", 
        "classpath:test-dataSource-context.xml",
        "classpath:test-transaction-context.xml"
        })
        
@TransactionConfiguration(transactionManager="txMgr", defaultRollback=true)
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public abstract class AbstractSpitterDaoTest {

  @Autowired
  private SimpleJdbcTemplate jdbcTemplate;
  
  @Autowired
  private SpitterDao dao;
  
  @After
  public void cleanup() {
    deleteFromTables(jdbcTemplate, "spitter");
  }
  
  @Test
  public void shouldCreateRowsAndSetIds() {
    assertEquals(0, countRowsInTable(jdbcTemplate, "spitter"));    
    insertASpitter("username", "password", "fullname", "email", false);
    
    assertEquals(1, countRowsInTable(jdbcTemplate, "spitter"));
    
    insertASpitter("username2", "password2", "fullname2", "email2", false);
    assertEquals(2, countRowsInTable(jdbcTemplate, "spitter"));
  }  
  
  @Test
  public void shouldBeAbleToFindInsertedSpitter() {
    Spitter spitterIn = insertASpitter("username", "password", "fullname", "email", false);
    
    Spitter spitterOut = dao.getSpitterById(spitterIn.getId());

    assertEquals(spitterIn, spitterOut);
  }

  private Spitter insertASpitter(String username, String password, String fullname, String email, boolean updateByEmail) {
    Spitter spitter = new Spitter();
    spitter.setUsername(username);
    spitter.setPassword(password);
    spitter.setFullName(fullname);
    spitter.setEmail(email);
    spitter.setUpdateByEmail(updateByEmail);
    assertNull(spitter.getId());
    dao.addSpitter(spitter);
    assertNotNull(spitter.getId());
    return spitter;
  }}
