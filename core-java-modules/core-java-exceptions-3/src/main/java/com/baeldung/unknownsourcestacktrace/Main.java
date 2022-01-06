package com.baeldung.unknownsourcestacktrace;

import com.baeldung.unknownsourcestacktrace.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final int SHORT_NAME_LIMIT = 10;

    public static void main(String[] args) {
        User user = new User();
        user.setName("Tom");

        logger.info(getGreetingMessage(user.getName()));
    }

    private static String getGreetingMessage(String name) {
        return "Welcome " + getShortenedName(name) + "!";
    }

    private static String getShortenedName(String name) {
        return name.substring(0, SHORT_NAME_LIMIT);
    }
}
