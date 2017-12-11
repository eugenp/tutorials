package dipattern;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.dipattern.Employee;
import com.baeldung.dipattern.Student;

public class TestDiPattern {
    ApplicationContext context = null;

    @Before
    public void setup() {
        context = new ClassPathXmlApplicationContext("diPatternContext.xml");
    }

    @Test
    public void constructorInjectionUnitTest() {
        Student student = (Student) context.getBean(Student.class);
        int id = student.getId();
        assertEquals(1, id);

        String name = student.getName();
        assertEquals("abc", name);
    }

    @Test
    public void setterInjectionUnitTest() {
        Employee employee = (Employee) context.getBean(Employee.class);
        int id = employee.getId();
        assertEquals(1, id);

        String name = employee.getName();
        assertEquals("def", name);

    }

}
