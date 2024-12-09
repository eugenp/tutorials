package com.baeldung.spymethods;

public class Human {

    boolean isHumanAngry = false;

    public enum HumanReaction {
        SCREAM,
        CRY,
        CLEAN,
        PET_ON_HEAD,
        BITE_BACK,
    }
    public HumanReaction biteCatBack() {
        System.out.println("Running to catch cat");
        System.out.println("Apologizing in advance");
        isHumanAngry = true;
        return HumanReaction.BITE_BACK;
    }
}
