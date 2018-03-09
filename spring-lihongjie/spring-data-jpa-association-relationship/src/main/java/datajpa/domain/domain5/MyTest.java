package datajpa.domain.domain5;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import datajpa.repository.User5Repository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:exampleApplicationContext-persistence.xml"})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public class MyTest {

    @Autowired
    private User5Repository user5Repository;


    @Test
    public void test1() {
        User user = new User();

        Person person = new Person();
        person.setFirstname("Li");
        person.setLastname("Hongjie");

        user.setPerson(person);

        user5Repository.save(user);
    }


}
