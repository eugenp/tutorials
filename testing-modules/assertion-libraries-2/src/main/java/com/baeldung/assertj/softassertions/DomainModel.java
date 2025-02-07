package com.baeldung.assertj.softassertions;

public class DomainModel {
    private String id;
    private Integer type;
    private String status;

    public DomainModel(String id, Integer type, String status) {
        this.id = id;
        this.type = type;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public DomainModel setId(String id) {
        this.id = id;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public DomainModel setType(Integer type) {
        this.type = type;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public DomainModel setStatus(String status) {
        this.status = status;
        return this;
    }
}
