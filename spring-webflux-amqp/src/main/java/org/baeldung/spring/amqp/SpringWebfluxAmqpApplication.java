package org.baeldung.spring.amqp;

import java.util.stream.Stream;

import org.baeldung.spring.amqp.DestinationsConfig.DestinationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@SpringBootApplication
@EnableConfigurationProperties(DestinationsConfig.class)
@RestController
public class SpringWebfluxAmqpApplication {

    private static Logger log = LoggerFactory.getLogger(SpringWebfluxAmqpApplication.class);

    @Autowired
    private AmqpTemplate amqpTemplate;
    
    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private DestinationsConfig destinationsConfig;


    public static void main(String[] args) {
        SpringApplication.run(SpringWebfluxAmqpApplication.class, args);
    }

    @Bean
    public CommandLineRunner setupQueueDestinations(AmqpAdmin amqpAdmin,DestinationsConfig destinationsConfig) {

        return (args) -> {

            log.info("[I48] Creating Destinations...");

            destinationsConfig.getQueues()
                .forEach((key, destination) -> {

                    log.info("[I54] Creating directExchange: key={}, name={}, routingKey={}", key, destination.getExchange(), destination.getRoutingKey());

                    Exchange ex = ExchangeBuilder
                                    .directExchange(destination.getExchange())
                                    .durable(true)
                                    .build();
                    
                    amqpAdmin.declareExchange(ex);

                    Queue q = QueueBuilder
                                .durable(destination.getRoutingKey())
                                .build();
                    
                    amqpAdmin.declareQueue(q);

                    Binding b = BindingBuilder.bind(q)
                        .to(ex)
                        .with(destination.getRoutingKey())
                        .noargs();
                    amqpAdmin.declareBinding(b);

                    log.info("[I70] Binding successfully created.");

                });

        };

    }

    @Bean
    public CommandLineRunner setupTopicDestinations(AmqpAdmin amqpAdmin, DestinationsConfig destinationsConfig) {

        return (args) -> {

            // For topic each consumer will have its own Queue, so no binding
            destinationsConfig.getTopics()
                .forEach((key, destination) -> {

                    log.info("[I98] Creating TopicExchange: name={}, exchange={}", key, destination.getExchange());

                    Exchange ex = ExchangeBuilder.topicExchange(destination.getExchange())
                        .durable(true)
                        .build();

                    amqpAdmin.declareExchange(ex);

                    log.info("[I107] Topic Exchange successfully created.");

                });
        };
    }
    
    @PostMapping(value = "/queue/{name}")
    public Mono<ResponseEntity<?>> sendMessageToQueue(@PathVariable String name, @RequestBody String payload) {

        // Lookup exchange details
        final DestinationInfo d = destinationsConfig.getQueues()
            .get(name);
        if (d == null) {
            // Destination not found.
            return Mono.just(ResponseEntity.notFound().build());
        }
        
        return Mono.fromCallable(() -> {

            log.info("[I51] sendMessageToQueue: queue={}, routingKey={}", d.getExchange(), d.getRoutingKey());
            amqpTemplate.convertAndSend(d.getExchange(), d.getRoutingKey(), payload);
            
            return  ResponseEntity.accepted().build();

        });

    }
    
    
    /**
     * Receive messages for the given queue
     * @param name
     * @return
     */
    @GetMapping(value = "/queue/{name}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<?> receiveMessagesFromQueue(@PathVariable String name) {

        final DestinationInfo d = destinationsConfig.getQueues().get(name);

        if (d == null) {
            return Flux.just(ResponseEntity.notFound().build());
        }
        
        Stream<String> s = Stream.generate(() -> {
            String queueName = d.getRoutingKey();
            
            log.info("[I137] Polling {}", queueName);
            
            Object payload = amqpTemplate.receiveAndConvert(queueName,5000);
            if ( payload == null ) {
                payload = "No news is good news...";
            } 
            
            return payload.toString();
        });
            
        
        return Flux
                .fromStream(s)
                .subscribeOn(Schedulers.elastic());
        
    }
    
    /**
     * send message to a given topic
     * @param name
     * @param payload
     * @return
     */
    @PostMapping(value = "/topic/{name}")
    public Mono<ResponseEntity<?>> sendMessageToTopic(@PathVariable String name, @RequestBody String payload) {

        // Lookup exchange details
        final DestinationInfo d = destinationsConfig.getTopics().get(name);
        if (d == null) {
            // Destination not found.
            return Mono.just(ResponseEntity.notFound().build());
        }
        
        return Mono.fromCallable(() -> {

            log.info("[I51] sendMessageToTopic: topic={}, routingKey={}", d.getExchange(), d.getRoutingKey());
            amqpTemplate.convertAndSend(d.getExchange(), d.getRoutingKey(), payload);
            
            return  ResponseEntity.accepted().build();

        });
    }
    
    
    @GetMapping(value = "/topic/{name}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<?> receiveMessagesFromTopic(@PathVariable String name) {

        DestinationInfo d = destinationsConfig.getTopics().get(name);

        if (d == null) {
            return Flux.just(ResponseEntity.notFound().build());
        }
        
        final Queue topicQueue = createTopicQueue(d);
                
        Stream<String> s = Stream.generate(() -> {
            String queueName = topicQueue.getName();
            
            log.info("[I137] Polling {}", queueName);
            
            try {
                Object payload = amqpTemplate.receiveAndConvert(queueName,5000);
                if ( payload == null ) {
                    payload = "No news is good news...";
                } 
                
                return payload.toString();
            }
            catch(AmqpException ex) {
                log.warn("[W247] Received an AMQP Exception: {}", ex.getMessage());
                return null;
            }
        });
        

        return Flux.fromStream(s)
            .doOnCancel(() -> {
              log.info("[I250] doOnCancel()");
              amqpAdmin.deleteQueue(topicQueue.getName());
            })
            .subscribeOn(Schedulers.elastic());
        
        
    }
    
    
    private Queue createTopicQueue(DestinationInfo destination) {

        Exchange ex = ExchangeBuilder.topicExchange(destination.getExchange())
            .durable(true)
            .build();

        amqpAdmin.declareExchange(ex);

        // Create a durable queue
        Queue q = QueueBuilder
            .durable()
            .build();
        
        amqpAdmin.declareQueue(q);

        Binding b = BindingBuilder.bind(q)
            .to(ex)
            .with(destination.getRoutingKey())
            .noargs();
        
        amqpAdmin.declareBinding(b);

        return q;
    }    
    
    
}
