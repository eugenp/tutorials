package org.baeldung.hexagonal.adapter.implementations;

import org.baeldung.hexagonal.adapter.ArticleDeliverer;
import org.baeldung.hexagonal.news.Article;

public class SmsDeliverer implements ArticleDeliverer {

	@Override
	public void deliverArticle(Article article) {
		// We received an article from our app (the hexagon)
		// Let's use this for sending a SMS with the last news
		Sms sms = new Sms(article.getTitle(), article.getBody());
		sendSms(sms);
	}

	/**
	 * Will send a SMS to whoever is listed for this way of receive news
	 * 
	 * @param sms
	 */
	private void sendSms(Sms sms) {
		// TODO: The real implementation depends on the techonologies used
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
