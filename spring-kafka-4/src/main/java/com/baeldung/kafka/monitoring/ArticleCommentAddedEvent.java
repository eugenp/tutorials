package com.baeldung.kafka.monitoring;

public record ArticleCommentAddedEvent(String articleSlug, String articleAuthor, String comment, String commentAuthor) {

}
