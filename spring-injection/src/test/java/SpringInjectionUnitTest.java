import com.baeldung.spring.di.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class SpringInjectionUnitTest {

    @Test
    public void givenResourceAnnotationAndXML_WhenConstructorBasedApplication_ThenDependencyValid() {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        ConstructorApplication app = context.getBean(ConstructorApplication.class);

        assertThat(app.getMessageService()).isInstanceOf(FacebookService.class);


        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        ConstructorApplication appXML = (ConstructorApplication) applicationContext.getBean("ConstructorApplication");

        assertThat(appXML.getMessageService()).isInstanceOf(WhatsappService.class);
    }

    @Test
    public void givenResourceAnnotationAndXML_WhenSetterBasedApplication_ThenDependencyValid() {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        SetterApplication app = context.getBean(SetterApplication.class);

        assertThat(app.getMessageService()).isInstanceOf(WhatsappService.class);


        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        SetterApplication appXML = (SetterApplication) applicationContext.getBean("SetterApplication");

        assertThat(appXML.getMessageService()).isInstanceOf(WhatsappService.class);
    }


    @Test
    public void givenResourceAnnotationAndXML_WhenLookupMethodApplication_ThenDependencyValid() {


        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        LookupMethodApplication lookupApp = (LookupMethodApplication) applicationContext.getBean("LookupMethodApplication");

        assertThat(lookupApp.createServiceFromAnnotation()).isInstanceOf(WhatsappService.class);


        ApplicationContext applicationXMLContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        LookupMethodApplication lookupMethodXMLApplication = (LookupMethodApplication) applicationXMLContext.getBean("LookupMethodApplicationByXML");

        assertThat(lookupMethodXMLApplication.createServiceFromXML()).isInstanceOf(WhatsappService.class);
    }
}
