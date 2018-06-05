package com.baeldung.reactive.generator;

import com.baeldung.reactive.model.Email;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.*;

@Component
public class EmailGenerator {
    private static Map<String, Email> emailMap = new HashMap<String, Email>() {
        {
            put("emailBob", new Email("Hello", "Hello Bob"));
            put("emailRob", new Email("Hello1", "Hello Rob"));
            put("emailJeff", new Email("Hello2", "Hello Jeff"));
            put("emailTom", new Email("Hello3", "Hello Tom"));
            put("emailBobby", new Email("Hello4", "Hello Bobby"));
        }
    };

    public Flux<Email> fetchQuoteStream() {
        return Flux.interval(Duration.ofSeconds(1))
            .onBackpressureDrop()
            .map(this::generateEmail)
            .flatMapIterable(emails -> emails);
    }

    /*
     * Create an email for all tickers
     */
    private List<Email> generateEmail(long interval) {
        Object[] emailKeys = emailMap.keySet()
            .toArray();
        List<Email> emailList = new ArrayList<>();

        // select random email message from emailMap
        emailList.add(emailMap.get(emailKeys[new Random().nextInt(emailKeys.length)]));
        return emailList;
    }
}
