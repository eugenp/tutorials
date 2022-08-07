package com.baeldung.pojo.deepcopy;

import java.io.Serializable;
import java.util.Objects;

public class BookDeepCopy implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;
    private String bookName;

    private BookDetailDeepCopy bookDetail;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public BookDetailDeepCopy getBookDetail() {
        return bookDetail;
    }

    public void setBookDetail(BookDetailDeepCopy bookDetail) {
        this.bookDetail = bookDetail;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        BookDeepCopy shelfInfoClone = (BookDeepCopy) super.clone();
        shelfInfoClone.setBookDetail((BookDetailDeepCopy) bookDetail.clone());
        return shelfInfoClone;
    }

    public BookDeepCopy() {
        super();

    }

    public BookDeepCopy(String bookName, BookDetailDeepCopy bookDetail) {
        super();

        this.bookName = bookName;

        this.bookDetail = bookDetail;
    }

    public BookDeepCopy(BookDeepCopy shelfInfo) {

        this.bookName = shelfInfo.bookName;

        this.bookDetail = new BookDetailDeepCopy(shelfInfo.getBookDetail());

    }

    @Override
    public String toString() {
        return "BookShelfInfoDeepCopy [bookName=" + bookName + ", bookDetail=" + bookDetail + "]";
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
        BookDeepCopy other = (BookDeepCopy) obj;
        return Objects.equals(bookDetail, other.bookDetail) && Objects.equals(bookName, other.bookName);
    }

}
