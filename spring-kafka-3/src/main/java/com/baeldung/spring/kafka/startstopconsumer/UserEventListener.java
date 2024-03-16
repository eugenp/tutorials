package com.baeldung.spring.kafka.startstopconsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventListener {

    private static final Logger logger = LoggerFactory.getLogger(UserEventListener.class);

    @Autowired
    UserEventStore userEventStore;

    @KafkaListener(id = Constants.LISTENER_ID, topics = Constants.MULTI_PARTITION_TOPIC, groupId = "test-group",
      containerFactory = "kafkaListenerContainerFactory", autoStartup = "false")
    public void processUserEvent(UserEvent userEvent) {
        logger.info("Received UserEvent: " + userEvent.getUserEventId());
        userEventStore.addUserEvent(userEvent);
    }
}
