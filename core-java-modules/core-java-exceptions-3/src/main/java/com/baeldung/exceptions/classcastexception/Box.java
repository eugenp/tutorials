package com.baeldung.exceptions.classcastexception;

public class Box<T> {

    private T content;

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
