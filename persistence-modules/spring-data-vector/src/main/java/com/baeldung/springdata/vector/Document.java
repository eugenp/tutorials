package com.baeldung.springdata.vector;

import org.hibernate.annotations.Array;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String content;

    @Column(name = "year_published")
    private String yearPublished;

    @JdbcTypeCode(SqlTypes.VECTOR)
    @Array(length = 5)
    @Column(name = "the_embedding")
    private float[] theEmbedding;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(String yearPublished) {
        this.yearPublished = yearPublished;
    }

    public float[] getTheEmbedding() {
        return theEmbedding;
    }

    public void setTheEmbedding(float[] theEmbedding) {
        this.theEmbedding = theEmbedding;
    }
}
