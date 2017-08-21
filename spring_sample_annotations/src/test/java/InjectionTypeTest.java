import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.CustomerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:/spring")
public class InjectionTypeTest {

    private static final String CUSTOMER_NAME = "Alin";

    @Test
    public void givenJavaConfigFile_whenUsingConstructorBasedBeanInjection_thenFindCustomerName() {

        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext();
        configApplicationContext.register(ConstructorConfig.class);
        configApplicationContext.refresh();

        CustomerService customerService = configApplicationContext.getBean(CustomerService.class);

        Assert.assertEquals(CUSTOMER_NAME, customerService.findAll().get(0).getName());
    }

    @Test
    public void givenJavaConfigFile_whenUsingSetterBasedBeanInjection_thenFindCustomerName() {

        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext();
        configApplicationContext.register(SetterConfig.class);
        configApplicationContext.refresh();

        CustomerService customerService = configApplicationContext.getBean(CustomerService.class);

        Assert.assertEquals(CUSTOMER_NAME, customerService.findAll().get(0).getName());
    }
}
