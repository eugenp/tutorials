package com.baeldung.verticalslices.events;

public record ArticleCreatedEvent (String slug, String name, String category) {
}
