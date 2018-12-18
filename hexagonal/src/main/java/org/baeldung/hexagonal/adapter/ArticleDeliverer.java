package org.baeldung.hexagonal.adapter;

import org.baeldung.hexagonal.news.Article;
import org.baeldung.hexagonal.users.User;

public interface ArticleDeliverer {

	public void deliverArticle(Article article, User user);

}
