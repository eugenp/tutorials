package com.baeldung.springbootadminserver;

import com.baeldung.springbootadminserver.configs.NotifierConfiguration;
import de.codecentric.boot.admin.notify.Notifier;
import de.codecentric.boot.admin.notify.RemindingNotifier;
import de.codecentric.boot.admin.notify.filter.FilteringNotifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { NotifierConfiguration.class }, webEnvironment = NONE)
public class NotifierConfigurationIntegrationTest {

    @Autowired private ApplicationContext applicationContext;

    @Test
    public void whenApplicationContextStart_ThenNotifierBeanExists() {
        Notifier notifier = (Notifier) applicationContext.getBean("notifier");
        assertNotEquals(notifier, null);
    }

    @Test
    public void whenApplicationContextStart_ThenFilteringNotifierBeanExists() {
        FilteringNotifier filteringNotifier = (FilteringNotifier) applicationContext.getBean("filteringNotifier");
        assertNotEquals(filteringNotifier, null);
    }

    @Test
    public void whenApplicationContextStart_ThenRemindingNotifierBeanExists() {
        RemindingNotifier remindingNotifier = (RemindingNotifier) applicationContext.getBean("remindingNotifier");
        assertNotEquals(remindingNotifier, null);
    }

}
