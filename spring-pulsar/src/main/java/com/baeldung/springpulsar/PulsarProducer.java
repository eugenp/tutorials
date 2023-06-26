package com.baeldung.springpulsar;

import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.stereotype.Component;

@Component
public class PulsarProducer {

    @Autowired
    private PulsarTemplate<User> template;
    @Autowired
    private PulsarTemplate<String> stringTemplate;

    private static final String USER_TOPIC = "user-topic";
    private static final String USER_TOPIC_STR = "user-topic-str";

    public void sendMessageToPulsarTopic(User user) throws PulsarClientException {
        template.send(USER_TOPIC, user);
    }

    public void sendStringMessageToPulsarTopic(String str) throws PulsarClientException {
        stringTemplate.send(USER_TOPIC_STR, str);
    }
}
