package com.baeldung.pojo.shallowcopy;

import java.io.Serializable;

public class BookShelfInfoShallowCopy implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;
    private int bookId;
    private String bookName;
    private String shelfInfo;
    private BookDetailShallowCopy bookDetail;

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

    public BookShelfInfoShallowCopy() {
        super();

    }

    public BookShelfInfoShallowCopy(int bookId, String bookName, String shelfInfo, BookDetailShallowCopy bookDetail) {
        super();
        this.bookId = bookId;
        this.bookName = bookName;
        this.shelfInfo = shelfInfo;
        this.bookDetail = bookDetail;
    }

    /**
     * Copy constructor
     * @param shallow
     */
    public BookShelfInfoShallowCopy(BookShelfInfoShallowCopy shallow) {

        this.bookId = shallow.bookId;
        this.bookName = shallow.bookName;
        this.shelfInfo = shallow.shelfInfo;
        this.bookDetail = shallow.bookDetail;// shallow copy.
    }

    @Override
    public String toString() {
        return "BookShelfInfoShallowCopy [bookId=" + bookId + ", bookName=" + bookName + ", shelfInfo=" + shelfInfo + ", bookDetail=" + bookDetail + "]";
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
        BookShelfInfoShallowCopy other = (BookShelfInfoShallowCopy) obj;
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
