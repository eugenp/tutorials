package org.baeldung.test;

import org.baeldung.hexagonal.HexagonalApplicationForNews;
import org.baeldung.hexagonal.adapter.ArticleDeliverer;
import org.baeldung.hexagonal.adapter.implementations.SmsDeliverer;
import org.baeldung.hexagonal.news.Article;
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

		// Let's create an article
		Article article = new Article();
		article.setBody("Example body");
		article.setTitle("Example title");

		// Let's send it through SMS
		ArticleDeliverer deliverer = new SmsDeliverer();
		deliverer.deliverArticle(article);

	}

}
