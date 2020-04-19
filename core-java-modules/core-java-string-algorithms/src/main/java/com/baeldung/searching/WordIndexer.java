package com.baeldung.searching;

import java.util.ArrayList;
import java.util.List;

public class WordIndexer {

    public List<Integer> findWord(String textString, String word) {
        int index = 0;
        List<Integer> indexes = new ArrayList<Integer>();
        String lowerCaseTextString = textString.toLowerCase();
        String lowerCaseWord = word.toLowerCase();

        while(index != -1) {
            index = lowerCaseTextString.indexOf(lowerCaseWord, index);
            if (index == -1) {
                break;
            }

            indexes.add(index);
            index++;
        }
        return indexes;
    }



    public List<Integer> findWordUpgrade(String textString, String word) {
        int index = 0;
        List<Integer> indexes = new ArrayList<Integer>();
        StringBuilder output = new StringBuilder();
        String lowerCaseTextString = textString.toLowerCase();
        String lowerCaseWord = word.toLowerCase();
        int wordLength = 0;

        while(index != -1){
            index = lowerCaseTextString.indexOf(lowerCaseWord, index + wordLength);  // Slight improvement
            if (index != -1) {
                indexes.add(index);
            }
            wordLength = word.length();
        }
        return indexes;
    }
}
