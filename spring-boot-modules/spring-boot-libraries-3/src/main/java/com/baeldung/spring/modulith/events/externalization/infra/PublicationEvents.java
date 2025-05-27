package com.baeldung.spring.modulith.events.externalization.infra;

import com.baeldung.spring.modulith.events.externalization.ArticlePublishedEvent;
import org.springframework.modulith.events.CompletedEventPublications;
import org.springframework.modulith.events.IncompleteEventPublications;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
class PublicationEvents {
	private final IncompleteEventPublications incompleteEvent;
	private final CompletedEventPublications completeEvents;

	public PublicationEvents(IncompleteEventPublications incompleteEvent, CompletedEventPublications completeEvents) {
		this.incompleteEvent = incompleteEvent;
		this.completeEvents = completeEvents;
	}

	public void resubmitUnpublishedEvents() {
		incompleteEvent.resubmitIncompletePublicationsOlderThan(Duration.ofSeconds(60));

		// or
		incompleteEvent.resubmitIncompletePublications(it ->
		  it.getPublicationDate().isBefore(Instant.now().minusSeconds(60))
		    && it.getEvent() instanceof ArticlePublishedEvent);
	}

	public void clearPublishedEvents() {
		completeEvents.deletePublicationsOlderThan(Duration.ofSeconds(60));

		// or
		completeEvents.deletePublications(it ->
		  it.getPublicationDate().isBefore(Instant.now().minusSeconds(60))
		    && it.getEvent() instanceof ArticlePublishedEvent);
	}
}
