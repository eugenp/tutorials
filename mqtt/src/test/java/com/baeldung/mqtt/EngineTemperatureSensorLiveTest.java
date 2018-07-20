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

        String senderId = UUID.randomUUID().toString();
        MqttClient sender = new MqttClient("tcp://iot.eclipse.org:1883",senderId);
        
        String receiverId = UUID.randomUUID().toString();
        MqttClient receiver = new MqttClient("tcp://iot.eclipse.org:1883",receiverId);
        
        
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        
        
        receiver.connect(options);
        sender.connect(options);
        
        CountDownLatch receivedSignal = new CountDownLatch(1);
        
        receiver.subscribe(EngineTemperatureSensor.TOPIC, (topic, msg) -> {
            log.info("[I41] Message received: topic={}, payload={}", topic, new String(msg.getPayload()));
            receivedSignal.countDown();
        });
        
        
        Callable<Void> target = new EngineTemperatureSensor(sender);
        target.call();

        receivedSignal.await(1, TimeUnit.MINUTES);

        log.info("[I51] Success !");
    }
    
    @Test
    public void whenSendMultipleMessages_thenSuccess() throws Exception {

        String senderId = UUID.randomUUID().toString();
        MqttClient sender = new MqttClient("tcp://iot.eclipse.org:1883",senderId);
        
        String receiverId = UUID.randomUUID().toString();
        MqttClient receiver = new MqttClient("tcp://iot.eclipse.org:1883",receiverId);
        
        
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        

        sender.connect(options);        
        receiver.connect(options);
        
        CountDownLatch receivedSignal = new CountDownLatch(10);
        
        receiver.subscribe(EngineTemperatureSensor.TOPIC, (topic, msg) -> {
            log.info("[I41] Message received: topic={}, payload={}", topic, new String(msg.getPayload()));
            receivedSignal.countDown();
        });
        
        
        Callable<Void> target = new EngineTemperatureSensor(sender);
        
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
              try {
                  target.call();
              }
              catch(Exception ex) {
                  throw new RuntimeException(ex);
              }
          }, 1, 1, TimeUnit.SECONDS);
        

        receivedSignal.await(1, TimeUnit.DAYS);
        executor.shutdown();
        
        assertTrue(receivedSignal.getCount() == 0 , "Countdown should be zero");

        log.info("[I51] Success !");
    }
    

}
