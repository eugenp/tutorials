import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.CustomerService;

public class Application {

    public static void main(String[] args){

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SetterConfig.class);
        CustomerService service = applicationContext.getBean("customerService", CustomerService.class);
        System.out.println(service.findAll().get(0).getName());
    }
}
