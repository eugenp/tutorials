package app.had.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages=
{"app.had.demo.in.adapter","app.had.demo.out.adapter",
 "app.had.demo.in.port", "app.had.demo.out.port",
 "app.had.demo.domain.core"})
public class HadApplication {

	public static void main(String[] args) {
		SpringApplication.run(HadApplication.class, args);
	}

}
