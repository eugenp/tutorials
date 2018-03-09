package com.springinaction.springidol;

import static org.junit.Assert.*;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("spring-idol.xml")
public class AnnotationConfigTest {
  @Inject
  @Named("eddie")
  private Instrumentalist eddie;

  @Inject
  private Instrument instrument;
  
  @Inject
  private KnifeJuggler juggler;

  @Test
  public void shouldWireWithAtInject() {
    assertNotNull(eddie.getInstrument());
    assertEquals(instrument, eddie.getInstrument());
  }
  
  @Test
  public void shouldWireViaProvider() {
    assertNotNull(juggler);
    assertNotNull(juggler.getKnives());
    assertEquals(5, juggler.getKnives().size());
  }
}
