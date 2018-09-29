package org.baeldung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.annotation.servletcomponentscan.SpringBootAnnotatedApp;
import com.baeldung.annotation.servletcomponentscan.SpringBootPlainApp;
import com.baeldung.git.CommitIdApplication;
import com.baeldung.internationalization.InternationalizationApp;
import com.baeldung.intro.App;
import com.baeldung.servlets.ApplicationMain;
import com.baeldung.webjar.WebjarsdemoApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SpringBootAnnotatedApp.class, SpringBootPlainApp.class, CommitIdApplication.class,
		InternationalizationApp.class, App.class, ApplicationMain.class, Application.class,
		WebjarsdemoApplication.class })
public class SpringContextIntegrationTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
