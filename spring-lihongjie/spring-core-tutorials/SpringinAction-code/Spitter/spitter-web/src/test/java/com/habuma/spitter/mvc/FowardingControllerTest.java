package com.habuma.spitter.mvc;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FowardingControllerTest {
  private ForwardingController controller;

  @Before
  public void setup() {
    controller = new ForwardingController();
  }
  
  @Test
  public void shouldForwardToSpittersPage() {
    assertEquals("forward:/app/spitters/habuma", 
            controller.showSpittlesForSpitter("habuma"));
  }
  
  @Test
  public void shouldForwardToSignupForm() {
    assertEquals("forward:/app/spitters/form", controller.signup());
  }
}
