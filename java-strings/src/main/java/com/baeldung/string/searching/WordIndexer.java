package com.baeldung.string.searching;

public class WordIndexer {

    public String findWord(String textString, String word) {
        int index = -1;
        int count = 0;
        StringBuilder output = new StringBuilder();
        boolean done = false;
        String lowerCaseTextString = textString.toLowerCase();
        String lowerCaseWord = word.toLowerCase();

        while(!done){

            index = lowerCaseTextString.indexOf(lowerCaseWord, index + 1);
            if (index != -1) {
                count++;
                String foundString = textString.substring(index);
                if(foundString.length() > 17){
                    output.append(index + " " + foundString.substring(0, 17) + "\n" );
                }else{
                    output.append(index + " " + foundString.substring(0,foundString.length()-1) + "\n" );
                }

            }else{
                done = true;
            }
        }
        output.append("\nThe string '" + word + "' was found " + count +" times.");
        return output.toString();
    }
}
