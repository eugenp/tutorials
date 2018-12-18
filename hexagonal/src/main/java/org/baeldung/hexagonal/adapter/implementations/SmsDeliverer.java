package org.baeldung.hexagonal.adapter.implementations;

import org.baeldung.hexagonal.adapter.ArticleDeliverer;
import org.baeldung.hexagonal.news.Article;
import org.baeldung.hexagonal.users.User;

public class SmsDeliverer implements ArticleDeliverer {

	@Override
	public void deliverArticle(Article article, User user) {
		// We received an article from our app (the hexagon)
		// Let's use this for sending a SMS with the last news
		Sms sms = new Sms(article.getTitle(), article.getBody());
		sendSms(sms, user.getPhoneNumber());
	}

	/**
	 * Will send a SMS to an User
	 * 
	 * @param sms
	 */
	private void sendSms(Sms sms, String destinationNumber) {
		// TODO: The real implementation depends on the technologies used
	}

	private class Sms {
		String subject;
		String body;

		public Sms(String subject, String body) {
			super();
			this.subject = subject;
			this.body = body;
		}
	}

}
