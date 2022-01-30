package com.baeldung.simplehexagonal.infrastructure.repositories;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.baeldung.simplehexagonal.domain.Session;
import com.baeldung.simplehexagonal.domain.Speaker;

@Entity(name = "sessions")
public class SessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long session_id;

    private String session_name;
    private String session_description;
    private Integer session_length;

    @ManyToMany
    @JoinTable(name = "session_speakers", joinColumns = @JoinColumn(name = "session_id"), inverseJoinColumns = @JoinColumn(name = "speaker_id"))
    private List<SpeakerEntity> speakerEntities;

    public SessionEntity() {
    }

    public SessionEntity(Session session) {
        this.setSession_id(session.getSessionId());
        this.setSession_name(session.getSessionName());
        this.setSession_description(session.getSessionDescription());
        this.setSession_length(session.getSessionLength());
        List<SpeakerEntity> speakerEntities = session.getSpeakers()
            .stream()
            .map(it -> new SpeakerEntity(it))
            .toList();
        this.speakerEntities = speakerEntities;
    }

    public Session toSession() {
        Session session = new Session();
        session.setSessionId(session_id);
        session.setSessionName(session_name);
        session.setSessionDescription(session_description);
        session.setSessionLength(session_length);
        List<Speaker> speakers = speakerEntities.stream()
            .map(it -> it.toSpeaker())
            .toList();
        session.setSpeakers(speakers);
        return session;
    }

    public Long getSession_id() {
        return session_id;
    }

    public void setSession_id(Long session_id) {
        this.session_id = session_id;
    }

    public String getSession_name() {
        return session_name;
    }

    public void setSession_name(String session_name) {
        this.session_name = session_name;
    }

    public String getSession_description() {
        return session_description;
    }

    public void setSession_description(String session_description) {
        this.session_description = session_description;
    }

    public Integer getSession_length() {
        return session_length;
    }

    public void setSession_length(Integer session_length) {
        this.session_length = session_length;
    }

    public List<SpeakerEntity> getSpeakerEntities() {
        return speakerEntities;
    }

    public void setSpeakerEntities(List<SpeakerEntity> speakerEntities) {
        this.speakerEntities = speakerEntities;
    }

}
