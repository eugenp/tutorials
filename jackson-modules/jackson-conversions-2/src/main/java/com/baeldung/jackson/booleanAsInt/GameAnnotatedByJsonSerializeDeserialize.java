package com.baeldung.jackson.booleanAsInt;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class GameAnnotatedByJsonSerializeDeserialize {

    private Long id;
    private String name;

    @JsonSerialize(using = NumericBooleanSerializer.class)
    @JsonDeserialize(using = NumericBooleanDeserializer.class)
    private Boolean paused;

    @JsonSerialize(using = NumericBooleanSerializer.class)
    @JsonDeserialize(using = NumericBooleanDeserializer.class)
    private Boolean over;

    public GameAnnotatedByJsonSerializeDeserialize() {
    }

    public GameAnnotatedByJsonSerializeDeserialize(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isPaused() {
        return paused;
    }

    public void setPaused(Boolean paused) {
        this.paused = paused;
    }

    public Boolean isOver() {
        return over;
    }

    public void setOver(Boolean over) {
        this.over = over;
    }
}
