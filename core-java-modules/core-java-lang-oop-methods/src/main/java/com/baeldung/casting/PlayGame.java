package com.baeldung.casting;

import java.util.ArrayList;
import java.util.List;

public class PlayGame {

    public List<Character> buildCharacters() {
        List<Character> characters = new ArrayList<>();
        characters.add(new Commander( "Odin"));
        characters.add(new Warrior("Thor"));
        return characters;
    }

    public void playViaClassCast(List<Character> characters, String command) {
        System.out.println("Playing game via a class cast operation...");
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
        System.out.println("Playing game via a the cast operator...");
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