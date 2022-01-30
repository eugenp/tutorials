package com.baeldung.simplehexagonal.infrastructure.repositories;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Type;

import com.baeldung.simplehexagonal.domain.Speaker;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "speakers")
public class SpeakerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long speaker_id;

    private String first_name;
    private String last_name;
    private String title;
    private String company;
    private String speaker_bio;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] speaker_photo;

    @JsonIgnore
    @ManyToMany(mappedBy = "speakerEntities", fetch = FetchType.LAZY)
    private List<SessionEntity> sessionEntities;

    public SpeakerEntity() {

    }

    public SpeakerEntity(Speaker speaker) {
        this.setSpeaker_id(speaker.getSpeakerId());
        this.setFirst_name(speaker.getFirstName());
        this.setLast_name(speaker.getLastName());
        this.setTitle(speaker.getTitle());
        this.setCompany(speaker.getCompany());
        this.setSpeaker_bio(speaker.getSpeakerBio());
        this.setSpeaker_photo(speaker.getSpeakerPhoto());
    }

    public Speaker toSpeaker() {
        Speaker speaker = new Speaker();
        speaker.setSpeakerId(speaker_id);
        speaker.setFirstName(first_name);
        speaker.setLastName(last_name);
        speaker.setTitle(title);
        speaker.setCompany(company);
        speaker.setSpeakerBio(speaker_bio);
        speaker.setSpeakerPhoto(speaker_photo);
        return speaker;
    }

    public Long getSpeaker_id() {
        return speaker_id;
    }

    public void setSpeaker_id(Long speaker_id) {
        this.speaker_id = speaker_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSpeaker_bio() {
        return speaker_bio;
    }

    public void setSpeaker_bio(String speaker_bio) {
        this.speaker_bio = speaker_bio;
    }

    public byte[] getSpeaker_photo() {
        return speaker_photo;
    }

    public void setSpeaker_photo(byte[] speaker_photo) {
        this.speaker_photo = speaker_photo;
    }

    public List<SessionEntity> getSessionEntities() {
        return sessionEntities;
    }

    public void setSessionEntities(List<SessionEntity> sessionEntities) {
        this.sessionEntities = sessionEntities;
    }

}
