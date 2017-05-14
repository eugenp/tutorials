import org.baeldung.spring.bean.injection.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public Person person() {
        return new Person();
    }
}