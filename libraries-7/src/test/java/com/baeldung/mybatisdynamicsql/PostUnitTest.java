package com.baeldung.mybatisdynamicsql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mybatis.dynamic.sql.SqlBuilder.count;
import static org.mybatis.dynamic.sql.SqlBuilder.equalTo;
import static org.mybatis.dynamic.sql.SqlBuilder.select;

import org.junit.jupiter.api.Test;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

class PostUnitTest {

    @Test
    void whenJoiningTables_thenTheCorrectSQLIsGenerated() {
        User user = new User();
        Post post = new Post();

        SelectStatementProvider sql = select(post.allColumns())
            .from(post)
            .join(user).on(user.userId, equalTo(post.posterId))
            .build()
            .render(RenderingStrategies.SPRING_NAMED_PARAMETER);

        assertEquals("select posts.* from posts join users on users.user_id = posts.poster_id", sql.getSelectStatement());
        assertTrue(sql.getParameters().isEmpty());
    }

    @Test
    void whenDoingAFullJoin_thenTheCorrectSQLIsGenerated() {
        User user = new User();
        Post post = new Post();

        SelectStatementProvider sql = select(post.allColumns())
            .from(post)
            .fullJoin(user).on(user.userId, equalTo(post.posterId))
            .build()
            .render(RenderingStrategies.SPRING_NAMED_PARAMETER);

        assertEquals("select posts.* from posts full join users on users.user_id = posts.poster_id", sql.getSelectStatement());
        assertTrue(sql.getParameters().isEmpty());
    }
}
