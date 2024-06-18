package com.baeldung.verticalslices.reader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
class ReadArticleUseCase {

    @Autowired
    private JdbcClient jdbcClient;

    public void apply(Long id) {
    }

}
