package com.baeldung.shutdown;

import com.baeldung.autoconfiguration.MySQLAutoconfiguration;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.security.RolesAllowed;

@SpringBootApplication(exclude = MySQLAutoconfiguration.class)
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
//        closeApplication();
//        exitApplication();
//        writePID();

    }

    private static void closeApplication() {

        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(Application.class).web(false).run();
        System.out.println("Spring Boot application started");
        ctx.getBean(TerminateBean.class);
        ctx.close();
    }

    private static void exitApplication() {

        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(Application.class).web(false).run();

        int exitCode = SpringApplication.exit(ctx, new ExitCodeGenerator() {
            @Override
            public int getExitCode() {
                // return the error code
                return 0;
            }
        });

        System.out.println("Exit Spring Boot");

        System.exit(exitCode);
    }

    private static void writePID() {
        SpringApplicationBuilder app = new SpringApplicationBuilder(Application.class).web(false);
        app.build().addListeners(new ApplicationPidFileWriter("./bin/shutdown.pid"));
        app.run();
    }
}
