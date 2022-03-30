package com.baeldung.springboot.swagger.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

public class Article {

    //@JsonIgnore
    //@JsonProperty(access = JsonProperty.Access.READ_ONLY)
    //@ApiModelProperty(hidden = true)
    @ApiParam(hidden = true)
    //@ApiModelProperty(readOnly = true)
    private int id;
    private String title;
    private int numOfWords;

    public Article() {
    }

    public Article(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumOfWords() {
        return numOfWords;
    }

    public void setNumOfWords(int numOfWords) {
        this.numOfWords = numOfWords;
    }

}
