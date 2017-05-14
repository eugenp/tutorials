import org.baeldung.spring.bean.injection.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class JavaIntegrationTest {
    private ApplicationContext context = null;

    @Before
    public void setUp() {
        context = new AnnotationConfigApplicationContext(Person.class);
    }

    @Test
    public void test() {
        Person person = context.getBean(Person.class);
        person.setName("John Doe");
        Assert.assertThat(person.getName(), is(equalTo("John Doe")));
    }
}
