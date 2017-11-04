package com.baeldung.beaninjection.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Hero {
    private Sword sword;
    private Shield shield;
    private Armor armor;


    public Hero(Sword sword, Shield shield){
        this.shield = shield;
        this.sword = sword;
    }


    @Autowired(required = false)
    public void setArmor(Armor armor) {
        this.armor = armor;
    }


    @Override
    public String toString() {
        return "Hero{" +
                "sword=" + sword +
                ", shield=" + shield +
                ", armor=" + armor +
                '}';
    }


    public Shield getShield() {
        return shield;
    }

    public Sword getSword() {
        return sword;
    }

    public Armor getArmor() {
        return armor;
    }
}
