package com.baeldung.configurationproperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = {Database.class,DatabaseConfig.class})
public class DatabaseConfigPropertiesApp{

    public static void main(String[]args) {SpringApplication.run(DatabaseConfigPropertiesApp.class,args);}

}