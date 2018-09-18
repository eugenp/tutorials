package definition;

import com.baeldung.definition.Config;
import com.baeldung.definition.domain.Company;
import com.baeldung.definition.domain.Person;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class SpringBeanIntegrationTest {
    @Test
    public void whenObtainingBeansByName_thenDependenciesAreInjected() {
        ApplicationContext context = new ClassPathXmlApplicationContext("/bean-definition.xml");
        Person person = context.getBean("person", Person.class);
        assertEquals("John Doe", person.getFullName());
        assertEquals("Pennsylvania Avenue", person.getAddress().getStreet());
        assertEquals(1600, person.getAddress().getNumber());
    }

    @Test
    public void whenAutowiringBeans_thenDependenciesAreInjected() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        Company company = context.getBean("company", Company.class);
        assertEquals("High Street", company.getAddress().getStreet());
        assertEquals(1000, company.getAddress().getNumber());
        assertEquals(1, company.getAccount().getNumber());
        assertEquals(BigDecimal.valueOf(1000.00), company.getAccount().getBalance());
    }
}
