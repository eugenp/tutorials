package com.baeldung.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class TestBeanInjection {

    public static void main(String[] args) {
	    ApplicationContext context = new FileSystemXmlApplicationContext(
          "WebContent/WEB-INF/beanConfig.xml");
	   
        Country countryobj = (Country)context.getBean("countryBean");
	    countryobj.stateName();
	   }

}
