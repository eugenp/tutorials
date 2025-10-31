package com.baeldung.setnullproperty.dto;

import java.util.Objects;

public class ReviewableDTO {

    private String title;

    public ReviewableDTO() {
    }

    public ReviewableDTO(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReviewableDTO that = (ReviewableDTO) o;
        return Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(title);
    }
}
