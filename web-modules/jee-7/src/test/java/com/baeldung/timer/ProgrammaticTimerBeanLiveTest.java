package com.baeldung.timer;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.io.File;

import static com.jayway.awaitility.Awaitility.await;
import static com.jayway.awaitility.Awaitility.to;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


@RunWith(Arquillian.class)
public class ProgrammaticTimerBeanLiveTest {

    final static long TIMEOUT = 5000l;
    final static long TOLERANCE = 1000l;

    @Inject
    TimerEventListener timerEventListener;

    @Deployment
    public static WebArchive deploy() {
        File[] jars = Maven.resolver().loadPomFromFile("pom.xml")
                .resolve("com.jayway.awaitility:awaitility")
                .withTransitivity().asFile();

        return ShrinkWrap.create(WebArchive.class)
                .addAsLibraries(jars)
                .addClasses(WithinWindowMatcher.class, TimerEvent.class, TimerEventListener.class, ProgrammaticTimerBean.class);
    }

    @Test
    public void should_receive_two_pings() {

        await().untilCall(to(timerEventListener.getEvents()).size(), equalTo(2));

        TimerEvent firstEvent = timerEventListener.getEvents().get(0);
        TimerEvent secondEvent = timerEventListener.getEvents().get(1);

        long delay = secondEvent.getTime() - firstEvent.getTime();
        System.out.println("Actual timeout = " + delay);
        assertThat(delay, is(WithinWindowMatcher.withinWindow(TIMEOUT, TOLERANCE)));
    }
}