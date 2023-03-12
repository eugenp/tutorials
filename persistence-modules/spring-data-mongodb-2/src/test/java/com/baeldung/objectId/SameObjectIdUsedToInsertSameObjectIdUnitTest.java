package com.baeldung.objectId;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.assertj.core.util.Lists;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.objectId.config.MongoConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MongoConfig.class)
public class SameObjectIdUsedToInsertSameObjectIdUnitTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void setUp() {
        mongoTemplate.dropCollection(User.class);
    }

    @Test
    public void givenUserInDatabase_whenInsertingAnotherUserWithTheSameObjectId_DKEThrownAndInsertRetried() {
        //given
        String userName = "Kevin";
        User firstUser = new User(ObjectId.get(), userName);
        User secondUser = new User(ObjectId.get(), userName);

        mongoTemplate.insert(firstUser);

        //when
        try {
            mongoTemplate.insert(firstUser);
        } catch (DuplicateKeyException dke) {
            mongoTemplate.insert(secondUser);
        }

        //then
        Query query = new Query();
        query.addCriteria(Criteria.where(User.NAME_FIELD)
          .is(userName));
        List<User> users = mongoTemplate.find(query, User.class);

        assertThat(users).usingRecursiveComparison()
          .isEqualTo(Lists.newArrayList(firstUser, secondUser));
    }
}


