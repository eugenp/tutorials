package com.baeldung.impl;

import com.baeldung.service.TranslatingPort;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class TranslatingPortMapAdapter implements TranslatingPort {

    private static Map<Map.Entry<String, String>, String> translator;

    static {
        translator = new HashMap<>();
        translator.put(new AbstractMap.SimpleEntry<>("Hello", "en"), "Hello");
        translator.put(new AbstractMap.SimpleEntry<>("Hello", "de"), "Hallo");
        translator.put(new AbstractMap.SimpleEntry<>("Hello", "es"), "Hola");
    }

    @Override
    public String translate(String greeting, String lang) {
        return translator.get(new AbstractMap.SimpleEntry<>(greeting, lang));
    }
}
