package com.baeldung.springintegration.controllers;

import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;

@Named("helloPFMBean")
@SessionScoped
public class HelloPFMBean {

    private String magicWord;

    public String getMagicWord() {
        return magicWord;
    }

    public void setMagicWord(String magicWord) {
        this.magicWord = magicWord;
    }

    public String go() {
        if (this.magicWord != null && this.magicWord.toUpperCase()
            .equals("BAELDUNG")) {
            return "pm:success";
        }
        return "pm:failure";
    }

}
