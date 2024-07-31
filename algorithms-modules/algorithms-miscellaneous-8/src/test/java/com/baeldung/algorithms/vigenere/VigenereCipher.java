package com.baeldung.algorithms.vigenere;

public class VigenereCipher {
    private final String characters;

    public VigenereCipher() {
        this("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    public VigenereCipher(String characters) {
        this.characters = characters;
    }

    public String encode(String input, String key) {
        String result = "";

        int keyPosition = 0;
        for (char c : input.toCharArray()) {
            char k = key.charAt(keyPosition % key.length());

            int charIndex = characters.indexOf(c);
            int keyIndex = characters.indexOf(k);

            if (charIndex >= 0) {
                if (keyIndex >= 0) {
                    int newCharIndex = (charIndex + keyIndex + 1) % characters.length();
                    c = characters.charAt(newCharIndex);

                }

                keyPosition++;
            }

            result += c;
        }

        return result;
    }

    public String decode(String input, String key) {
        String result = "";

        int keyPosition = 0;
        for (char c : input.toCharArray()) {
            char k = key.charAt(keyPosition % key.length());

            int charIndex = characters.indexOf(c);
            int keyIndex = characters.indexOf(k);

            if (charIndex >= 0) {
                if (keyIndex >= 0) {
                    int newCharIndex = charIndex - keyIndex - 1;
                    if (newCharIndex < 0) {
                        newCharIndex = characters.length() + newCharIndex;
                    }
                    c = characters.charAt(newCharIndex);

                }

                keyPosition++;
            }

            result += c;
        }

        return result;
    }

}
