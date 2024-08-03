package com.baeldung.javafeatures.classfile;

import java.util.Objects;

class Data<T> {

    String id;
    long timestamp;
    T payload;

    public Data() {
    }

    public Data(String id, long timestamp, T payload) {
        this.id = id;
        this.timestamp = timestamp;
        this.payload = payload;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Data<?> other = (Data<?>) obj;
        return Objects.equals(id, other.id) && Objects.equals(payload, other.payload) && timestamp == other.timestamp;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, payload, timestamp);
    }

}