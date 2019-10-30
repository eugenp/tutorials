package com.baeldung.openltablets.model;

public class Case {

    private User user;
    private int hourOfDay;

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public int getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(final int hourOfDay) {
        this.hourOfDay = hourOfDay;
    }
}
