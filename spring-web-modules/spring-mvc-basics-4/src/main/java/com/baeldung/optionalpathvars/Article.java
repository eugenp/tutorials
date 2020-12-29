package com.baeldung.optionalpathvars;

public class Article {

    public static final Article DEFAULT_ARTICLE = new Article(12);

    private Integer id;

    public Article(Integer articleId) {
        this.id = articleId;
    }

    public Integer getId() {
        return id;
    }
    
    @Override
    public String toString() {
        return "Article [id=" + id + "]";
    }

}
