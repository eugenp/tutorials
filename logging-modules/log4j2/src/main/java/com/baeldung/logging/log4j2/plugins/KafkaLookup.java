package com.baeldung.logging.log4j2.plugins;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.StrLookup;

@Plugin(name = "kafka", category = StrLookup.CATEGORY)
public class KafkaLookup implements StrLookup {

    @Override
    public String lookup(String key) {
        return getFromKafka(key);
    }

    @Override
    public String lookup(LogEvent event, String key) {
        return getFromKafka(key);
    }

    private String getFromKafka(String topicName) {
        //kafka search logic should go here
        return "topic1-p1";
    }
}
