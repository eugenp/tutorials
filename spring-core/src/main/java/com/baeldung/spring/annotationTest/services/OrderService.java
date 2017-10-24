package com.baeldung.spring.annotationTest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderService {

		@Autowired
		private MessageService messageService; //This example is Autowired on field.
		
		private OrderValidationService orderValidation;
		 
		//Instead of using autowired on field, we can use @Autowired on setter method.
		//Below is the code.
		/*
		private MessageService messageService;
		
		public MessageService getMessageService() {
			return messageService;
		}
		
		@Autowired
		public void setMessageService(MessageService messageService) {
			this.messageService = messageService;
		}
		*/
		 
		// Autowired on constructor
		@Autowired
		public OrderService(OrderValidationService orderValidation)
		{
			this.orderValidation=orderValidation;
		}
		public void saveOrder()
		{
			System.out.println("========= Testing annotation based Bean injection =============");
			orderValidation.doOrderValidation();
			messageService.sendMessage();
		}
		
}
