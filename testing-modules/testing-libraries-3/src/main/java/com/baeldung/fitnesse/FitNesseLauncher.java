package com.baeldung.fitnesse;

import fitnesseMain.FitNesseMain;

public class FitNesseLauncher {

    public static void main(String[] args) throws Exception {
        FitNesseMain.main(new String[] { "-p", "8080" });
    }
}
