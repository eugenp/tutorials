package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*@Controller*/
@SpringBootApplication
public class SampleController {
/*
    @RequestMapping("/")

    String home() {
        return "index";
    }*/

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleController.class, args);
    }
}