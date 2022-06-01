package com.baeldung.twitterhdfs.source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.app.twitterstream.source.TwitterstreamSourceConfiguration;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Import;
import org.springframework.integration.annotation.InboundChannelAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@EnableBinding(Source.class)
@Import(TwitterstreamSourceConfiguration.class)
public class SourceApp {
    Logger log = LoggerFactory.getLogger(SourceApp.class);

    @InboundChannelAdapter(value = Source.OUTPUT)
    public String timerMessageSource() {
        return new SimpleDateFormat().format(new Date());
    }

}
