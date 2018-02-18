package com.baeldung.spring.controller.rss;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JacksonXmlRootElement(localName="articles")
public class ArticleFeed extends RssData implements Serializable {

    @JacksonXmlElementWrapper(localName = "items", useWrapping = true)
    private List<ArticleItem> items = new ArrayList<ArticleItem>();

    public void addItem(ArticleItem articleItem) {
        this.items.add(articleItem);
    }

    public List<ArticleItem> getItems() {
        return items;
    }

    public void setItems(List<ArticleItem> items) {
        this.items = items;
    }
}
