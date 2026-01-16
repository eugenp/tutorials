package com.baeldung.mybatisdynamicsql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mybatis.dynamic.sql.SqlBuilder.countFrom;
import static org.mybatis.dynamic.sql.SqlBuilder.deleteFrom;
import static org.mybatis.dynamic.sql.SqlBuilder.insertInto;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isGreaterThan;
import static org.mybatis.dynamic.sql.SqlBuilder.select;
import static org.mybatis.dynamic.sql.SqlBuilder.update;

import org.junit.jupiter.api.Test;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.GeneralInsertStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;

public class UserUnitTest {
    @Test
    void whenCountingATable_thenTheCorrectSQLIsGenerated() {
        User user = new User();
        SelectStatementProvider sql = countFrom(user)
            .build()
            .render(RenderingStrategies.SPRING_NAMED_PARAMETER);

        assertEquals("select count(*) from users", sql.getSelectStatement());
        assertTrue(sql.getParameters().isEmpty());
    }

    @Test
    void whenSelectingAllRows_thenTheCorrectSQLIsGenerated() {
        User user = new User();
        SelectStatementProvider sql = select(user.allColumns())
            .from(user)
            .build()
            .render(RenderingStrategies.SPRING_NAMED_PARAMETER);

        assertEquals("select * from users", sql.getSelectStatement());
        assertTrue(sql.getParameters().isEmpty());
    }

    @Test
    void whenSelectingSomeRows_thenTheCorrectSQLIsGenerated() {
        User user = new User();
        SelectStatementProvider sql = select(user.allColumns())
            .from(user)
            .where(user.userName, isEqualTo("baeldung"))
            .build()
            .render(RenderingStrategies.SPRING_NAMED_PARAMETER);

        assertEquals("select * from users where username = :p1", sql.getSelectStatement());
        assertEquals(1, sql.getParameters().size());
        assertEquals("baeldung", sql.getParameters().get("p1"));
    }

    @Test
    void whenUsingComplexFilters_thenTheCorrectSQLIsGenerated() {
        User user = new User();
        SelectStatementProvider sql = select(user.allColumns())
            .from(user)
            .where(user.userName, isEqualTo("baeldung"))
            .or(user.userId, isGreaterThan(5))
            .build()
            .render(RenderingStrategies.SPRING_NAMED_PARAMETER);

        assertEquals("select * from users where username = :p1 or user_id > :p2", sql.getSelectStatement());
        assertEquals(2, sql.getParameters().size());
        assertEquals("baeldung", sql.getParameters().get("p1"));
        assertEquals(5, sql.getParameters().get("p2"));
    }

    @Test
    void whenDeletingRows_thenTheCorrectSQLIsGenerated() {
        User user = new User();
        DeleteStatementProvider sql = deleteFrom(user)
            .where(user.userId, isEqualTo(1))
            .build()
            .render(RenderingStrategies.SPRING_NAMED_PARAMETER);

        assertEquals("delete from users where user_id = :p1", sql.getDeleteStatement());
        assertEquals(1, sql.getParameters().size());
        assertEquals(1, sql.getParameters().get("p1"));
    }

    @Test
    void whenUpdatingRows_thenTheCorrectSQLIsGenerated() {
        User user = new User();
        UpdateStatementProvider sql = update(user)
            .set(user.userName).equalTo("Baeldung")
            .where(user.userId, isEqualTo(1))
            .build()
            .render(RenderingStrategies.SPRING_NAMED_PARAMETER);

        assertEquals("update users set username = :p1 where user_id = :p2", sql.getUpdateStatement());
        assertEquals(2, sql.getParameters().size());
        assertEquals("Baeldung", sql.getParameters().get("p1"));
        assertEquals(1, sql.getParameters().get("p2"));
    }

    @Test
    void whenInsertingRows_thenTheCorrectSQLIsGenerated() {
        User user = new User();
        GeneralInsertStatementProvider sql = insertInto(user)
            .set(user.userId).toValue(2)
            .set(user.userName).toValue("Baeldung")
            .build()
            .render(RenderingStrategies.SPRING_NAMED_PARAMETER);

        assertEquals("insert into users (user_id, username) values (:p1, :p2)", sql.getInsertStatement());
        assertEquals(2, sql.getParameters().size());
        assertEquals(2, sql.getParameters().get("p1"));
        assertEquals("Baeldung", sql.getParameters().get("p2"));
    }
}
