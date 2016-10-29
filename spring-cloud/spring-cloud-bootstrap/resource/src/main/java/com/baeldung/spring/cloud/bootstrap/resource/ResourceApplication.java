package com.baeldung.spring.cloud.bootstrap.resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class ResourceApplication {
        public static void main(String[] args) {
                SpringApplication.run(ResourceApplication.class, args);
        }

        @Value("${resource.returnString}")
        private String returnString;

        @Value("${resource.user.returnString}")
        private String userReturnString;

        @Value("${resource.admin.returnString}")
        private String adminReturnString;

        @RequestMapping("/hello/cloud")
        public String getString() {
                return returnString;
        }

        @RequestMapping("/hello/user")
        public String getUserString() {
                return userReturnString;
        }

        @RequestMapping("/hello/admin")
        public String getAdminString() {
                return adminReturnString;
        }
}
