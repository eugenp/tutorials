package com.baeldung.hashtable;

public class Word {
    private String name;

    public Word(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Word))
            return false;

        Word word = (Word) o;
        return word.getName().equals(this.name) ? true : false;

    }

    public int hashCode() {
        return name.hashCode();
    }
}
