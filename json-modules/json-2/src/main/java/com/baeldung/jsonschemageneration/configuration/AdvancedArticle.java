package com.baeldung.jsonschemageneration.configuration;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

enum Area {
    JAVA("JAVA"), KOTLIN("KOTLIN"), SCALA("SCALA"), LINUX("LINUX");

    private final String area;

    Area(String area) {
        this.area = area;
    }

    public String getArea() {
        return area;
    }
}

public class AdvancedArticle {
    private UUID id;
    private String title;

    private String content;

    @AllowedTypes({ Timestamp.class, Date.class })
    private Object createdAt;
    private Area area;

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
