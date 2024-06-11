package com.baeldung;

import com.baeldung.data.Employee;
import com.baeldung.data.EmployeeRepository;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Push
@SpringBootApplication
public class Application implements AppShellConfigurator {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner loadData(EmployeeRepository repository) {
        return (args) -> {
            repository.save(new Employee("Bill", "Gates"));
            repository.save(new Employee("Mark", "Zuckerberg"));
            repository.save(new Employee("Sundar", "Pichai"));
            repository.save(new Employee("Jeff", "Bezos"));
        };
    }
}
