package com.baeldung.servletinitializer;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class WarInitializerApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WarInitializerApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication sa = new SpringApplication(WarInitializerApplication.class);
        sa.setLogStartupInfo(false);
        sa.run(args);
    }

    @RestController
    public static class WarInitializerController {

        @RequestMapping("/")
        public String handler(Model model) {
            model.addAttribute("date", LocalDateTime.now());
            return "WarInitializerApplication is up and running!";
        }
    }
}
