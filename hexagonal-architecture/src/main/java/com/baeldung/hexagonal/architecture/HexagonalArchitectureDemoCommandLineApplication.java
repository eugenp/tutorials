package com.baeldung.hexagonal.architecture;

import com.baeldung.hexagonal.architecture.domain.Student;
import com.baeldung.hexagonal.architecture.interaction.HexagonalArchitectureDemoCommandLineController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@ComponentScan("com.baeldung.hexagonal.architecture")
public class HexagonalArchitectureDemoCommandLineApplication implements ApplicationRunner {
  private final Logger logger =
      LoggerFactory.getLogger(HexagonalArchitectureDemoCommandLineApplication.class);

  @Autowired
  private HexagonalArchitectureDemoCommandLineController
      hexagonalArchitectureDemoCommandLineController;

  public static void main(String... args) throws Exception {
    SpringApplication.run(HexagonalArchitectureDemoCommandLineApplication.class, args);
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    String operationToPerform = args.getOptionValues("operation").get(0);

    Student student = null;
    switch (operationToPerform) {
      case "GET":
        String id = args.getOptionValues("id").get(0);

        student = hexagonalArchitectureDemoCommandLineController.getStudent(id);
        break;
      case "CREATE":
        String name = args.getOptionValues("name").get(0);
        String age = args.getOptionValues("age").get(0);

        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("name", name);
        requestParameters.put("age", age);

        student = hexagonalArchitectureDemoCommandLineController.createStudent(requestParameters);
        break;
    }

    logger.debug("student => {}", student);
  }
}
