package com.springinaction.springidol;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("spring-idol.xml")
public class AspectTest {
  @Autowired
  ApplicationContext context;

  @Test
  public void audienceShouldApplaud() throws Exception {
    Performer eddie = (Performer) context.getBean("eddie");
    eddie.perform();
  }

  @Test
  public void eddieShouldBeAContestant() {
    Contestant eddie = (Contestant) context.getBean("eddie");
    eddie.receiveAward();
  }
}
