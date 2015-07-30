package org.baeldung.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.baeldung.config.MongoConfig;
import org.baeldung.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MongoConfig.class)
public class UserRepositoryIntegrationTest {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoOperations mongoOps;

    @Before
    public void testSetup() {
        mongoOps.createCollection(User.class);
    }

    @After
    public void tearDown() {
        mongoOps.dropCollection(User.class);
    }

    @Test
    public void testInsert() {
        User user = new User();
        user.setName("Jon");
        userRepository.insert(user);

        assertThat(mongoOps.findOne(Query.query(Criteria.where("name").is("Jon")), User.class).getName(), is("Jon"));
    }

    @Test
    public void testSave() {
        User user = new User();
        user.setName("Jack");
        mongoOps.insert(user);

        user = mongoOps.findOne(Query.query(Criteria.where("name").is("Jack")), User.class);
        String id = user.getId();

        user.setName("Jim");
        userRepository.save(user);

        assertThat(mongoOps.findOne(Query.query(Criteria.where("id").is(id)), User.class).getName(), is("Jim"));
    }

    @Test
    public void testDelete() {
        User user = new User();
        user.setName("Benn");
        mongoOps.insert(user);

        userRepository.delete(user);

        assertThat(mongoOps.find(Query.query(Criteria.where("name").is("Benn")), User.class).size(), is(0));
    }
    
    @Test
    public void testFindOne() {
        User user = new User();
        user.setName("Chris");
        mongoOps.insert(user);

        user = mongoOps.findOne(Query.query(Criteria.where("name").is("Chris")), User.class);
        User foundUser = userRepository.findOne(user.getId());

        assertThat(user.getName(), is(foundUser.getName()));
    }
    
    @Test
    public void testExists() {
        User user = new User();
        user.setName("Harris");
        mongoOps.insert(user);

        user = mongoOps.findOne(Query.query(Criteria.where("name").is("Harris")), User.class);
        boolean isExists = userRepository.exists(user.getId());

        assertThat(isExists, is(true));
    }
    
    @Test
    public void testFindAllWithSort() {
        User user = new User();
        user.setName("Brendan");
        mongoOps.insert(user);
        
        user = new User();
        user.setName("Adam");
        mongoOps.insert(user);

        List<User> users = userRepository.findAll(new Sort(Sort.Direction.ASC,"name"));

        assertThat(users.size(), is(2));
        assertThat(users.get(0).getName(), is("Adam"));
        assertThat(users.get(1).getName(), is("Brendan"));
    }
    
    @Test
    public void testFindAllWithPageable() {
        User user = new User();
        user.setName("Brendan");
        mongoOps.insert(user);
        
        user = new User();
        user.setName("Adam");
        mongoOps.insert(user);

        Pageable pageableRequest = new PageRequest(0, 2);
        
        Page<User> users = userRepository.findAll(pageableRequest);

        assertThat(users.getTotalPages(), is(1));
        assertThat(users.iterator().next().getName(), is("Brendan"));
    }
}
