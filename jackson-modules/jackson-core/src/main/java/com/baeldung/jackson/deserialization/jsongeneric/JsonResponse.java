package com.baeldung.jackson.deserialization.jsongeneric;

public class JsonResponse<T> {

    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
