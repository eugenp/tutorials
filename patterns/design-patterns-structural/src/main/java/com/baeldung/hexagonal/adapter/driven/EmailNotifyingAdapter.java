package com.baeldung.hexagonal.adapter.driven;

import com.baeldung.hexagonal.ports.driven.INotify;

public class EmailNotifyingAdapter implements INotify {

    int count = 0;

    @Override
    public boolean sendNotification() {
        count++;
        return true;
    }

    public int getCount() {
        return count;
    }
}
