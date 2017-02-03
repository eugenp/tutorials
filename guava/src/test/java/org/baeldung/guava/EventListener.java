package org.baeldung.guava;
import com.google.common.eventbus.Subscribe;

public class EventListener {

    private static int eventsHandled;

    /**
     * Handles events of type String     *
     */
    @Subscribe
    public void stringEvent(String event){
        System.out.println("do event ["+event+"]");
        eventsHandled++;
    }

    /**
     * Handles events of type CustomEvent
     */
    @Subscribe
    public void someEvent(CustomEvent customEvent){
        System.out.println("do event ["+ customEvent.getAction()+"]");
        eventsHandled++;
    }

    public int getEventsHandled() {
        return eventsHandled;
    }

    public void resetEventsHandled(){
        eventsHandled = 0;
    }
}
