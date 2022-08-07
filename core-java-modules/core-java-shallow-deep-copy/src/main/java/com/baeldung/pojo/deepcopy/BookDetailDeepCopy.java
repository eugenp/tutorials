package com.baeldung.pojo.deepcopy;

import java.io.Serializable;
import java.util.Objects;

public class BookDetailDeepCopy implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;
    private int yearOfPublication;

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public BookDetailDeepCopy() {
        super();
    }

    public BookDetailDeepCopy(int yearOfPublication) {
        super();
        this.yearOfPublication = yearOfPublication;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public BookDetailDeepCopy(BookDetailDeepCopy bookDetail) {
        this.yearOfPublication = bookDetail.getYearOfPublication();
    }

    @Override
    public String toString() {
        return "BookDetailDeepCopy [yearOfPublication=" + yearOfPublication + "]";
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
        BookDetailDeepCopy other = (BookDetailDeepCopy) obj;
        return yearOfPublication == other.yearOfPublication;
    }

}
