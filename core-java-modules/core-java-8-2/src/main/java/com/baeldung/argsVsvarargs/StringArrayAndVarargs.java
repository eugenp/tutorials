package com.baeldung.argsVsvarargs;

public class StringArrayAndVarargs {
    public static void capitalizeNames(String[] args) {
        for(int i = 0; i < args.length; i++){
           args[i] = args[i].toUpperCase();
        }

    }

    public static String[] firstLetterOfWords(String... args) {
        String[] firstLetter = new String[args.length];
        for(int i = 0; i < args.length; i++){
            firstLetter[i] = String.valueOf(args[i].charAt(0));
        }
        return firstLetter;
    }

}
