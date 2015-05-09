package org.baeldung.persistence;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIn.isIn;
import static org.hamcrest.core.IsNot.not;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.baeldung.persistence.dao.PostRepository;
import org.baeldung.persistence.dao.UserRepository;
import org.baeldung.persistence.model.Post;
import org.baeldung.persistence.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestJPAConfig.class })
@Transactional
@TransactionConfiguration
// @Ignore
public class PersistenceJPATest {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private Post alreadySentPost, notSentYetOld, notSentYet;

    private User userJohn, userTom;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Before
    public void init() throws ParseException {
        userJohn = new User();
        userJohn.setUsername("John");
        userRepository.save(userJohn);

        userTom = new User();
        userTom.setUsername("Tom");
        userRepository.save(userTom);

        alreadySentPost = new Post();
        alreadySentPost.setTitle("First post title");
        alreadySentPost.setSent(true);
        alreadySentPost.setSubmissionDate(dateFormat.parse("2015-03-03 10:30"));
        alreadySentPost.setUser(userJohn);
        alreadySentPost.setSubreddit("funny");
        alreadySentPost.setUrl("www.example.com");
        postRepository.save(alreadySentPost);

        notSentYetOld = new Post();
        notSentYetOld.setTitle("Second post title");
        notSentYetOld.setSent(false);
        notSentYetOld.setSubmissionDate(dateFormat.parse("2015-03-03 11:00"));
        notSentYetOld.setUser(userTom);
        notSentYetOld.setSubreddit("funny");
        notSentYetOld.setUrl("www.example.com");
        postRepository.save(notSentYetOld);

        notSentYet = new Post();
        notSentYet.setTitle("Second post title");
        notSentYet.setSent(false);
        notSentYet.setSubmissionDate(dateFormat.parse("2015-03-03 11:30"));
        notSentYet.setUser(userJohn);
        notSentYet.setSubreddit("funny");
        notSentYet.setUrl("www.example.com");
        postRepository.save(notSentYet);
    }

    @Test
    public void whenGettingListOfSentPosts_thenCorrect() throws ParseException {
        final List<Post> results = postRepository.findBySubmissionDateBeforeAndIsSent(dateFormat.parse("2015-03-03 11:50"), true);

        assertThat(alreadySentPost, isIn(results));
        assertThat(notSentYet, not(isIn(results)));
        assertThat(notSentYetOld, not(isIn(results)));
    }

    @Test
    public void whenGettingListOfOldPosts_thenCorrect() throws ParseException {
        final List<Post> results = postRepository.findBySubmissionDateBeforeAndIsSent(dateFormat.parse("2015-03-03 11:01"), false);

        assertThat(notSentYetOld, isIn(results));
        assertThat(notSentYet, not(isIn(results)));
        assertThat(alreadySentPost, not(isIn(results)));
    }

    @Test
    public void whenGettingListOfSpecificuser_thenCorrect() throws ParseException {
        final List<Post> results = postRepository.findByUser(userTom);
        assertThat(notSentYetOld, isIn(results));
        assertThat(notSentYet, not(isIn(results)));
        assertThat(alreadySentPost, not(isIn(results)));
    }

}
