package com.baeldung.hexagonal.ports.driven;

public interface INotify {

    boolean sendNotification();

    int getCount();
}
