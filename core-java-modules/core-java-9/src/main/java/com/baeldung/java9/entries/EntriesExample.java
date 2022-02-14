package com.baeldung.java9.entries;

import com.google.common.collect.Maps;
import org.apache.commons.collections4.KeyValue;
import org.apache.commons.collections4.keyvalue.DefaultMapEntry;
import org.apache.commons.collections4.keyvalue.UnmodifiableMapEntry;

import java.util.AbstractMap;
import java.util.Map;

public class EntriesExample {

    public static void main(String[] args) {
        EntriesExample app = new EntriesExample();

        app.createSimpleEntry();
        app.createSimpleImmutableEntry();
        app.createEntryJava9();
        app.createEntryWithApacheCommons();
        app.createUnmodifiableEntryWithApacheCommons();
        app.createEntryWithGuava();
        app.createCustomEntry();
    }

    public void createSimpleEntry() {
        AbstractMap.SimpleEntry<String, String> firstEntry = new AbstractMap.SimpleEntry<>("key1", "value1");
        AbstractMap.SimpleEntry<String, String> secondEntry = new AbstractMap.SimpleEntry<>("key2", "value2");
        AbstractMap.SimpleEntry<String, String> thirdEntry = new AbstractMap.SimpleEntry<>(firstEntry);
        thirdEntry.setValue("a different value");

        System.out.println(firstEntry);
        System.out.println(secondEntry);
        System.out.println(thirdEntry);
    }

    public void createSimpleImmutableEntry() {
        AbstractMap.SimpleImmutableEntry<String, String> firstEntry = new AbstractMap.SimpleImmutableEntry<>("key1", "value1");
        AbstractMap.SimpleImmutableEntry<String, String> secondEntry = new AbstractMap.SimpleImmutableEntry<>("key2", "value2");
        AbstractMap.SimpleImmutableEntry<String, String> thirdEntry = new AbstractMap.SimpleImmutableEntry<>(firstEntry);

        System.out.println(firstEntry);
        System.out.println(secondEntry);
        System.out.println(thirdEntry);
    }

    public void createEntryJava9() {
        Map.Entry<String, String> entry = Map.entry("key", "value");

        System.out.println(entry);
    }

    public void createEntryWithApacheCommons() {
        Map.Entry<String, String> firstEntry = new DefaultMapEntry<>("key1", "value1");
        KeyValue<String, String> secondEntry = new DefaultMapEntry<>("key2", "value2");

        KeyValue<String, String> thirdEntry = new DefaultMapEntry<>(firstEntry);
        KeyValue<String, String> fourthEntry = new DefaultMapEntry<>(secondEntry);

        firstEntry.setValue("a different value");

        System.out.println(firstEntry);
        System.out.println(secondEntry);
        System.out.println(thirdEntry);
        System.out.println(fourthEntry);
    }

    public void createUnmodifiableEntryWithApacheCommons() {
        Map.Entry<String, String> firstEntry = new UnmodifiableMapEntry<>("key1", "value1");
        KeyValue<String, String> secondEntry = new UnmodifiableMapEntry<>("key2", "value2");

        KeyValue<String, String> thirdEntry = new UnmodifiableMapEntry<>(firstEntry);
        KeyValue<String, String> fourthEntry = new UnmodifiableMapEntry<>(secondEntry);

        System.out.println(firstEntry);
        System.out.println(secondEntry);
        System.out.println(thirdEntry);
        System.out.println(fourthEntry);
    }

    public void createEntryWithGuava() {
        Map.Entry<String, String> firstEntry = Maps.immutableEntry("key1", "value1");
        Map.Entry<String, String> secondEntry = Maps.immutableEntry("key2", "value2");

        System.out.println(firstEntry);
        System.out.println(secondEntry);
    }

    public void createCustomEntry() {
        SimpleCustomKeyValue<String, String> firstEntry = new SimpleCustomKeyValue<>();
        firstEntry.setKey("key1");
        firstEntry.setValue("value1");

        System.out.println(firstEntry);

        SimpleCustomKeyValue<String, String> secondEntry = new SimpleCustomKeyValue<>("key2", "value2");
        System.out.println(secondEntry);
        secondEntry.setKey("different key");
        System.out.println(secondEntry);
    }
}
