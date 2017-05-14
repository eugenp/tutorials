import org.baeldung.spring.bean.injection.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class XmlIntegrationTest {

    private ApplicationContext context = null;

    @Before
    public void setUp() {
        context = new ClassPathXmlApplicationContext("Beans.xml");
    }

    @Test
    public void test() {
        Person person = (Person) context.getBean("person");
        Assert.assertThat(person.getName(), is(equalTo("John Doe")));
    }

}
