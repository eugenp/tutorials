package com.baeldung.injectiontypes.javaconfig.main;

import com.baeldung.injectiontypes.javaconfig.config.AppConfig;
import com.baeldung.injectiontypes.javaconfig.constructor.SolarSystemConstructorDi;
import com.baeldung.injectiontypes.javaconfig.setter.SolarSystemSetterDi;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringJavaConfigDITest {
    public static void main(String[] args) {
        SolarSystemConstructorDi milkyWayConstructorDi = getSolarSystemConstructorDiFromJavaConfig();
        SolarSystemSetterDi milkyWaySetterDi = getSolarSystemSetterDiFromJavaConfig();

        milkyWayConstructorDi.printDiameters();
        milkyWaySetterDi.printDiameters();
    }

    private static SolarSystemConstructorDi getSolarSystemConstructorDiFromJavaConfig() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        return context.getBean(SolarSystemConstructorDi.class);
    }

    private static SolarSystemSetterDi getSolarSystemSetterDiFromJavaConfig() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        return context.getBean(SolarSystemSetterDi.class);
    }
}