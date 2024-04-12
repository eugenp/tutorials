package com.baeldung.factorygeneric;

public class StringNotifier implements Notifier<String> {

    @Override
    public void notify(String str) {
        System.out.println("Notifying: " + str);
    }
}
