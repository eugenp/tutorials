package datajpa.domain.domain1;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import datajpa.repository.PostDetailsRepository;
import datajpa.repository.PostRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:exampleApplicationContext-persistence.xml"})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public class RepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostDetailsRepository postDetailsRepository;

    @Test
    public void one_to_one() {
        Post post = new Post();
        post.setName("t");
        PostDetails postDetails = new PostDetails();
        postDetails.setVisible(true);
        post.addDetails(postDetails);
        postRepository.save(post);
    }

}
