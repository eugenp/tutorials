import org.baeldung.spring.bean.injection.PersonLister;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

@ComponentScan
public class AnnotationIntegrationTest {
    private ApplicationContext context = null;

    @Before
    public void setUp() {
        context = new ClassPathXmlApplicationContext("Beans.xml");
    }

    @Test
    public void test() {
        PersonLister lister = (PersonLister) context.getBean("personLister");
        Assert.assertThat(lister.printName(), is(equalTo("John Doe")));
    }
}
