package com.baeldung.logging;

import org.springframework.data.annotation.Id;

public class GroupByAuthor {

    @Id
    private String authorName;
    private int authCount;

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
