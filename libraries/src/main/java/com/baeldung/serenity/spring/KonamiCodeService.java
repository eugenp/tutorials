package com.baeldung.serenity.spring;

import org.springframework.stereotype.Service;

/**
 * refer to <a href="https://en.wikipedia.org/wiki/Konami_Code">Konami Code</a>
 */
@Service
public class KonamiCodeService {

    private String classicCode = "↑↑↓↓←→←→BA";

    public String getClassicCode() {
        return classicCode;
    }

    public void alterClassicCode(String newCode) {
        classicCode = newCode;
    }

    public boolean cheatWith(String cheatcode) {
        if ("↑↑↓↓←→←→BA".equals(cheatcode)) {
            stageLeft++;
            return true;
        }
        return false;
    }

    private int stageLeft = 1;

    public void clearStage() {
        stageLeft = 0;
    }

    public int stageLeft() {
        return stageLeft;
    }

}
