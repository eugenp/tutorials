package com.baeldung.scheduleonlyonce.trigger;

import java.time.Duration;
import java.time.Instant;

import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.PeriodicTrigger;

public class OneOffTrigger extends PeriodicTrigger {

    public OneOffTrigger(Instant when) {
        super(Duration.ofSeconds(0));

        Duration difference = Duration.between(Instant.now(), when);
        setInitialDelay(difference);
    }

    @Override
    public Instant nextExecution(TriggerContext context) {
        if (context.lastCompletion() == null) {
            return super.nextExecution(context);
        }

        return null;
    }
}
