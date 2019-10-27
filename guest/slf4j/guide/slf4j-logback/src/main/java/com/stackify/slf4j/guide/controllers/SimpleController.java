package com.stackify.slf4j.guide.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;
import org.slf4j.ext.EventData;
import org.slf4j.ext.EventLogger;
import org.slf4j.profiler.Profiler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stackify.slf4j.guide.l10n.Messages;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;

@RestController
public class SimpleController {

    Logger logger = LoggerFactory.getLogger(SimpleController.class);

    @GetMapping("/slf4j-guide-request")
    public String processList(List<String> list) {
        logger.info("Client requested process the following list: {}", list);
        try {
            logger.debug("Starting process");
            // ...processing list here...
            Thread.sleep(500);
        } catch (RuntimeException | InterruptedException e) {
            logger.error("There was an issue processing the list.", e);
        } finally {
            logger.info("Finished processing");
        }
        return "done";
    }

    @GetMapping("/slf4j-guide-mdc-request")
    public String clientMDCRequest(@RequestHeader String clientId) throws InterruptedException {
        MDC.put("clientId", clientId);
        logger.info("Client {} has made a request", clientId);
        logger.info("Starting request");
        Thread.sleep(500);
        logger.info("Finished request");
        MDC.clear();
        return "finished";
    }

    @GetMapping("/slf4j-guide-marker-request")
    public String clientMarkerRequest() throws InterruptedException {
        logger.info("client has made a request");
        Marker myMarker = MarkerFactory.getMarker("MYMARKER");
        logger.info(myMarker, "Starting request");
        Thread.sleep(500);
        logger.debug(myMarker, "Finished request");
        return "finished";
    }

    @GetMapping("/slf4j-guide-profiler-request")
    public String clientProfilerRequest() {
        logger.info("client has made a request");
        Profiler myProfiler = new Profiler("MYPROFILER");
        // Associate the logger to handle the output( for testing purposes here)
        myProfiler.setLogger(logger);

        myProfiler.start("List generation process");
        List<Integer> list = generateList();

        myProfiler.start("List sorting process");
        Collections.sort(list);

        // Use the log() method instead of print() to use the logger (for testing purposes here)
        myProfiler.stop()
            .log();
        return "finished";
    }

    private List<Integer> generateList() {
        List<Integer> generated = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            generated.add(ThreadLocalRandom.current()
                .nextInt(2000));
        }
        return generated;
    }

    @GetMapping("/slf4j-guide-event-request")
    public String clientEventRequest(@RequestParam("sender") String sender, @RequestParam("receiver") String receiver) {
        logger.info("sending from {} to {}", sender, receiver);

        // ...sending process...

        EventData data = new EventData();
        data.setEventDateTime(new Date());
        data.setEventType("sending");
        String confirm = UUID.randomUUID()
            .toString();
        data.setEventId(confirm);
        data.put("from", sender);
        data.put("to", receiver);
        EventLogger.logEvent(data);

        return "finished";
    }

    @GetMapping("/slf4j-guide-locale-request")
    public String clientLocaleRequest(@RequestHeader("Accept-Language") String localeHeader) {
        List<Locale.LanguageRange> list = Locale.LanguageRange.parse(localeHeader);
        Locale locale = Locale.lookup(list, Arrays.asList(Locale.getAvailableLocales()));
        IMessageConveyor messageConveyor = new MessageConveyor(locale);
        LocLoggerFactory llFactory = new LocLoggerFactory(messageConveyor);
        LocLogger locLogger = llFactory.getLocLogger(this.getClass());
        locLogger.info(Messages.CLIENT_REQUEST, "parametrizedClientId", localeHeader);
        locLogger.debug(Messages.REQUEST_STARTED);
        locLogger.info(Messages.REQUEST_FINISHED);
        return "finished";
    }

}
