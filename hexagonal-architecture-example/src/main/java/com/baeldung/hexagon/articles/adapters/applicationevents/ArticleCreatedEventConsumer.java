package com.baeldung.hexagon.articles.adapters.applicationevents;

import com.baeldung.hexagon.articles.domain.ports.ArticleEventBusBroadcaster;
import com.baeldung.hexagon.articles.domain.ports.SocialMediaPublisher;
import com.baeldung.hexagon.articles.domain.ports.UserNotifier;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class ArticleCreatedEventConsumer implements ApplicationListener<ArticleCreatedSpringEvent> {

    private final List<UserNotifier> notifiers;
    private final List<SocialMediaPublisher> socialMediaPublishers;
    private final ArticleEventBusBroadcaster articleEventBusBroadcaster;

    ArticleCreatedEventConsumer(final List<UserNotifier> notifiers, final List<SocialMediaPublisher> socialMediaPublishers, final ArticleEventBusBroadcaster articleEventBusBroadcaster) {
        this.notifiers = notifiers;
        this.socialMediaPublishers = socialMediaPublishers;
        this.articleEventBusBroadcaster = articleEventBusBroadcaster;
    }


    @Override
    public void onApplicationEvent(final ArticleCreatedSpringEvent event) {
        notifiers.forEach(n -> n.notifyAbout(event.article()));
        socialMediaPublishers.forEach(smp -> smp.publish(event.article()));
        articleEventBusBroadcaster.broadcast(event.articleEvent());
    }

}
