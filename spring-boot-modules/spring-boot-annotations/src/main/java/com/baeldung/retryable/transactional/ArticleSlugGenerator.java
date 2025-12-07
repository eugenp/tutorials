package com.baeldung.retryable.transactional;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class ArticleSlugGenerator {

    public String randomSlug() {
        return UUID.randomUUID()
            .toString();
    }
}
