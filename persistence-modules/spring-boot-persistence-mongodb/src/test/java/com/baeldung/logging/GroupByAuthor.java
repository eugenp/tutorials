package com.baeldung.logging;

import org.springframework.data.mongodb.core.mapping.Field;

public class GroupByAuthor {

    @Field("_id")
    private String authorName;
    private int authCount;

    public GroupByAuthor() {}

    public GroupByAuthor(String authorName, int authCount) {
        this.authorName = authorName;
        this.authCount = authCount;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getAuthCount() {
        return authCount;
    }

    public void setAuthCount(int authCount) {
        this.authCount = authCount;
    }

}
