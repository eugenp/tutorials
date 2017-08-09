package com.baeldung.metrics.servo;

import com.netflix.servo.Metric;
import com.netflix.servo.annotations.DataSourceType;
import com.netflix.servo.annotations.Monitor;
import com.netflix.servo.annotations.MonitorTags;
import com.netflix.servo.monitor.Monitors;
import com.netflix.servo.tag.BasicTag;
import com.netflix.servo.tag.BasicTagList;
import com.netflix.servo.tag.TagList;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class MetricAnnotationManualTest extends MetricTestBase {

    @Monitor(name = "integerCounter", type = DataSourceType.COUNTER, description = "Total number of update operations.")
    private final AtomicInteger updateCount = new AtomicInteger(0);

    @MonitorTags private TagList tags = new BasicTagList(newArrayList(new BasicTag("tag-key", "tag-value")));

    @Test
    public void givenAnnotatedMonitor_whenUpdated_thenDataCollected() throws Exception {
        Monitors.registerObject("testObject", this);
        assertTrue(Monitors.isObjectRegistered("testObject", this));

        updateCount.incrementAndGet();
        updateCount.incrementAndGet();
        SECONDS.sleep(1);

        List<List<Metric>> metrics = observer.getObservations();
        System.out.println(metrics);
        assertThat(metrics, hasSize(greaterThanOrEqualTo(1)));

        Iterator<List<Metric>> metricIterator = metrics.iterator();
        //skip first empty observation
        metricIterator.next();
        while (metricIterator.hasNext()) {
            assertThat(metricIterator.next(), hasItem(hasProperty("config", hasProperty("name", is("integerCounter")))));
        }

    }
}
