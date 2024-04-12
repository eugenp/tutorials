package com.baeldung.kafka.streams;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class WordCountRestService {

    private final StreamsBuilderFactoryBean factoryBean;

    private final KafkaProducer kafkaProducer;

    @GetMapping("/count/{word}")
    public Long getWordCount(@PathVariable String word) {
        KafkaStreams kafkaStreams =  factoryBean.getKafkaStreams();
        ReadOnlyKeyValueStore<String, Long> counts = kafkaStreams
            .store(StoreQueryParameters.fromNameAndType("counts", QueryableStoreTypes.keyValueStore()));
        return counts.get(word);
    }

    @PostMapping("/message")
    public void addMessage(@RequestBody String message) {
        kafkaProducer.sendMessage(message);
    }
}
