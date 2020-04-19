package com.baeldung.powermockito.introduction;

class LuckyNumberGenerator {

    public int getLuckyNumber(String name) {

        saveIntoDatabase(name);

        if (name == null) {
            return getDefaultLuckyNumber();
        }

        return getComputedLuckyNumber(name.length());
    }

    private void saveIntoDatabase(String name) {
        // Save the name into the database
    }

    private int getDefaultLuckyNumber() {
        return 100;
    }

    private int getComputedLuckyNumber(int length) {
        return length < 5 ? 5 : 10000;
    }

}
