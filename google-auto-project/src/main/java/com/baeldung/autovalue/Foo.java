package com.baeldung.autovalue;

import java.util.Objects;

public final class Foo {
    private final String text;
    private final int number;

    public Foo(String text, int number) {
        this.text = text;
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, number);
    }

    @Override
    public String toString() {
        return "Foo [text=" + text + ", number=" + number + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Foo other = (Foo) obj;
        if (number != other.number)
            return false;
        if (text == null) {
            if (other.text != null)
                return false;
        } else if (!text.equals(other.text))
            return false;
        return true;
    }

}
