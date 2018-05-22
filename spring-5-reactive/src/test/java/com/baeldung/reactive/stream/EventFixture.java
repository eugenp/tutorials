/**
 *
 */
package com.baeldung.reactive.stream;

/**
 * @author goobar
 *
 */
public class EventFixture {
    /**
     * @param msg
     * @return
     */
    static Event event(String msg) {
        return new Event(msg);
    }
}
