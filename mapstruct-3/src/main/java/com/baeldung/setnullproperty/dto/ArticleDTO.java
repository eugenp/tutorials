package com.baeldung.setnullproperty.dto;

import java.util.Objects;

public class ArticleDTO extends ReviewableDTO {

    private String title;

    public ArticleDTO(String title) {
        this.title = title;
    }

    public ArticleDTO() {
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
        ArticleDTO that = (ArticleDTO) o;
        return Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(title);
    }
}
