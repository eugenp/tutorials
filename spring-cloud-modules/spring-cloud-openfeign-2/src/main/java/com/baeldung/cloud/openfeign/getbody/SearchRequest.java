package com.baeldung.cloud.openfeign.getbody;

public class SearchRequest {
    private String keyword;
    private String category;

    public SearchRequest() {

    }

    public SearchRequest(String keyword, String category) {
        this.keyword = keyword;
        this.category = category;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}