package com.baeldung.beansinjectiontypes.setter;

import org.springframework.context.support.FileSystemXmlApplicationContext;

public class HornCheckerXML {

    public static void main(String[] args) {
        try (FileSystemXmlApplicationContext context = 
              new FileSystemXmlApplicationContext("src/main/resources/beansinjectiontypes-ctx.xml")) {
            Dealership dealership = (Dealership) context.getBean("setterDealership");

            dealership.getCar().honk();
        }
    }
}
