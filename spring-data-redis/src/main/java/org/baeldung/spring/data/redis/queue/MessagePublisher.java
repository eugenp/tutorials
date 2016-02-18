package org.baeldung.spring.data.redis.queue;


public interface MessagePublisher {

    void publish(String message);
}
