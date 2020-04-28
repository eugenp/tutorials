package com.baeldung.s;

public class TextManipulator {
    private String text;

    public TextManipulator(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void appendText(String newText) {
        text = text.concat(newText);
    }

    public void findWordAndReplace(String word, String replacementWord) {
        if (text.contains(word)) {
            text = text.replace(word, replacementWord);
        } else System.out.println("Word you want to replace is not found in the text");
    }

    public void findWordAndDelete(String word) {
        if (text.contains(word)) {
            text = text.replace(word, "");
        } else System.out.println("Word you want to delete is not found in the text");
    }

 /*
  * Bad practice when implementing SRP principle, not in the scope of this class
    public void printText() {
        System.out.println(textManipulator.getText());
    }*/
}
