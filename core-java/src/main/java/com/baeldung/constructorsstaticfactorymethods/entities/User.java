package com.baeldung.constructorsstaticfactorymethods.entities;

import java.time.LocalTime;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class User {

    private static User instance = null;
    private static final Logger LOGGER = Logger.getLogger(User.class.getName());
    private final String name;
    private final String email;
    private final String country;
    private static LocalTime instantiationTime;

    public static User createWithDefaultCountry(String name, String email) {
        return new User(name, email, "Argentina");
    }

    public static User createWithLoggedInstantiationTime(String name, String email, String country) {
        setLoggerProperties();
        instantiationTime = LocalTime.now();
        LOGGER.log(Level.INFO, "Creating User instance at : {0}", instantiationTime);
        return new User(name, email, country);
    }

    public static User getSingletonInstance(String name, String email, String country) {
        if (instance == null) {
            synchronized (User.class) {
                if (instance == null) {
                    instance = new User(name, email, country);
                }
            }
        }
        return instance;
    }

    private User(String name, String email, String country) {
        this.name = name;
        this.email = email;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCountry() {
        return country;
    }

    public static LocalTime getInstantiationTime() {
        return instantiationTime;
    }

    private static void setLoggerProperties() {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.INFO);
        handler.setFormatter(new SimpleFormatter());
        LOGGER.addHandler(handler);
    }
}
