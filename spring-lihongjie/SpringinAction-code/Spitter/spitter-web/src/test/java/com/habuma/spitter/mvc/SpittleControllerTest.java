package com.habuma.spitter.mvc;

import org.junit.Test;


public class SpittleControllerTest {
  private SpittleController controller;
  
  @Test
  public void canary() {}
  
//  @Before
//  public void setup() {
//    controller = new SpittleController();
//  }
//  
//  @Test
//  public void shouldDeleteSpittle() {
//    SpitterService spitterService = mock(SpitterService.class);
//    Spittle spittle = new Spittle();
//    spittle.setId(123L);
//    Spitter spitter = new Spitter();
//    spitter.setUsername("habuma");
//    spittle.setSpitter(spitter);
//    
//    when(spitterService.getSpittleById(123L)).thenReturn(spittle);
//    controller.spitterService = spitterService;
//    
//    String view = controller.deleteSpittle("123");
//    assertEquals("redirect:/home", view);
//    verify(spitterService).deleteSpittle(123L);
//  }
//  
//  @Test
//  public void shouldDisplaySpittleForm() {
//    Map<String, Object> model = new HashMap<String, Object>();
//    controller.showSpittleForm(model);    
//    assertEquals(new Spittle(), model.get("spittle"));
//  }
  
//  @Test
//  public void shouldAddValidSpittle() {
//    Spittle expectedSpittle = new Spittle();
//    expectedSpittle.setText("This is a valid spittle");
//    injectMockSpitterService(expectedSpittle);
//
//    String view = controller.addSpittle(
//            expectedSpittle, createMockBindingResult(false));
//    assertEquals("redirect:/home", view);
//  }
//  
//  @Test
//  public void shouldRediplayFormWhenGivenInvalidSpittle() {
//    Spittle expectedSpittle = new Spittle();
//    expectedSpittle.setText("Not valid");
//    
//    String view = controller.addSpittle(
//            expectedSpittle, createMockBindingResult(true));
//    assertEquals("spittles/form", view);
//  }
//
//  private void injectMockSpitterService(Spittle expectedSpittle) {
//    SpitterService spitterService = mock(SpitterService.class);
//    spitterService.addSpittle(expectedSpittle);    
//    controller.spitterService = spitterService;
//  }
}
