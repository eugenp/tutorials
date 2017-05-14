package simple.example.spring_depencdency_injection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("config.xml");
        // Example for constructor based dependency injection
        Library lib = (Library)appContext.getBean("lib");
        lib.printInjectedBook();
    }
}
