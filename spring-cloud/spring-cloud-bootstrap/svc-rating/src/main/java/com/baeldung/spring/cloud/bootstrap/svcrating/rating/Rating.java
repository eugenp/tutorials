package com.baeldung.spring.cloud.bootstrap.svcrating.rating;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rating implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3308900941650386473L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long bookId;
    private int stars;

    @Transient
    private boolean fromCache;
    @Transient
    private Long cachedTS = -1L;

    public Rating() {
    }

    public Rating(Long id, Long bookId, int stars) {
        this.id = id;
        this.bookId = bookId;
        this.stars = stars;
    }

    public Rating(Long bookId, int stars) {
        this.bookId = bookId;
        this.stars = stars;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public boolean isFromCache() {
        return fromCache;
    }

    public void setFromCache(boolean fromCache) {
        this.fromCache = fromCache;
    }

    public Long getCachedTS() {
        return cachedTS;
    }

    public void setCachedTS(Long cachedTS) {
        this.cachedTS = cachedTS;
    }

}
