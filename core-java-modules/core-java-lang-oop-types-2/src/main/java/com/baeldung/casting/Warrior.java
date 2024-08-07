package com.baeldung.casting;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Warrior extends Character {

    // standard constructor
    public Warrior(String name) {
        super(name);
    }

    public void obeyCommand(String command) {
        log.info("Warrior {} obeys a command {}", this.getName(), command);
    }

}
