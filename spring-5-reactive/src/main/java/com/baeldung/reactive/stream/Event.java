/**
 *
 */
package com.baeldung.reactive.stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A simple event with a message.
 * @author goobar
 *
 */
public class Event {
    /**
     * Creates a new event with message reflecting given sequence.
     * @param sequence a sequential number
     * @return the event with message containing given sequence number
     */
    public static Event of(long sequence) {
        Event event = new Event("event" + sequence);
        return event;
    }

    private final String msg;

    /**
     * @param msg a message
     */
    @JsonCreator
    public Event(@JsonProperty("msg") String msg) {
        super();
        this.msg = msg;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Event other = (Event) obj;
        if (msg == null) {
            if (other.msg != null) {
                return false;
            }
        } else if (!msg.equals(other.msg)) {
            return false;
        }
        return true;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((msg == null) ? 0 : msg.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Event [msg=" + msg + "]";
    }
}
