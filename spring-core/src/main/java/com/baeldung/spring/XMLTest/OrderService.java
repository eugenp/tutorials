package com.baeldung.spring.XMLTest;
  
public class OrderService {

		 
		private MessageService messageService;
		private OrderValidationService validationService;
		
		public OrderService(){
			
		}
		public OrderService(OrderValidationService service)
		{
			 
			this.validationService=service;
			
		}
		public MessageService getMessageService() {
			return messageService;
		}


		public void setMessageService(MessageService messageService) {
			this.messageService = messageService;
		}


		public void saveOrder()
		{
			System.out.println("========= Testing XML based Bean injection =============");
			validationService.doOrderValidation();
			messageService.sendMessage();
			
		}
		
}
