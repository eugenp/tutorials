package com.baeldung.string.searching;

import java.util.ArrayList;

public class WordIndexer {

    public List<Integer> findWord(String textString, String word) {
        int index = -1;
        List<Integer> indexes = new ArrayList<Integer>();
        StringBuilder output = new StringBuilder();
        boolean done = false;
        String lowerCaseTextString = textString.toLowerCase();
        String lowerCaseWord = word.toLowerCase();
        int wordLength = word.length();

        while(!done){
            index = lowerCaseTextString.indexOf(lowerCaseWord, index + wordLength);  // Slight improvement
            if (index != -1) {
                indexes.add(index);
            }else{
                done = true;
            }
        }
        return indexes;
    }

    public List<Integer> findWordUpgrade(String textString, String word) {
        int index = -1;
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        StringBuilder output = new StringBuilder();
        boolean done = false;
        String lowerCaseTextString = textString.toLowerCase();
        String lowerCaseWord = word.toLowerCase();
        int wordLength = word.length();

        while(!done){
            index = lowerCaseTextString.indexOf(lowerCaseWord, index + wordLength);  // Slight improvement
            if (index != -1) {
                indexes.add(index);
            }else{
                done = true;
            }
        }
        return indexes;
    }
}
