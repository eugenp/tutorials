package com.baeldung.spring.cloud.aws.sqs.conversion.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "events.queues.shipping")
public class ShipmentEventsQueuesProperties {

    private String simplePojoConversionQueue;

    private String customObjectMapperQueue;

    private String subclassDeserializationQueue;

    public String getSimplePojoConversionQueue() {
        return simplePojoConversionQueue;
    }

    public void setSimplePojoConversionQueue(String simplePojoConversionQueue) {
        this.simplePojoConversionQueue = simplePojoConversionQueue;
    }

    public String getCustomObjectMapperQueue() {
        return customObjectMapperQueue;
    }

    public void setCustomObjectMapperQueue(String customObjectMapperQueue) {
        this.customObjectMapperQueue = customObjectMapperQueue;
    }

    public String getSubclassDeserializationQueue() {
        return subclassDeserializationQueue;
    }

    public void setSubclassDeserializationQueue(String subclassDeserializationQueue) {
        this.subclassDeserializationQueue = subclassDeserializationQueue;
    }
}