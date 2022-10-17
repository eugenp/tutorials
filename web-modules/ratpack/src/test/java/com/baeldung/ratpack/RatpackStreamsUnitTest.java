package com.baeldung.ratpack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ratpack.exec.ExecResult;
import ratpack.func.Action;
import ratpack.stream.StreamEvent;
import ratpack.stream.Streams;
import ratpack.stream.TransformablePublisher;
import ratpack.test.exec.ExecHarness;

public class RatpackStreamsUnitTest {
    
    private static Logger log = LoggerFactory.getLogger(RatpackStreamsUnitTest.class);

    @Test
    public void whenPublish_thenSuccess() {
        
        Publisher<String> pub = Streams.publish(Arrays.asList("hello", "hello again"));
        LoggingSubscriber<String> sub = new LoggingSubscriber<String>();
        pub.subscribe(sub);
        sub.block();
    }
    
    
    @Test
    public void whenYield_thenSuccess() {
        
        Publisher<String> pub = Streams.yield((t) -> {
            return t.getRequestNum() < 5 ? "hello" : null;
        });
        
        LoggingSubscriber<String> sub = new LoggingSubscriber<String>();
        pub.subscribe(sub);
        sub.block();
        assertEquals(5, sub.getReceived());
    }
    
    @Test
    public void whenPeriodic_thenSuccess() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Publisher<String> pub = Streams.periodically(executor, Duration.ofSeconds(1), (t) -> {
            return t < 5 ? String.format("hello %d",t): null; 
        });

        LoggingSubscriber<String> sub = new LoggingSubscriber<String>();
        pub.subscribe(sub);
        sub.block();
        assertEquals(5, sub.getReceived());
    }
    
    @Test
    public void whenMap_thenSuccess() throws Exception {
        
        TransformablePublisher<String> pub = Streams.yield( t -> {
            return t.getRequestNum() < 5 ? t.getRequestNum() : null;
          })
          .map(v -> String.format("item %d", v));
        
        ExecResult<List<String>> result = ExecHarness.yieldSingle((c) -> pub.toList() );
        assertTrue("should succeed", result.isSuccess());
        assertEquals("should have 5 items",5,result.getValue().size());
    }
    
    @Test
    public void whenNonCompliantPublisherWithBuffer_thenSuccess() throws Exception {
        
        TransformablePublisher<Integer> pub = Streams.transformable(new NonCompliantPublisher())
          .wiretap(new LoggingAction("before buffer"))
          .buffer()
          .wiretap(new LoggingAction("after buffer"))
          .take(1);
          
        LoggingSubscriber<Integer> sub = new LoggingSubscriber<>();
        pub.subscribe(sub);
        sub.block();
    }
    
    @Test
    public void whenNonCompliantPublisherWithoutBuffer_thenSuccess() throws Exception {
        TransformablePublisher<Integer> pub = Streams.transformable(new NonCompliantPublisher())
          .wiretap(new LoggingAction(""))
          .take(1);
          
        LoggingSubscriber<Integer> sub = new LoggingSubscriber<>();
        pub.subscribe(sub);
        sub.block();
    }

@Test
public void whenCompliantPublisherWithoutBatch_thenSuccess() throws Exception {
    
    TransformablePublisher<Integer> pub = Streams.transformable(new CompliantPublisher(10))
      .wiretap(new LoggingAction(""));
      
    LoggingSubscriber<Integer> sub = new LoggingSubscriber<>();
    pub.subscribe(sub);
    sub.block();
}

@Test
public void whenCompliantPublisherWithBatch_thenSuccess() throws Exception {
    
    TransformablePublisher<Integer> pub = Streams.transformable(new CompliantPublisher(10))
      .wiretap(new LoggingAction("before batch"))
      .batch(5, Action.noop())
      .wiretap(new LoggingAction("after batch"));
      
    LoggingSubscriber<Integer> sub = new LoggingSubscriber<>();
    pub.subscribe(sub);
    sub.block();
}
    
    private static class LoggingAction implements Action<StreamEvent<Integer>>{
        private final String label;

        public LoggingAction(String label) {
            this.label = label;
        }

        @Override
        public void execute(StreamEvent<Integer> e) throws Exception {
            log.info("{}: event={}", label,e);
        }
        
    }
    
}
