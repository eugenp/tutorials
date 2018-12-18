package org.baeldung.test;

import org.baeldung.hexagonal.HexagonalApplicationForNews;
import org.baeldung.hexagonal.adapter.ArticleDeliverer;
import org.baeldung.hexagonal.adapter.implementations.SmsDeliverer;
import org.baeldung.hexagonal.news.Article;
import org.baeldung.hexagonal.users.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(classes = HexagonalApplicationForNews.class)
public class HexagonalApplicationUnitTest {

	@Test
	public void givenNews_thenDeliverItThroughSms() {

		// Let's create an Article
		Article article = new Article("Example body", "Example title");

		// Let's create an User
		User user = new User("John Doe", "54-987654321-22", "johndoe@baeldung.com");

		// Let's send it through SMS
		ArticleDeliverer deliverer = new SmsDeliverer();
		deliverer.deliverArticle(article, user);
	}
}