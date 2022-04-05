package com.baeldung.couchbase.spring.person;

public class Person {

    private String id;
    private String type;
    private String name;
    private String homeTown;

    Person() {
    }

    public Person(Builder b) {
        this.id = b.id;
        this.type = b.type;
        this.name = b.name;
        this.homeTown = b.homeTown;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public static class Builder {
        private String id;
        private String type;
        private String name;
        private String homeTown;

        public static Builder newInstance() {
            return new Builder();
        }

        public Person build() {
            return new Person(this);
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder homeTown(String homeTown) {
            this.homeTown = homeTown;
            return this;
        }
    }
}
