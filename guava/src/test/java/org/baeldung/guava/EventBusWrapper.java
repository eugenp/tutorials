package org.baeldung.guava;

import com.google.common.eventbus.EventBus;

class EventBusWrapper {

    private static EventBus eventBus = new EventBus();

    static void register(Object object){
        eventBus.register(object);
    }

    static void unregister(Object object){
        eventBus.unregister(object);
    }

    static void post(Object object){
        eventBus.post(object);
    }


}
