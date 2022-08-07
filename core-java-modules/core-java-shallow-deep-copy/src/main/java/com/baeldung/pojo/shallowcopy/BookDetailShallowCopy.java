package com.baeldung.pojo.shallowcopy;

import java.util.Objects;

public class BookDetailShallowCopy {

    private int yearOfPublication;

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public BookDetailShallowCopy() {
        super();

    }

    public BookDetailShallowCopy(int yearOfPublication) {
        super();

        this.yearOfPublication = yearOfPublication;

    }

    @Override
    public int hashCode() {
        return Objects.hash(yearOfPublication);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BookDetailShallowCopy other = (BookDetailShallowCopy) obj;
        return yearOfPublication == other.yearOfPublication;
    }

    @Override
    public String toString() {
        return "BookDetailShallowCopy [yearOfPublication=" + yearOfPublication + "]";
    }

}
