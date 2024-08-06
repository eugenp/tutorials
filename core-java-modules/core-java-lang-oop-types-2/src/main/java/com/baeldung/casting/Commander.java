package com.baeldung.casting;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Commander extends Character {

    public Commander(String name) {
        super(name);
    }

    public void issueCommand(String command) {
        log.info("Commander {} issues a command {}", this.getName(), command);
    }
}
