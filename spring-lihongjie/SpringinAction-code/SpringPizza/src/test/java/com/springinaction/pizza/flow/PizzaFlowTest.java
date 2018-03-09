package com.springinaction.pizza.flow;

import static org.mockito.Mockito.*;

import org.springframework.webflow.config.FlowDefinitionResource;
import org.springframework.webflow.config.FlowDefinitionResourceFactory;
import org.springframework.webflow.test.MockFlowBuilderContext;
import org.springframework.webflow.test.execution.AbstractXmlFlowExecutionTests;

import com.springinaction.pizza.domain.Customer;
import com.springinaction.pizza.service.CustomerNotFoundException;

public class PizzaFlowTest extends AbstractXmlFlowExecutionTests {
  protected FlowDefinitionResource getResource(
          FlowDefinitionResourceFactory resourceFactory) {
    return resourceFactory.createResource(
         "file:src/main/webapp/WEB-INF/flows/pizza/pizza-flow.xml");
  }
  
  
  protected void configureFlowBuilderContext(
          MockFlowBuilderContext builderContext) {
        
    PizzaFlowActions pizzaFlowActions = mock(PizzaFlowActions.class);
    try {
      when(pizzaFlowActions.lookupCustomer("9725551234")).
              thenReturn(new Customer("9725551234"));
      when(pizzaFlowActions.lookupCustomer("5051231234")).
              thenThrow(new CustomerNotFoundException());
    } catch (CustomerNotFoundException e) {}
    
    builderContext.registerBean("pizzaFlowActions", pizzaFlowActions);    
  }

  
//  public void testStartPizzaFlow() {
//    startFlow(new MockExternalContext());
//    assertCurrentStateEquals("welcome");
//  }
//  
//  public void testKnownPhoneEnteredEventFromWelcomeState(){
//    startFlow(new MockExternalContext());
//    
//    MockExternalContext context = new MockExternalContext();
//    context.putRequestParameter("phoneNumber", "9725551234");
//    context.setEventId("phoneEntered");
//      
//    setCurrentState("welcome");
//    resumeFlow(context);
//    assertCurrentStateEquals("showOrder");
//
//    Order order = (Order) getFlowAttribute("order");
//    assertEquals("9725551234", order.getCustomer().getPhoneNumber());
//  }
//  
//  public void testUnknownPhoneEnteredEventFromWelcomeState(){
//    startFlow(new MockExternalContext());
//    
//    MockExternalContext context = new MockExternalContext();
//    context.putRequestParameter("phoneNumber", "5051231234");
//    context.setEventId("phoneEntered");
//      
//    setCurrentState("welcome");
//    resumeFlow(context);
//    assertCurrentStateEquals("registrationForm");
//  }
//  
//  public void testShouldTransitionFromShowOrderToCreatePizza(){
//    startFlow(new MockExternalContext());
//    
//    MockExternalContext context = new MockExternalContext();
//    context.setEventId("createPizza");
//      
//    setCurrentState("showOrder");
//    resumeFlow(context);
//    assertCurrentStateEquals("createPizza");
//    assertNotNull(getFlowAttribute("pizza"));
//  }
//  
//  public void testShouldAddPizzaOnAddPizzaEvent(){
//    startFlow(new MockExternalContext());
//    setCurrentState("createPizza");
//    getFlowScope().put("pizza", new Pizza());
//    MockExternalContext context = new MockExternalContext();
//    context.putRequestParameter("toppings", "TOMATO");
//    context.setEventId("addPizza");
//    resumeFlow(context);
//    
//    assertCurrentStateEquals("showOrder");
//    Order order = (Order) getFlowAttribute("order");
//    List<Pizza> pizzas = order.getPizzas();
//    assertEquals(1, pizzas.size());
////    assertEquals(1, pizzas.get(0).getToppings().size());
//  }
//  
//  public void testShouldNotAddPizzaOnCancelEvent() {
//    startFlow(new MockExternalContext());
//    setCurrentState("createPizza");
//    MockExternalContext context = new MockExternalContext();
//    context.setEventId("cancel");
//    resumeFlow(context);
//    
//    assertCurrentStateEquals("showOrder");
//    Order order = (Order) getFlowAttribute("order");
//    assertEquals(0, order.getPizzas().size());
//  } 
  
  
}
