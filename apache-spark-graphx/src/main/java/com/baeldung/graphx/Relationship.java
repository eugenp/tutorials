package com.baeldung.graphx;

public class Relationship {
    private String type;
    private User source;
    private User target;

    public Relationship(String type, User source, User target) {
        this.type = type;
        this.source = source;
        this.target = target;
    }

    public String getType() {
        return type;
    }

    public User getSource() {
        return source;
    }

    public User getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return getSource().toString() + " -- " + getType() + " --> " + getTarget().toString();
    }
}
