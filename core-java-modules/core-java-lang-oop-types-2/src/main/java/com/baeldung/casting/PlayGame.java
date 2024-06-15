package com.baeldung.casting;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class PlayGame {

    public void playViaClassCast(List<Character> characters, String command) {
        log.info("Playing game via a class cast operation...");
        for (Character character : characters) {
            if (character instanceof Warrior) {
                Warrior warrior = Warrior.class.cast(character);
                warrior.obeyCommand(command);
            } else if (character instanceof Commander) {
                Commander commander = Commander.class.cast(character);
                commander.issueCommand(command);
            }
        }
    }

    public void playViaCastOperator(List<Character> characters, String command) {
        log.info("Playing game via a the cast operator...");
        for (Character character : characters) {
            if (character instanceof Warrior) {
                Warrior warrior = (Warrior) character;
                warrior.obeyCommand(command);
            } else if (character instanceof Commander) {
                Commander commander = (Commander) character;
                commander.issueCommand(command);
            }
        }
    }

}