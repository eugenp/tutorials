package util;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"view","core","dao"})
@EntityScan(basePackages = {"core"})
public class SpringBootHexagonalDemoApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootHexagonalDemoApp.class, args);
	}

}
