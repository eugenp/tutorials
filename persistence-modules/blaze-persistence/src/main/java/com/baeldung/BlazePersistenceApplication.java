package com.baeldung;

import com.baeldung.model.Person;
import com.baeldung.model.Post;
import com.baeldung.repository.PersonRepository;
import com.baeldung.repository.PostRepository;
import com.baeldung.repository.PostViewRepository;
import com.baeldung.view.PostWithAuthorView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlazePersistenceApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(BlazePersistenceApplication.class);

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostViewRepository postViewRepository;

    public static void main(String[] args) {
        SpringApplication.run(BlazePersistenceApplication.class, args);
    }

    @Override
    public void run(String... args) {

        logger.info("All Posts:");
        Iterable<Post> posts = postRepository.findAll();
        posts.forEach(p -> logger.info(String.valueOf(p)));

        logger.info("Posts with title 'Spring' or author 'Peter':");
        Iterable<PostWithAuthorView> postsFiltered = postRepository.findBy("Spring", "Peter");
        postsFiltered.forEach(p -> logger.info(String.valueOf(p)));

        logger.info("Find all post with author view:");
        Iterable<PostWithAuthorView> postsView = postViewRepository.findAll();
        postsView.forEach(p -> logger.info(String.valueOf(p)));

        logger.info("Person with at least two posts:");
        Iterable<Person> personIterable = personRepository.find();
        personIterable.forEach(p -> logger.info(String.valueOf(p)));

        logger.info("All Persons:");
        Iterable<Person> personIterableAll = personRepository.findAll();
        personIterableAll.forEach(p -> logger.info(String.valueOf(p)));
    }
}