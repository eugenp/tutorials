package com.baeldung.injectiontypes.xmlconfig.main;

import com.baeldung.injectiontypes.xmlconfig.domain.constructor.SolarSystemConstructorDi;
import com.baeldung.injectiontypes.xmlconfig.domain.setter.SolarSystemSetterDi;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringXMLConfigDITest {
    public static void main(String[] args) {

        SolarSystemConstructorDi milkyWayConstructorDi = getSolarSystemConstructorDiFromXMLConfig();
        SolarSystemSetterDi milkyWaySetterDi = getSolarSystemSetterDiFromXMLConfig();

        milkyWayConstructorDi.printDiameters();
        milkyWaySetterDi.printDiameters();
    }

    private static SolarSystemConstructorDi getSolarSystemConstructorDiFromXMLConfig() {
        ApplicationContext context = new ClassPathXmlApplicationContext("AppConfigConstructor.xml");
        return context.getBean(SolarSystemConstructorDi.class);
    }

    private static SolarSystemSetterDi getSolarSystemSetterDiFromXMLConfig() {
        ApplicationContext context = new ClassPathXmlApplicationContext("AppConfigSetter.xml");
        return context.getBean(SolarSystemSetterDi.class);
    }
}