package com.baeldung.exitcode.event;

import org.springframework.boot.ExitCodeEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class ExitCodeEventDemoApplication {

    public static void main(String[] args) {
        System.exit(
            SpringApplication.exit(
                SpringApplication.run(ExitCodeEventDemoApplication.class, args)
            )
        );
    }

    @Bean
    DemoListener demoListenerBean() {
        return new DemoListener();
    }

    private static class DemoListener {
        @EventListener
        public void exitEvent(ExitCodeEvent event) {
            System.out.println("Exit code: " + event.getExitCode());
        }
    }
}
