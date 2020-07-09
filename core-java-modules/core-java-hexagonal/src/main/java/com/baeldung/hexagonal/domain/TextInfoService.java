package com.baeldung.hexagonal.domain;

public interface TextInfoService extends Runnable {
    
    InputService getInputService();
    
    OutputService getOutputService();

    TextInfo getInfo(String text);
    
    java.util.Map<Character, Integer> getCharFrequency(String text);
    
    @Override
    default void run() {
        getOutputService().showInfo(getInfo(getInputService().readText()));
    }
}
