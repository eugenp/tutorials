package com.baeldung.timer;

import com.jayway.awaitility.Awaitility;
import org.hamcrest.Matchers;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.io.File;
import java.util.concurrent.TimeUnit;

import static com.jayway.awaitility.Awaitility.await;
import static com.jayway.awaitility.Awaitility.to;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Arquillian.class)
public class ScheduleTimerBeanLiveTest {

    private final static long TIMEOUT = 5000l;
    private final static long TOLERANCE = 1000l;

    @Inject TimerEventListener timerEventListener;

    @Deployment
    public static WebArchive deploy() {
        File[] jars = Maven
          .resolver()
          .loadPomFromFile("pom.xml")
          .resolve("com.jayway.awaitility:awaitility")
          .withTransitivity()
          .asFile();

        return ShrinkWrap
          .create(WebArchive.class)
          .addAsLibraries(jars)
          .addClasses(WithinWindowMatcher.class, TimerEvent.class, TimerEventListener.class, ScheduleTimerBean.class);
    }

    @Test
    public void should_receive_three_pings() {

        Awaitility.setDefaultTimeout(30, TimeUnit.SECONDS);
        await().untilCall(to(timerEventListener.getEvents()).size(), equalTo(3));

        TimerEvent firstEvent = timerEventListener
          .getEvents()
          .get(0);
        TimerEvent secondEvent = timerEventListener
          .getEvents()
          .get(1);
        TimerEvent thirdEvent = timerEventListener
          .getEvents()
          .get(2);

        long delay = secondEvent.getTime() - firstEvent.getTime();
        assertThat(delay, Matchers.is(WithinWindowMatcher.withinWindow(TIMEOUT, TOLERANCE)));
        delay = thirdEvent.getTime() - secondEvent.getTime();
        assertThat(delay, Matchers.is(WithinWindowMatcher.withinWindow(TIMEOUT, TOLERANCE)));

    }
}
