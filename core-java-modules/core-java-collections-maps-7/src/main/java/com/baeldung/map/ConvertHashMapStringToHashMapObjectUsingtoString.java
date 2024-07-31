package com.baeldung.map;

import java.util.HashMap;
import java.util.Map;

public class ConvertHashMapStringToHashMapObjectUsingtoString {
    public String name;
    public int age;

    public ConvertHashMapStringToHashMapObjectUsingtoString(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static ConvertHashMapStringToHashMapObjectUsingtoString deserializeCustomObject(String valueString) {
        if (valueString.startsWith("{") && valueString.endsWith("}")) {
            valueString = valueString.substring(1, valueString.length() - 1);
            String[] parts = valueString.split(",");
            String name = null;
            int age = -1;
            for (String part : parts) {
                String[] keyValue = part.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0].trim();
                    String val = keyValue[1].trim();
                    if (key.equals("name")) {
                        name = val;
                    } else if (key.equals("age")) {
                        age = Integer.parseInt(val);
                    }
                }
            }
            if (name != null && age >= 0) {
                return new ConvertHashMapStringToHashMapObjectUsingtoString(name, age);
            }
        }
        return new ConvertHashMapStringToHashMapObjectUsingtoString("", -1);
    }

    public static void main(String[] args) {
        String hashMapString = "{key1={name=John, age=30}, key2={name=Alice, age=25}}";
        String keyValuePairs = hashMapString.replaceAll("[{}\\s]", "");
        String[] pairs = keyValuePairs.split(",");
        Map<String, ConvertHashMapStringToHashMapObjectUsingtoString> actualHashMap = new HashMap<>();
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0];
                ConvertHashMapStringToHashMapObjectUsingtoString value = deserializeCustomObject(keyValue[1]);
                actualHashMap.put(key, value);
            }
        }
        System.out.println(actualHashMap);
    }

    @Override
    public String toString() {
        return "{name=" + name + ", age=" + age + "}";
    }
}
