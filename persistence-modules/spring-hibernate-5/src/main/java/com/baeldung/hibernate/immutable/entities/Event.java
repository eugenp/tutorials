package com.baeldung.hibernate.immutable.entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.Set;

@Entity
@Immutable
@Table(name = "events")
public class Event {

    @Id
    @Column(name = "event_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @ElementCollection
    @Immutable
    private Set<String> guestList;

    public Event() {}

    public Event(Long id, String title, Set<String> guestList) {
        this.id = id;
        this.title = title;
        this.guestList = guestList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Cascade({ CascadeType.SAVE_UPDATE, CascadeType.DELETE })
    public Set<String> getGuestList() {
        return guestList;
    }

    public void setGuestList(Set<String> guestList) {
        this.guestList = guestList;
    }
}
