package org.baeldung.persistence.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String subreddit;

    @Column(nullable = false)
    private String url;

    private boolean sendReplies;

    @Column(nullable = false)
    private Date submissionDate;

    private boolean isSent;

    private String submissionResponse;

    private String redditID;

    private int noOfAttempts;

    private int timeInterval;

    private int minScoreRequired;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Post() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isSendReplies() {
        return sendReplies;
    }

    public void setSendReplies(boolean sendReplies) {
        this.sendReplies = sendReplies;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean isSent) {
        this.isSent = isSent;
    }

    public String getSubmissionResponse() {
        return submissionResponse;
    }

    public void setSubmissionResponse(String submissionResponse) {
        this.submissionResponse = submissionResponse;
    }

    public String getRedditID() {
        return redditID;
    }

    public void setRedditID(String redditID) {
        this.redditID = redditID;
    }

    public int getNoOfAttempts() {
        return noOfAttempts;
    }

    public void setNoOfAttempts(int noOfAttempts) {
        this.noOfAttempts = noOfAttempts;
    }

    public int getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(int timeInterval) {
        this.timeInterval = timeInterval;
    }

    public int getMinScoreRequired() {
        return minScoreRequired;
    }

    public void setMinScoreRequired(int minScoreRequired) {
        this.minScoreRequired = minScoreRequired;
    }

    @Override
    public String toString() {
        return "Post [title=" + title + ", subreddit=" + subreddit + ", url=" + url + ", submissionDate=" + submissionDate + ", user=" + user + "]";
    }

}