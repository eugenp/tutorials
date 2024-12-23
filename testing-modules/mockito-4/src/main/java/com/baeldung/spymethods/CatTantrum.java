package com.baeldung.spymethods;

public class CatTantrum {

    public enum CatAction {
        BITE,
        MEOW,
        VOMIT_ON_CARPET,
        EAT_DOGS_FOOD,
        KNOCK_THING_OFF_TABLE
    }
    public enum HumanReaction {
        SCREAM,
        CRY,
        CLEAN,
        PET_ON_HEAD,
        BITE_BACK,
    }

    public HumanReaction whatIsHumanReaction(CatAction action){
        return switch (action) {
            case MEOW -> HumanReaction.PET_ON_HEAD;
            case VOMIT_ON_CARPET -> HumanReaction.CLEAN;
            case EAT_DOGS_FOOD -> HumanReaction.SCREAM;
            case KNOCK_THING_OFF_TABLE -> HumanReaction.CRY;
            case BITE -> biteCatBack();
        };
    }

    public HumanReaction biteCatBack() {
        // Some logic
        return HumanReaction.BITE_BACK;
    }

}