package com.baeldung.dependencyinjection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.baeldung.dependencyinjection.domain.Building;

public class DependencyInjectionRunner {

    public static void main(String[] args) {

        Building building = getBuilding();

        System.out.println(building);
    }

    private static Building getBuilding() {
     
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        return context.getBean(Building.class);
    }
}
