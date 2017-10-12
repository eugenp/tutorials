package com.baeldung.caffeine;

final class DataObject {
    private final String data;

    private static int objectCounter = 0;

    private DataObject(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "DataObject{" +
                "data='" + data + '\'' +
                '}';
    }

    public static DataObject get(String data) {
        objectCounter++;
        System.out.println(String.format("Initializing DataObject#%d with data '%s'", objectCounter, data));
        return new DataObject(data);
    }
}
