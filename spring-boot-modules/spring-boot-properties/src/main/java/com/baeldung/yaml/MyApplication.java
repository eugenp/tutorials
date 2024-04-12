/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.yaml;

import com.baeldung.yaml.YAMLConfig.Idm;
import com.baeldung.yaml.YAMLConfig.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyApplication implements CommandLineRunner {

    @Autowired
    private YAMLConfig myConfig;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MyApplication.class);
        app.run();
    }

    public void run(String... args) throws Exception {
        System.out.println("using environment:" + myConfig.getEnvironment());
        System.out.println("name:" + myConfig.getName());
        System.out.println("enabled:" + myConfig.isEnabled());
        System.out.println("servers:" + myConfig.getServers());
        
        if ("testing".equalsIgnoreCase(myConfig.getEnvironment())) {
            System.out.println("external:" + myConfig.getExternal());
            System.out.println("map:" + myConfig.getMap());
    
            Idm idm = myConfig.getComponent().getIdm();
            Service service = myConfig.getComponent().getService();
            System.out.println("Idm:");
            System.out.println("   Url: " + idm.getUrl());
            System.out.println("   User: " + idm.getUser()); 
            System.out.println("   Password: " + idm.getPassword());
            System.out.println("   Description: " + idm.getDescription());
            System.out.println("Service:");
            System.out.println("   Url: " + service.getUrl());
            System.out.println("   Token: " + service.getToken()); 
            System.out.println("   Description: " + service.getDescription());
        }
    }

}
