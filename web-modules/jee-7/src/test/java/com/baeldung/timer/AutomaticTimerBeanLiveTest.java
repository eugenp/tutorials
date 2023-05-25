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
public class AutomaticTimerBeanLiveTest {

    //the @AutomaticTimerBean has a method called every 10 seconds
    //testing the difference ==> 100000
    final static long TIMEOUT = 10000l;

    //the tolerance accepted , so if between two consecutive calls there has to be at least 9 or max 11 seconds.
    //because the timer service is not intended for real-time applications so it will not be exactly 10 seconds
    final static long TOLERANCE = 1000l;

    @Inject
    TimerEventListener timerEventListener;

    @Deployment
    public static WebArchive deploy() {
        File[] jars = Maven.resolver().loadPomFromFile("pom.xml")
            .resolve("com.jayway.awaitility:awaitility")
            .withTransitivity().asFile();

        //only @AutomaticTimerBean is deployed not the other timers
        return ShrinkWrap.create(WebArchive.class)
            .addAsLibraries(jars)
            .addClasses(WithinWindowMatcher.class, TimerEvent.class, TimerEventListener.class, AutomaticTimerBean.class);
    }



    @Test
    public void should_receive_two_pings() {
        Awaitility.setDefaultTimeout(30, TimeUnit.SECONDS);
        //the test will wait here until two events are triggered
        await().untilCall(to(timerEventListener.getEvents()).size(), equalTo(2));

        TimerEvent firstEvent = timerEventListener.getEvents().get(0);
        TimerEvent secondEvent = timerEventListener.getEvents().get(1);

        long delay = secondEvent.getTime() - firstEvent.getTime();
        System.out.println("Actual timeout = " + delay);

        //ensure that the delay between the events is more or less 10 seconds (no real time precision)
        assertThat(delay, Matchers.is(WithinWindowMatcher.withinWindow(TIMEOUT, TOLERANCE)));
    }
}
