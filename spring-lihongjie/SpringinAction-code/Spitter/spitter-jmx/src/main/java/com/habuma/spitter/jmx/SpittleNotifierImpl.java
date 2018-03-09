package com.habuma.spitter.jmx;

import javax.management.Notification;
import org.springframework.jmx.export.annotation.ManagedNotification;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;
import org.springframework.stereotype.Component;

@Component
@ManagedResource("spitter:name=SpitterNotifier")
@ManagedNotification(
        notificationTypes="SpittleNotifier.OneMillionSpittles",
        name="TODO")
public class SpittleNotifierImpl 
    implements NotificationPublisherAware, SpittleNotifier { //<co id="co_npAware"/>

  private NotificationPublisher notificationPublisher;

  public void setNotificationPublisher( //<co id="co_injectPublisher"/>
          NotificationPublisher notificationPublisher) {
    this.notificationPublisher = notificationPublisher;
  }

  public void millionthSpittlePosted() {
    notificationPublisher.sendNotification( //<co id="co_sendNotification"/>
            new Notification(
                    "SpittleNotifier.OneMillionSpittles", this, 0));
  }
  
//  @ManagedNotification
  Notification myNotification;

}
