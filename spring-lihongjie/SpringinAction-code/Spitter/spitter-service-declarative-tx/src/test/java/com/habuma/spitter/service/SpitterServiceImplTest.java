package com.habuma.spitter.service;

import static org.easymock.EasyMock.*;

import org.junit.Before;
import org.junit.Test;

import com.habuma.spitter.domain.Spitter;
import com.habuma.spitter.persistence.SpitterDao;

public class SpitterServiceImplTest {
  private SpitterServiceImpl spitterService;
  private SpitterDao spitterDao;
  private Spitter newSpitter;
  private Spitter oldSpitter;
  
  @Before
  public void setup() {
    newSpitter = new Spitter();
    newSpitter.setUsername("testuser");
    newSpitter.setPassword("password");
    newSpitter.setFullName("Michael McTest");

    oldSpitter = new Spitter();
    oldSpitter.setId(12345L);
    oldSpitter.setUsername("olduser");
    oldSpitter.setPassword("letmein");
    oldSpitter.setFullName("Bob O'Test");
    
    spitterDao = createNiceMock(SpitterDao.class);  
    spitterDao.saveSpitter(oldSpitter);
    spitterDao.addSpitter(newSpitter);
    replay(spitterDao);
    
    spitterService = new SpitterServiceImpl();
    spitterService.spitterDao = spitterDao;
  }
  
  @Test
  public void shouldAddSpitter() {
    spitterService.saveSpitter(newSpitter);
    spitterService.saveSpitter(oldSpitter);
    verify(spitterDao);
  }
}
