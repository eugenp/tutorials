package footmark.springdata.jpa.domain.domain1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-demo-cfg.xml");
        PostRepository postRepository = ctx.getBean("postRepository", PostRepository.class);

        Post post = new Post();
        post.setName("Hibernate Master Class");

        PostDetails details = new PostDetails();
        post.addDetails(details);
        postRepository.save(post);

    }
}

