package com.baeldung.spring.data.couchbase.model;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.geo.Point;

import com.couchbase.client.java.repository.annotation.Field;

@Document
public class Campus {

    @Id
    private String id;
    @Field
    @NotNull
    private String name;
    @Field
    @NotNull
    private Point location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        if (id != null) {
            hash = hash * 31 + id.hashCode();
        }
        if (name != null) {
            hash = hash * 31 + name.hashCode();
        }
        if (location != null) {
            hash = hash * 31 + location.hashCode();
        }
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        if (obj == this)
            return true;
        Campus other = (Campus) obj;
        return this.hashCode() == other.hashCode();
    }

    @SuppressWarnings("unused")
    private Campus() {
    }

    public Campus(Builder b) {
        this.id = b.id;
        this.name = b.name;
        this.location = b.location;
    }

    public static class Builder {
        private String id;
        private String name;
        private Point location;

        public static Builder newInstance() {
            return new Builder();
        }

        public Campus build() {
            return new Campus(this);
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder location(Point location) {
            this.location = location;
            return this;
        }
    }
}
