package com.baeldung.mqtt;



import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EngineTemperatureSensorLiveTest {
    
    private static Logger log = LoggerFactory.getLogger(EngineTemperatureSensorLiveTest.class);

    @Test
    public void whenSendSingleMessage_thenSuccess() throws Exception {

        String publisherId = UUID.randomUUID().toString();
        MqttClient publisher = new MqttClient("tcp://iot.eclipse.org:1883",publisherId);
        
        String subscriberId = UUID.randomUUID().toString();
        MqttClient subscriber = new MqttClient("tcp://iot.eclipse.org:1883",subscriberId);
        
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        
        
        subscriber.connect(options);
        publisher.connect(options);
        
        CountDownLatch receivedSignal = new CountDownLatch(1);
        
        subscriber.subscribe(EngineTemperatureSensor.TOPIC, (topic, msg) -> {
            byte[] payload = msg.getPayload();
            log.info("[I46] Message received: topic={}, payload={}", topic, new String(payload));
            receivedSignal.countDown();
        });
        
        
        Callable<Void> target = new EngineTemperatureSensor(publisher);
        target.call();

        receivedSignal.await(1, TimeUnit.MINUTES);

        log.info("[I56] Success !");
    }
    
    @Test
    public void whenSendMultipleMessages_thenSuccess() throws Exception {

        String publisherId = UUID.randomUUID().toString();
        MqttClient publisher = new MqttClient("tcp://iot.eclipse.org:1883",publisherId);
        
        String subscriberId = UUID.randomUUID().toString();
        MqttClient subscriber = new MqttClient("tcp://iot.eclipse.org:1883",subscriberId);
        
        
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        

        publisher.connect(options);        
        subscriber.connect(options);
        
        CountDownLatch receivedSignal = new CountDownLatch(10);
        
        subscriber.subscribe(EngineTemperatureSensor.TOPIC, (topic, msg) -> {
            byte[] payload = msg.getPayload();
            log.info("[I82] Message received: topic={}, payload={}", topic, new String(payload));
            receivedSignal.countDown();
        });
        
        
        Callable<Void> target = new EngineTemperatureSensor(publisher);
        
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
              try {
                  target.call();
              }
              catch(Exception ex) {
                  throw new RuntimeException(ex);
              }
          }, 1, 1, TimeUnit.SECONDS);
        

        receivedSignal.await(1, TimeUnit.MINUTES);
        executor.shutdown();
        
        assertTrue(receivedSignal.getCount() == 0 , "Countdown should be zero");

        log.info("[I105] Success !");
    }
    

}
