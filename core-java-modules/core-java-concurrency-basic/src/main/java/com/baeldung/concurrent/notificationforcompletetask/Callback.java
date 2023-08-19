package com.baeldung.concurrent.notificationforcompletetask;

public class Callback implements CallbackInterface {

    public void taskDone(String details){
        System.out.println("task complete: " + details);
        // Alerts/notifications go here
    }

}
