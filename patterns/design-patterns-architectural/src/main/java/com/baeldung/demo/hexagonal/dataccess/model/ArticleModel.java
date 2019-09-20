package com.baeldung.demo.hexagonal.dataccess.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "articles")
public class ArticleModel implements Serializable {
    
	private static final long serialVersionUID = 8710744770046873131L;

	@Id
    @GeneratedValue
    @Column(name = "articleId")
    private Integer articleId;
    
    @Column(name = "authorName")
    private String authorName;
	
    @Column(name = "content")
    private String content;

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}