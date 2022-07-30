package com.baeldung.pojo.deepcopy;

import java.io.Serializable;

public class BookDetailDeepCopy implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;
    private String authorName;
    private String publicationName;
    private int yearOfPublication;
    private int editionNumber;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getPublicationName() {
        return publicationName;
    }

    public void setPublicationName(String publicationName) {
        this.publicationName = publicationName;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public int getEditionNumber() {
        return editionNumber;
    }

    public void setEditionNumber(int editionNumber) {
        this.editionNumber = editionNumber;
    }

    public BookDetailDeepCopy() {
        super();

    }

    public BookDetailDeepCopy(String authorName, String publicationName, int yearOfPublication, int editionNumber) {
        super();
        this.authorName = authorName;
        this.publicationName = publicationName;
        this.yearOfPublication = yearOfPublication;
        this.editionNumber = editionNumber;
    }

    @Override
    public String toString() {
        return "BookDetailDeepCopy [authorName=" + authorName + ", publicationName=" + publicationName + ", yearOfPublication=" + yearOfPublication + ", editionNumber=" + editionNumber + "]";
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((authorName == null) ? 0 : authorName.hashCode());
        result = prime * result + editionNumber;
        result = prime * result + ((publicationName == null) ? 0 : publicationName.hashCode());
        result = prime * result + yearOfPublication;
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
        BookDetailDeepCopy other = (BookDetailDeepCopy) obj;
        if (authorName == null) {
            if (other.authorName != null)
                return false;
        } else if (!authorName.equals(other.authorName))
            return false;
        if (editionNumber != other.editionNumber)
            return false;
        if (publicationName == null) {
            if (other.publicationName != null)
                return false;
        } else if (!publicationName.equals(other.publicationName))
            return false;
        if (yearOfPublication != other.yearOfPublication)
            return false;
        return true;
    }

    public BookDetailDeepCopy(BookDetailDeepCopy bookDetail) {
        this.authorName = bookDetail.getAuthorName();
        this.editionNumber = bookDetail.getEditionNumber();
        this.publicationName = bookDetail.getPublicationName();
        this.yearOfPublication = bookDetail.getYearOfPublication();
    }

}
