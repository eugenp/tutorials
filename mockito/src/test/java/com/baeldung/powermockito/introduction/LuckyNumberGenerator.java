package com.baeldung.powermockito.introduction;

class LuckyNumberGenerator {

    public int getLuckyNumber(String name) {

        record(name);

        if (name == null) {
            return getDefaultLuckyNumber();
        }

        return getLuckyNumber(name.length() + 1);
    }

    private void record(String name) {
    }

    private int getDefaultLuckyNumber() {
        return 100;
    }

    private int getLuckyNumber(int length) {
        return length < 5 ? 5 : 10000;
    }

}
