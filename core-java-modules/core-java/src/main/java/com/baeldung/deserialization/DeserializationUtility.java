package com.baeldung.deserialization;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Base64;

public class DeserializationUtility {

    public static void main(String[] args) throws ClassNotFoundException, IOException {

        String serializedObj = "rO0ABXNyACljb20uYmFlbGR1bmcuZGVzZXJpYWxpemF0aW9uLkFwcGxlUHJvZHVjdAAAAAAAEtaHAgADTAANaGVhZHBob25lUG9ydHQAEkxqYXZhL2xhbmcvU3RyaW5nO0wADWxpZ2h0bmluZ1BvcnRxAH4AAUwAD3RodW5kZXJib2x0UG9ydHEAfgABeHB0ABFoZWFkcGhvbmVQb3J0MjAyMHQAEWxpZ2h0bmluZ1BvcnQyMDIwdAATdGh1bmRlcmJvbHRQb3J0MjAyMA==";
        System.out.println("Deserializing AppleProduct...");
        AppleProduct deserializedObj = (AppleProduct) deSerializeObjectFromString(serializedObj);
        System.out.println("Headphone port of AppleProduct:" + deserializedObj.getHeadphonePort());
        System.out.println("Thunderbolt port of AppleProduct:" + deserializedObj.getThunderboltPort());
        System.out.println("LightningPort port of AppleProduct:" + deserializedObj.getLightningPort());
    }

    public static Object deSerializeObjectFromString(String s) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(s);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        Object o = ois.readObject();
        ois.close();
        return o;
    }

}