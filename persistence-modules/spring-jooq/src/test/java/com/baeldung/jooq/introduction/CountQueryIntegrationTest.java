package com.baeldung.jooq.introduction;

import static com.baeldung.jooq.introduction.db.public_.tables.Author.AUTHOR;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(classes = PersistenceContextIntegrationTest.class)
@Transactional(transactionManager = "transactionManager")
@RunWith(SpringJUnit4ClassRunner.class)
public class CountQueryIntegrationTest {
    
    @Autowired
    private DSLContext dsl;
    
    @Test
    public void givenValidData_whenSimpleSelect_thenSucceed() {
        int count = dsl.select().from(AUTHOR).execute();
        Assert.assertEquals(3, count);
    }
    
    @Test
    public void givenValidData_whenSelectCount_thenSucceed() {
        int count = dsl.selectCount().from(AUTHOR).fetchOne(0, int.class);
        Assert.assertEquals(3, count);
    }
    
    @Test
    public void givenValidData_whenCount_thenSucceed() {
        int count = dsl.select(DSL.count()).from(AUTHOR).fetchOne(0, int.class);
        Assert.assertEquals(3, count);
    }
    
    @Test
    public void givenValidData_whenFetchCount_thenSucceed() {
        int count = dsl.fetchCount(DSL.selectFrom(AUTHOR));
        Assert.assertEquals(3, count);
    }
}