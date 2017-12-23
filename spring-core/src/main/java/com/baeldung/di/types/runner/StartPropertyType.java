package com.baeldung.di.types.runner;

import com.baeldung.di.types.xml.Department;
import com.baeldung.di.types.xml.Secretary;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Objects;

/**
 * Created by hakan on 22/12/2017
 */
public class StartPropertyType {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("property-di-type.xml");

        Department department = context.getBean(Department.class);
        Objects.requireNonNull(department);
        System.out.println(department);

        Secretary secretary = context.getBean(Secretary.class);
        Objects.requireNonNull(secretary);
        System.out.println(secretary);

    }
}
