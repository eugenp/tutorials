package com.baeldung.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "MyArticle")
@Table(name = Article.TABLE_NAME)
public class Article {

    public static final String TABLE_NAME = "ARTICLES";

}
