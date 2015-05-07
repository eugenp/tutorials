package org.baeldung.web.schedule;

import java.util.Date;
import java.util.List;

import org.baeldung.persistence.dao.PostRepository;
import org.baeldung.persistence.model.Post;
import org.baeldung.persistence.service.RedditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RedditScheduler {

    @Autowired
    private RedditService service;

    @Autowired
    private PostRepository postReopsitory;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Scheduled(fixedRate = 1 * 60 * 1000)
    public void schedulePosts() {
        final List<Post> posts = postReopsitory.findBySubmissionDateBeforeAndIsSent(new Date(), false);
        logger.info(posts.size() + " Posts in the queue.");
        for (final Post post : posts) {
            service.submitPost(post);
        }
    }

    @Scheduled(fixedRate = 3 * 60 * 1000)
    public void checkAndReSubmitPosts() {
        final List<Post> submitted = postReopsitory.findByRedditIDNotNullAndNoOfAttemptsGreaterThan(0);
        logger.info(submitted.size() + " Posts to check their score");
        for (final Post post : submitted) {
            service.checkAndReSubmit(post);
        }
    }

}
