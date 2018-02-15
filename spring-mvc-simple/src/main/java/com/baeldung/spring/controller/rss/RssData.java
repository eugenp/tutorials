package com.baeldung.spring.controller.rss;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RssData implements Serializable {
    private String link;
    private String title;
    private String description;
    private String publishedDate;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        this.publishedDate = df.format(publishedDate);
    }

    @Override
    public String toString() {
        return "RssData{" +
                "link='" + link + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", publishedDate=" + publishedDate +
                '}';
    }
}
