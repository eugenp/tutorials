package com.baeldung.verticalslices.reader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import com.baeldung.verticalslices.events.ArticleCreatedEvent;
import org.springframework.modulith.events.ApplicationModuleListener;

@Component
class ReadArticleUseCase {

    @Autowired
    private JdbcClient jdbcClient;

    public void apply(Long id) {
    }

    @ApplicationModuleListener
    void onArticleCreated(ArticleCreatedEvent event) {
        // index article for reading
    }

}
