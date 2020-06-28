package com.baeldung.deserialization;

import java.io.IOException;
import java.io.Serializable;

public class DefaultSerial implements Serializable {

    private String name;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String digest = "rO0ABXNyACpjb20uYmFlbGR1bmcuZGVzZXJpY"
          + "WxpemF0aW9uLkRlZmF1bHRTZXJpYWx9iVz3Lz/mdAIAAHhw";
        DefaultSerial instance = (DefaultSerial) DeserializationUtility.deSerializeObjectFromString(digest);
    }
}
