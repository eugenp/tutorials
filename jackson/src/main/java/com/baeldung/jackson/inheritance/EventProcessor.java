package com.baeldung.jackson.inheritance;


public class EventProcessor {
    public void process(Event event) {
        System.out.println(event.getMetadata().getId());

        if (event instanceof AddItemIdToUser) {
            addItemIdToUser((AddItemIdToUser) event);
        } else if (event instanceof RemoveItemIdFromUser) {
            removeItemIdFromUser((RemoveItemIdFromUser) event);
        }
    }

    private void removeItemIdFromUser(RemoveItemIdFromUser event) {
        //do some stuff
    }


    private void addItemIdToUser(AddItemIdToUser event) {
        //do some stuff
    }
}
