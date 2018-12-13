package com.baeldung.hibernate.ogm;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Article {
        
        @Id
        @GeneratedValue(generator = "uuid")
        @GenericGenerator(name = "uuid", strategy = "uuid2")
        private String articleId;
        
        private String articleTitle;
        private String articleUrl;
        
        @ManyToOne
        private Author author;
        
        // constructors, getters and setters...
        
        Article() {
        }

        public Article(String articleTitle, String articleUrl) {
                this.articleTitle = articleTitle;
                this.articleUrl = articleUrl;
        }

        public String getArticleId() {
                return articleId;
        }

        public void setArticleId(String articleId) {
                this.articleId = articleId;
        }

        public String getArticleTitle() {
                return articleTitle;
        }

        public void setArticleTitle(String articleTitle) {
                this.articleTitle = articleTitle;
        }

        public String getArticleUrl() {
                return articleUrl;
        }

        public void setArticleUrl(String articleUrl) {
                this.articleUrl = articleUrl;
        }       
        
        public Author getAuthor() {
                return author;
        }

        public void setAuthor(Author author) {
                this.author = author;
        }
}