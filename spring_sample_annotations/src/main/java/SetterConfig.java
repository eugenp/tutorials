import org.springframework.context.annotation.Bean;
import repository.CustomerRepository;
import repository.CustomerRepositoryImpl;
import service.CustomerService;
import service.CustomerServiceImpl;

public class SetterConfig {
    @Bean(name = "customerService")
    public CustomerService getCustomerService(){
        //setter injection
        CustomerServiceImpl service = new CustomerServiceImpl();
        service.setCustomerRepository(getCustomerRepository());
        return service;
    }

    @Bean(name = "customerRepository")
    public CustomerRepository getCustomerRepository(){
        return new CustomerRepositoryImpl();
    }
}
