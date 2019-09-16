package com.baeldung.core.modifiers.otherpackage;

import com.baeldung.core.modifiers.Person;

public class Knight extends Person{
    PrimarySkill skill;
    
    public Knight(String knightName, int age) {
        super("Sir",knightName, age);
        setHonorable(true);
        skill = new PrimarySkill("Fight");
    }
}
