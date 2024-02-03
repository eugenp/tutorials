package com.baeldung.springmodulith.events.externalization;

import org.springframework.modulith.events.Externalized;

@Externalized("baeldung.article.published::#{slug()}")
public record ArticlePublishedEvent(String slug, String title) {
}
