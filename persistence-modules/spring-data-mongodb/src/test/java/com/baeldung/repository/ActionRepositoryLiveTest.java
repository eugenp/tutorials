package com.baeldung.repository;

import com.baeldung.config.MongoConfig;
import com.baeldung.model.Action;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * 
 * This test requires:
 * * mongodb instance running on the environment
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MongoConfig.class)
public class ActionRepositoryLiveTest {

    @Autowired
    private MongoOperations mongoOps;

    @Autowired
    private ActionRepository actionRepository;

    @Before
    public void setup() {
        if (!mongoOps.collectionExists(Action.class)) {
            mongoOps.createCollection(Action.class);
        }
    }

    @Test
    public void givenSavedAction_TimeIsRetrievedCorrectly() {
        String id = "testId";
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);

        actionRepository.save(new Action(id, "click-action", now));
        Action savedAction = actionRepository.findById(id).get();

        Assert.assertEquals(now.withNano(0), savedAction.getTime().withNano(0));
    }

    @After
    public void tearDown() {
        mongoOps.dropCollection(Action.class);
    }
}