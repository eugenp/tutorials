package com.baeldung.hexagonal.application;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.baeldung.hexagonal.domain.InputService;
import com.baeldung.hexagonal.domain.OutputService;
import com.baeldung.hexagonal.domain.TextInfo;
import com.baeldung.hexagonal.domain.TextInfoService;

public class TextInfoServiceImpl implements TextInfoService {

    private InputService inputService;
    private OutputService outputService;

    @Override
    public InputService getInputService() {
        return inputService;
    }

    @Override
    public OutputService getOutputService() {
        return outputService;
    }

    @Override
    public TextInfo getInfo(String text) {
        Map<Character, Integer> charFrequency = getCharFrequency(text);
        return new TextInfo(text, charFrequency, charFrequency.keySet()
            .size());
    }

    @Override
    public Map<Character, Integer> getCharFrequency(String text) {
        Map<Character, Integer> charFrequency = new HashMap<>();
        for (Character ch : text.toCharArray()) {
            charFrequency.put(ch, (charFrequency.containsKey(ch) ? charFrequency.get(ch) : 0) + 1);
        }
        return Collections.unmodifiableMap(charFrequency);
    }

    public TextInfoServiceImpl(InputService inputService, OutputService outputService) {
        this.inputService = inputService;
        this.outputService = outputService;
    }
}
