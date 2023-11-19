package com.baeldung.jpa.primarykeys;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class BookId implements Serializable {

    private static final long serialVersionUID = 1L;
    private String title;
    private String language;

    public BookId() {

    }

    public BookId(String title, String language) {
        this.title = title;
        this.language = language;
    }

    public String getTitle() {
        return title;
    }

    public String getLanguage() {
        return language;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((language == null) ? 0 : language.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
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
        BookId other = (BookId) obj;
        if (language == null) {
            if (other.language != null)
                return false;
        } else if (!language.equals(other.language))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }

}
