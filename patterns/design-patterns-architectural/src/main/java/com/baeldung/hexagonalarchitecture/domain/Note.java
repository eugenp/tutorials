package com.baeldung.hexagonalarchitecture.domain;

public class Note {

        String text;

        public String getText() {
                return text;
        }

        public void setText(String text) {
                this.text = text;
        }

        public int getNumCharacters() {
                int num = 0;
                char[] characters = this.text.toCharArray();
                for (char c : characters) {
                        num++;
                }
                return num;
        }

}
