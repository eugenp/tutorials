package com.baeldung.beaninjection;

public class AnotherSampleDAOBean implements IAnotherSampleDAO {

	 	private String propertyY;
	    
		public AnotherSampleDAOBean(String propertyY){
			this.propertyY=propertyY;
		}

		public String getPropertyY() {
			return propertyY;
		}
		
}
