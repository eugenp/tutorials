package com.baeldung.jooq.introduction;

import static com.baeldung.jooq.introduction.db.public_.tables.Author.AUTHOR;


import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.Result;
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
        int count = dsl.selectCount().from(AUTHOR)
          .where(AUTHOR.FIRST_NAME.equalIgnoreCase("Bryan"))
          .fetchOne(0, int.class);
        Assert.assertEquals(1, count);
    }
    
    @Test
    public void givenValidData_whenCount_thenSucceed() {
        int count = dsl.select(DSL.count())
          .from(AUTHOR).fetchOne(0, int.class);
        Assert.assertEquals(3, count);
    }
    
    @Test
    public void givenValidData_whenFetchCount_thenSucceed() {
        int count = dsl.fetchCount(DSL.selectFrom(AUTHOR)
          .where(AUTHOR.FIRST_NAME.equalIgnoreCase("Bryan")));
        Assert.assertEquals(1, count);
    }
    
    @Test
    public void givenValidData_whenFetchCountWithoutCondition_thenSucceed() {
        int count = dsl.fetchCount(DSL.selectFrom(AUTHOR));
        Assert.assertEquals(3, count);
    }
    
    @Test
    public void givenValidData_whenFetchCountWithSingleCondition_thenSucceed() {
        int count = dsl.fetchCount(AUTHOR, AUTHOR.FIRST_NAME.equalIgnoreCase("Bryan"));
        Assert.assertEquals(1, count);
    }
    
    @Test
    public void givenValidData_whenFetchCountWithMultipleConditions_thenSucceed() {
        int count = dsl.fetchCount(AUTHOR, AUTHOR.FIRST_NAME.equalIgnoreCase("Bryan").and(AUTHOR.ID.notEqual(1)));
        Assert.assertEquals(1, count);
    }
    
    @Test
    public void givenValidData_whenFetchCountWithConditionsInVarargs_thenSucceed() {
        Condition firstCond = AUTHOR.FIRST_NAME.equalIgnoreCase("Bryan");
        Condition secondCond = AUTHOR.ID.notEqual(1);
        int count = dsl.fetchCount(AUTHOR, firstCond, secondCond);
        Assert.assertEquals(1, count);
    }
    
    @Test
    public void givenValidData_whenCountwithGroupBy_thenSucceed() {
        final Result<Record2<String, Integer>> result = dsl.select(AUTHOR.FIRST_NAME, DSL.count())
          .from(AUTHOR).groupBy(AUTHOR.FIRST_NAME).fetch();
        Assert.assertEquals(3, result.size());
        Assert.assertEquals(result.get(0).get(0), "Bert");
        Assert.assertEquals(result.get(0).get(1), 1);
    }
}