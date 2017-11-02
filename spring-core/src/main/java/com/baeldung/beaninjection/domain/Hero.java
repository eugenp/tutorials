package com.baeldung.beaninjection.domain;

import org.springframework.stereotype.Component;

@Component
public class Hero {
    private Sword sword;
    private Shield shield;
    private Armor armor;
}
