package com.baeldung.beaninjectiontypes.domain;

import com.baeldung.beaninjectiontypes.Config;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringRunner {
    public static void main(String[] args) {
        ComputerConstructorInjection computerCI = getComputerCIFromJavaConfig();
        System.out.println(computerCI);

        computerCI = getComputerCIFromXml();
        System.out.println(computerCI);

        ComputerSetterInjection computerSI = getComputerSIFromJavaConfig();
        System.out.println(computerSI);

        computerSI = getComputerSIFromXml();
        System.out.println(computerSI);
    }

    private static ComputerConstructorInjection getComputerCIFromJavaConfig() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        return context.getBean(ComputerConstructorInjection.class);
    }

    private static ComputerConstructorInjection getComputerCIFromXml() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beaninjectiontypes.xml");

        return context.getBean(ComputerConstructorInjection.class);
    }

    private static ComputerSetterInjection getComputerSIFromJavaConfig() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        return context.getBean(ComputerSetterInjection.class);
    }

    private static ComputerSetterInjection getComputerSIFromXml() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beaninjectiontypes.xml");

        return context.getBean(ComputerSetterInjection.class);
    }
}