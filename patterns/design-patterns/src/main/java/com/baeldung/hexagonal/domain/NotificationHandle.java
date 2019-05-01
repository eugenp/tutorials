package com.baeldung.hexagonal.domain;

@SuppressWarnings({ "SpellCheckingInspection", "unused" })
public class NotificationHandle {

    public enum Type {
        SMS, MAIL, PUSH
    }

    private Type type;
    private String handle;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

}
