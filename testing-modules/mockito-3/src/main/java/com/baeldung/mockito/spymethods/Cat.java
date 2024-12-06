package com.baeldung.mockito.spymethods;

public class Cat {

    public Cat(Human human){
        myHuman = human;
    }

    public Cat(){

    }

    Human myHuman;

    public enum Action {
        BITE,
        MEOW,
        VOMIT_ON_CARPET,
        EAT_DOGS_FOOD,
        KNOCK_THING_OFF_TABLE
    }

    public Human.HumanReaction whatIsHumanReaction(Action action){
        return switch (action) {
            case MEOW -> Human.HumanReaction.PET_ON_HEAD;
            case VOMIT_ON_CARPET -> Human.HumanReaction.CLEAN;
            case EAT_DOGS_FOOD -> Human.HumanReaction.SCREAM;
            case KNOCK_THING_OFF_TABLE -> Human.HumanReaction.CRY;
            case BITE -> myHuman.biteCatBack();
        };
    }

}