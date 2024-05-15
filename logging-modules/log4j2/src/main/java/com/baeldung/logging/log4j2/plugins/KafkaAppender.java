package com.baeldung.logging.log4j2.plugins;

import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

import java.io.Serializable;

@Plugin(name = "Kafka2", category = Core.CATEGORY_NAME)
public class KafkaAppender extends AbstractAppender {

    @PluginBuilderFactory
    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder implements org.apache.logging.log4j.core.util.Builder<KafkaAppender> {

        @PluginBuilderAttribute("name")
        @Required
        private String name;

        @PluginBuilderAttribute("ip")
        private String ipAddress;

        @PluginBuilderAttribute("port")
        private int port;

        @PluginBuilderAttribute("topic")
        private String topic;

        @PluginBuilderAttribute("partition")
        private String partition;

        @PluginElement("Layout")
        private Layout<? extends Serializable> layout;

        @PluginElement("Filter")
        private Filter filter;

        public Layout<? extends Serializable> getLayout() {
            return layout;
        }

        public Builder setLayout(Layout<? extends Serializable> layout) {
            this.layout = layout;
            return this;
        }

        public Filter getFilter() {
            return filter;
        }

        public String getName() {
            return name;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setFilter(Filter filter) {
            this.filter = filter;
            return this;
        }

        public String getIpAddress() {
            return ipAddress;
        }

        public Builder setIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        public int getPort() {
            return port;
        }

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        public String getTopic() {
            return topic;
        }

        public Builder setTopic(String topic) {
            this.topic = topic;
            return this;
        }

        public String getPartition() {
            return partition;
        }

        public Builder setPartition(String partition) {
            this.partition = partition;
            return this;
        }

        @Override
        public KafkaAppender build() {
            return new KafkaAppender(getName(), getFilter(), getLayout(), true, new KafkaBroker(ipAddress, port, topic, partition));
        }
    }

    private KafkaBroker broker;

    private KafkaAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, KafkaBroker broker) {
        super(name, filter, layout, ignoreExceptions);
        this.broker = broker;
    }

    @Override
    public void append(LogEvent event) {

        connectAndSendToKafka(broker, event);
    }

    private void connectAndSendToKafka(KafkaBroker broker, LogEvent event) {
        //send to Kafka
    }
}
