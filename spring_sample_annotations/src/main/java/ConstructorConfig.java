import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.CustomerRepository;
import repository.CustomerRepositoryImpl;
import service.CustomerService;
import service.CustomerServiceImpl;

@Configuration
public class ConstructorConfig {

    @Bean(name = "customerService")
    public CustomerService getCustomerService(){
        //constructor injection
       CustomerServiceImpl service = new CustomerServiceImpl(getCustomerRepository());
        return service;
    }

    @Bean(name = "customerRepository")
    public CustomerRepository getCustomerRepository(){
        return new CustomerRepositoryImpl();
    }
}
