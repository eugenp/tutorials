package com.baeldung.jmxterm;

import javax.management.ListenerNotFoundException;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcaster;
import javax.management.NotificationBroadcasterSupport;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;

public abstract class BroadcastingGuessGame implements NotificationBroadcaster, GuessGameMBean {
    private final NotificationBroadcasterSupport broadcaster =
        new NotificationBroadcasterSupport();

    private long notificationSequence = 0;

    private final MBeanNotificationInfo[] notificationInfo;

    protected BroadcastingGuessGame() {
        this.notificationInfo = new MBeanNotificationInfo[]{
            new MBeanNotificationInfo(new String[]{"game"}, Notification.class.getName(), "Game notification")
        };
    }

    protected void notifyAboutWinner(Player winner) {
        String message = "Winner is " + winner.getName() + " with score " + winner.getScore();
        Notification notification = new Notification("game.winner", this, notificationSequence++, message);
        broadcaster.sendNotification(notification);
    }

    public void addNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback) {
        broadcaster.addNotificationListener(listener, filter, handback);
    }

    public void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException {
        broadcaster.removeNotificationListener(listener);
    }

    public MBeanNotificationInfo[] getNotificationInfo() {
        return notificationInfo;
    }
}
