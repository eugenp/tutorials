package com.baeldung.guice.provider;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class MyService {
    private final Notifier emailNotifier;
    private final Notifier phoneNotifier;

    @Inject
    public MyService(@Named("Email") Notifier emailNotifier, @Named("Phone") Notifier phoneNotifier) {
        this.emailNotifier = emailNotifier;
        this.phoneNotifier = phoneNotifier;
    }
}