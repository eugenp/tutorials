package com.baeldung.concurrent.notificationForCompleteTask;

public class Callback implements CallbackInterface {

    public void taskDone(String details){
        System.out.println("task complete: " + details);
        // Alerts/notifications go here
    }

}
