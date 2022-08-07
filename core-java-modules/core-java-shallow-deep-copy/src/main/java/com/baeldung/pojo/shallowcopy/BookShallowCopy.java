package com.baeldung.pojo.shallowcopy;

import java.io.Serializable;
import java.util.Objects;

public class BookShallowCopy implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;
    private String bookName;
    private BookDetailShallowCopy bookDetail;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public BookDetailShallowCopy getBookDetail() {
        return bookDetail;
    }

    public void setBookDetail(BookDetailShallowCopy bookDetail) {
        this.bookDetail = bookDetail;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public BookShallowCopy() {
        super();
    }

    public BookShallowCopy(String bookName, BookDetailShallowCopy bookDetail) {
        super();
        this.bookName = bookName;
        this.bookDetail = bookDetail;
    }

    public BookShallowCopy(BookShallowCopy shallow) {
        this.bookName = shallow.bookName;
        this.bookDetail = shallow.bookDetail;
    }

    @Override
    public String toString() {
        return "BookShelfInfoShallowCopy [bookName=" + bookName + ", bookDetail=" + bookDetail + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookDetail, bookName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BookShallowCopy other = (BookShallowCopy) obj;
        return Objects.equals(bookDetail, other.bookDetail) && Objects.equals(bookName, other.bookName);
    }

}
