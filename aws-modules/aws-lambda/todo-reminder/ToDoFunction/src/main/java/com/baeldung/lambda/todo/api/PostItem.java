package com.baeldung.lambda.todo.api;

public class PostItem {
    private String title;
    private String body;
    private int userId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "PostItem{"
          + "title='" + title + '\''
          + ", body='" + body + '\''
          + ", userId=" + userId +
          '}';
    }
}
