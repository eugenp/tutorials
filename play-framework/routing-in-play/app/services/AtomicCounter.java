package services;

import java.util.concurrent.atomic.AtomicInteger;
import javax.inject.*;

/**
 * This class is a concrete implementation of the {@link Counter} trait.
 * It is configured for Guice dependency injection in the {@link Module}
 * class.
 *
 * This class has a {@link Singleton} annotation because we need to make
 * sure we only use one counter per application. Without this
 * annotation we would get a new instance every time a {@link Counter} is
 * injected.
 */
@Singleton
public class AtomicCounter implements Counter {

    private final AtomicInteger atomicCounter = new AtomicInteger();

    @Override
    public int nextCount() {
       return atomicCounter.getAndIncrement();
    }

}
