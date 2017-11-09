package com.baeldung.ditypes.constructordi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.ditypes.constructordi.domain.Office;
import com.baeldung.ditypes.constructordi.domain.Residence;

public class SpringRunner {

    public static void main(String[] args) {

        ApplicationContext appContext = new AnnotationConfigApplicationContext(Config.class);

        Residence johnsResidence = appContext.getBean(Residence.class);

        System.out.println("John's Residence Details : " + johnsResidence);

        Office johnsOffice = appContext.getBean(Office.class);

        System.out.println("John's Office Details : " + johnsOffice);

    }
}
