package vaibhav.com.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Event {

    private long id;
    private Date date;

    public Event(long id, Date date) {
        this.id = id;
        this.date = date;
    }

    // standard setters and getters
}