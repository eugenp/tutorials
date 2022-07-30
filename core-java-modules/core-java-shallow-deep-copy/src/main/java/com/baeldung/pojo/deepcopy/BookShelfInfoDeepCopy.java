package com.baeldung.pojo.deepcopy;

import java.io.Serializable;

public class BookShelfInfoDeepCopy implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;
    private int bookId;
    private String bookName;
    private String shelfInfo;
    private BookDetailDeepCopy bookDetail;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getShelfInfo() {
        return shelfInfo;
    }

    public void setShelfInfo(String shelfInfo) {
        this.shelfInfo = shelfInfo;
    }

    public BookDetailDeepCopy getBookDetail() {
        return bookDetail;
    }

    public void setBookDetail(BookDetailDeepCopy bookDetail) {
        this.bookDetail = bookDetail;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        BookShelfInfoDeepCopy shelfInfoClone = (BookShelfInfoDeepCopy) super.clone();
        shelfInfoClone.setBookDetail((BookDetailDeepCopy) bookDetail.clone());
        return shelfInfoClone;
    }

    public BookShelfInfoDeepCopy() {
        super();

    }

    public BookShelfInfoDeepCopy(int bookId, String bookName, String shelfInfo, BookDetailDeepCopy bookDetail) {
        super();
        this.bookId = bookId;
        this.bookName = bookName;
        this.shelfInfo = shelfInfo;
        this.bookDetail = bookDetail;
    }

    /**
     * copy constructor to achieve deep copy
     * @param shelfInfo
     */
    public BookShelfInfoDeepCopy(BookShelfInfoDeepCopy shelfInfo) {

        this.bookId = shelfInfo.bookId;
        this.bookName = shelfInfo.bookName;
        this.shelfInfo = shelfInfo.shelfInfo;
        this.bookDetail = new BookDetailDeepCopy(shelfInfo.getBookDetail()); // deep copy.

    }

    @Override
    public String toString() {
        return "BookShelfInfoDeepCopy [bookId=" + bookId + ", bookName=" + bookName + ", shelfInfo=" + shelfInfo + ", bookDetail=" + bookDetail + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bookDetail == null) ? 0 : bookDetail.hashCode());
        result = prime * result + bookId;
        result = prime * result + ((bookName == null) ? 0 : bookName.hashCode());
        result = prime * result + ((shelfInfo == null) ? 0 : shelfInfo.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BookShelfInfoDeepCopy other = (BookShelfInfoDeepCopy) obj;
        if (bookDetail == null) {
            if (other.bookDetail != null)
                return false;
        } else if (!bookDetail.equals(other.bookDetail))
            return false;
        if (bookId != other.bookId)
            return false;
        if (bookName == null) {
            if (other.bookName != null)
                return false;
        } else if (!bookName.equals(other.bookName))
            return false;
        if (shelfInfo == null) {
            if (other.shelfInfo != null)
                return false;
        } else if (!shelfInfo.equals(other.shelfInfo))
            return false;
        return true;
    }

}
