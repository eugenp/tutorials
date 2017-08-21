import model.Customer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.CustomerService;
import service.CustomerServiceImpl;

public class Application {

    public static void main(String[] args){

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContextForConstructorInjection.xml");
        CustomerService service = context.getBean("customerService", CustomerService.class);
        System.out.println(service.findAll().get(0).getName());

    }
}
