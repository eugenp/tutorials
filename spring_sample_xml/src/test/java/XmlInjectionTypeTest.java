import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.CustomerService;
import service.CustomerServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:/spring")
public class XmlInjectionTypeTest {

    public final static String CUSTOMER_NAME = "Alin";

    @Test
    public final void givenXMLConfigFile_whenUsingConstructorBasedBeanInjection_thenFindCustomerName(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContextForConstructorInjection.xml");
        CustomerServiceImpl customerService = (CustomerServiceImpl) applicationContext.getBean("customerService", CustomerService.class);

        Assert.assertEquals(CUSTOMER_NAME, customerService.findAll().get(0).getName());
    }

    @Test
    public final void givenXMLConfigFile_whenUsingSetterBasedBeanInjection_thenFindCustomerName(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContextForSetterInjection.xml");
        CustomerServiceImpl customerService = (CustomerServiceImpl) applicationContext.getBean("customerService");

        Assert.assertEquals(CUSTOMER_NAME, customerService.findAll().get(0).getName());
    }

}
