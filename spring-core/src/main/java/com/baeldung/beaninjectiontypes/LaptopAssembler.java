package com.baeldung.beaninjectiontypes;

import com.baeldung.beaninjectiontypes.beans.api.Laptop;
import com.baeldung.beaninjectiontypes.config.SpringConfig;
import com.baeldung.constructordi.Config;
import com.baeldung.constructordi.domain.Car;

import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LaptopAssembler {

    private static ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

    public static void main(String[] args) {

        Laptop dellXps = assembleLaptop("dell");
        Laptop macBookPro = assembleLaptop("macBookPro");
        System.out.println(dellXps);
        System.out.println(macBookPro);
    }

    private static Laptop assembleLaptop(final String laptopName) {

        return context.getBean(laptopName, Laptop.class);
    }
}
