package com.baeldung.faunablog.posts;

import java.time.Instant;

public record Post(String id, String title, String content, Author author, Instant created, Long version) {}
