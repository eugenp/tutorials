package com.baeldung.monitoring;

public record ArticleCommentAddedEvent(String articleSlug, String articleAuthor, String comment, String commentAuthor) {

}
