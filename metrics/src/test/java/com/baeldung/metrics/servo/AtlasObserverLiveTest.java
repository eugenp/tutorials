package com.baeldung.metrics.servo;

import com.netflix.servo.DefaultMonitorRegistry;
import com.netflix.servo.monitor.BasicCounter;
import com.netflix.servo.monitor.Counter;
import com.netflix.servo.monitor.MonitorConfig;
import com.netflix.servo.publish.BasicMetricFilter;
import com.netflix.servo.publish.MonitorRegistryMetricPoller;
import com.netflix.servo.publish.PollRunnable;
import com.netflix.servo.publish.PollScheduler;
import com.netflix.servo.publish.atlas.AtlasMetricObserver;
import com.netflix.servo.publish.atlas.BasicAtlasConfig;
import com.netflix.servo.tag.BasicTagList;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.http.HttpEntity;
import org.apache.http.impl.client.HttpClients;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.apache.http.client.methods.RequestBuilder.get;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

/**
 * Atlas server needs to be up and running for this live test to work.
 */
public class AtlasObserverLiveTest {

    private final String atlasUri = "http://localhost:7101/api/v1";

    @Before
    public void prepareScheduler() {
        System.setProperty("servo.pollers", "1000");
        System.setProperty("servo.atlas.batchSize", "1");
        System.setProperty("servo.atlas.uri", atlasUri + "/publish");
        AtlasMetricObserver observer = new AtlasMetricObserver(new BasicAtlasConfig(), BasicTagList.of("servo", "counter"));

        PollRunnable task = new PollRunnable(new MonitorRegistryMetricPoller(), new BasicMetricFilter(true), observer);
        PollScheduler
          .getInstance()
          .start();
        PollScheduler
          .getInstance()
          .addPoller(task, 1, SECONDS);
    }

    @After
    public void stopScheduler() {
        if (PollScheduler
          .getInstance()
          .isStarted()) {
            PollScheduler
              .getInstance()
              .stop();
        }
    }

    private String atlasValuesOfTag(String tagname) throws Exception {
        HttpEntity entity = HttpClients
          .createDefault()
          .execute(get()
            .setUri(atlasUri + "/tags/" + tagname)
            .build())
          .getEntity();
        return new BufferedReader(new InputStreamReader(entity.getContent())).readLine();
    }

    @Test
    public void givenAtlasAndCounter_whenRegister_thenPublishedToAtlas() throws Exception {
        Counter counter = new BasicCounter(MonitorConfig
          .builder("test")
          .withTag("servo", "counter")
          .build());
        DefaultMonitorRegistry
          .getInstance()
          .register(counter);
        assertThat(atlasValuesOfTag("servo"), not(containsString("counter")));

        for (int i = 0; i < 3; i++) {
            counter.increment(RandomUtils.nextInt(10));
            SECONDS.sleep(1);
            counter.increment(-1 * RandomUtils.nextInt(10));
            SECONDS.sleep(1);
        }

        assertThat(atlasValuesOfTag("servo"), containsString("counter"));
    }

}
