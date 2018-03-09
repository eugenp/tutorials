package com.habuma.spitter.mvc;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;

import com.habuma.spitter.domain.Spitter;
import com.habuma.spitter.domain.Spittle;
import com.habuma.spitter.service.SpitterService;


public class SpitterControllerTest {
  @Test
  public void canary() {}

  
//  private SpitterController controller;
//
//  @Before
//  public void setup() {
//    controller = new SpitterController();
//  }
//  
//  @Test
//  public void shouldShowSpitterForm() {
//    Map<String, Object> model = new HashMap<String, Object>();
//    controller.showSpitterForm(model);
//    Spitter newSpitter = (Spitter) model.get("spitter");
//    assertEquals(new Spitter(), newSpitter);
//  }
//  
//  @Test
//  public void shouldSaveValidSpitter() {
//    Spitter spitter = new Spitter();
//    spitter.setUsername("habuma");
//    spitter.setPassword("password");
//    spitter.setFullName("Craig Walls");
//    spitter.setEmail("craig@habuma.com");
//    spitter.setUpdateByEmail(true);
//
//    SpitterService spitterService = mock(SpitterService.class);
//    spitterService.saveSpitter(spitter);
//    
//    controller.spitterService = spitterService;
//    
//    String view = controller.addSpitter(
//            spitter, createMockBindingResult(false));
//    assertEquals("redirect:/spitter/habuma/spittles", view);
//  }
//  
//  @Test
//  public void shouldRedisplaySpitterFormWhenGivenInvalidSpitter() {
//    Spitter spitter = new Spitter();
//    
//    String view = controller.addSpitter(
//            spitter, createMockBindingResult(true));
//    assertEquals("spitters/form", view);
//  }
//  
  //<start id="java_SpitterControllerTest_shouldPutSpittlesForSpitterIntoModel" />
  @Test
  public void shouldProduceSpittlesForSpitter() {
    Spitter expectedSpitter = new Spitter();
    expectedSpitter.setUsername("habuma");
    
    SpitterService spitterService = mock(SpitterService.class); //<co id="co_mockSpitterService"/> 
    when(spitterService.getSpitter("habuma")).thenReturn(expectedSpitter);

    Spittle spittle1 = new Spittle();
    spittle1.setText("ABC");
    Spittle spittle2 = new Spittle();
    spittle2.setText("XYZ");   
    List<Spittle> expectedSpittles = Arrays.asList(spittle1, spittle2);

    when(spitterService.getSpittlesForSpitter("habuma")).
        thenReturn(expectedSpittles);
    
    SpitterController controller = 
        new SpitterController(spitterService);//<co id="co_createController"/> 
    
    ExtendedModelMap model = new ExtendedModelMap();
    String view = 
        controller.listSpittlesForSpitter("habuma", model); //<co id="co_callMethod"/> 
    
    Assert.assertEquals("spittles/list", view);//<co id="co_assertModel"/> 
    Assert.assertEquals(expectedSpitter, model.get("spitter")); 
    Assert.assertEquals(expectedSpittles, model.get("spittleList"));
    
    verify(spitterService).getSpitter("habuma");
    verify(spitterService).getSpittlesForSpitter("habuma");
  }
  //<end id="java_SpitterControllerTest_shouldPutSpittlesForSpitterIntoModel" />
//  
//  @Test
//  public void shouldHandleEmptyResultSet() {
//    String view = 
//        controller.handleEmptyResultDataAccessException(null);
//    assertEquals("errorpages/404", view);
//  }
}
