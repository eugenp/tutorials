package com.baeldung.spring.kafka.multiplelisteners;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.AcknowledgingConsumerAwareMessageListener;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.test.context.EmbeddedKafka;

@SpringBootTest(classes = MultipleListenersApplicationKafkaApp.class)
@EmbeddedKafka(partitions = 1, controlledShutdown = true, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class KafkaMultipleListenersIntegrationTest {

    @Autowired
    private KafkaListenerEndpointRegistry registry;
    @Autowired
    private KafkaTemplate<String, BookEvent> bookEventKafkaTemplate;

    private static final String TOPIC = "books";

    @Test
    void givenEmbeddedKafkaBroker_whenSendingAMessage_thenMessageIsConsumedByAll3Listeners() throws Exception {
        BookEvent bookEvent = new BookEvent("test-book-title-1", "test-book-desc-1", 2.0);
        CountDownLatch latch = new CountDownLatch(3);

        List<? extends ConcurrentMessageListenerContainer<?, ?>> bookListeners = registry.getAllListenerContainers()
          .stream()
          .map(c -> (ConcurrentMessageListenerContainer<?, ?>) c)
          .collect(Collectors.toList());

        bookListeners.forEach(listener -> {
            listener.stop();
            listener.getContainerProperties()
              .setMessageListener((AcknowledgingConsumerAwareMessageListener<String, BookEvent>) (data, acknowledgment, consumer) -> {
                  assertThat(data.value()).isEqualTo(bookEvent);
                  latch.countDown();
              });
            listener.start();
        });

        bookEventKafkaTemplate.send(TOPIC, UUID.randomUUID()
          .toString(), bookEvent);

        assertThat(bookListeners.size()).isEqualTo(3);
        assertThat(latch.await(10, TimeUnit.SECONDS)).isTrue();
    }

    @Test
    void givenEmbeddedKafkaBroker_whenSendingThreeMessage_thenListenerPrintLogs() throws Exception {
        CountDownLatch latch = new CountDownLatch(3);
        Arrays.stream(new int[] { 1, 2, 3 })
          .mapToObj(i -> new BookEvent(String.format("book %s", i), String.format("description %s", i), (double) i))
          .forEach(bookEvent -> {
              bookEventKafkaTemplate.send(TOPIC, UUID.randomUUID()
                .toString(), bookEvent);
              latch.countDown();
          });

        // wait for messages to be printed
        Thread.sleep(1000);

        assertThat(latch.await(10, TimeUnit.SECONDS)).isTrue();
    }
}
