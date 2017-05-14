package com.baeldung.spring.di;

import org.springframework.beans.factory.annotation.Lookup;

public abstract class LookupMethodApplication {

    public abstract MessageService createServiceFromXML();

    @Lookup
    public abstract MessageService createServiceFromAnnotation();
}
