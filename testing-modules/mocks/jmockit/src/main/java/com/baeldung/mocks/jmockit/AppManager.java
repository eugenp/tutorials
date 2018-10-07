package com.baeldung.mocks.jmockit;

import java.util.Random;

public class AppManager {

    public boolean managerResponse(String question) {
        return AppManager.isResponsePositive(question);
    }

    public static boolean isResponsePositive(String value) {
        if (value == null)
            return false;
        int orgLength = value.length();
        int randomNumber = randomNumber();
        return orgLength == randomNumber ? true : false;
    }

    private static int randomNumber() {
        return new Random().nextInt(7);
    }

    private static Integer stringToInteger(String num) {
        return Integer.parseInt(num);
    }
}
