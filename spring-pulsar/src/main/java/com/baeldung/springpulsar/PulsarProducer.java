package com.baeldung.springpulsar;

import org.apache.pulsar.client.api.ProducerAccessMode;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class PulsarProducer {

    @Autowired
    private PulsarTemplate<User> template;
    @Autowired
    private PulsarTemplate<String> stringTemplate;

    private static final String USER_TOPIC = "user-topic";
    private static final String USER_TOPIC_STR = "string-topic";

    public void sendMessageToPulsarTopic(User user) throws PulsarClientException {
        template.newMessage(user)
          .withProducerCustomizer(pc -> {
              pc.accessMode(ProducerAccessMode.Shared);
          })
          .withMessageCustomizer(mc -> {
              mc.deliverAfter(10L, TimeUnit.SECONDS);
          })
          .withTopic(USER_TOPIC)
          .send();
    }

    public void sendStringMessageToPulsarTopic(String str) throws PulsarClientException {
        stringTemplate.send(USER_TOPIC_STR, str);
    }
}
