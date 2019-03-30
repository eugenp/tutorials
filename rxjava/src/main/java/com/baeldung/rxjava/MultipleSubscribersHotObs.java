package com.baeldung.rxjava;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import rx.Observable;
import rx.Subscription;
import rx.observables.ConnectableObservable;
import rx.subscriptions.Subscriptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultipleSubscribersHotObs {
    private static final Logger LOGGER = LoggerFactory.getLogger(MultipleSubscribersHotObs.class);
    private static JFrame frame;

    public static void main(String[] args) throws InterruptedException, InvocationTargetException {

        javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

        defaultBehaviour();
        // subscribeBeforeConnect();
        // connectBeforeSubscribe();
        // autoConnectAndSubscribe();
        // refCountAndSubscribe();
    }

    private static void createAndShowGUI() {
        frame = new JFrame("Hot Observable Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.GRAY);
        frame.setPreferredSize(new Dimension(500, 500));
        frame.pack();
        frame.setVisible(true);
    }

    public static void defaultBehaviour() throws InterruptedException {
        Observable obs = getObservable();

        LOGGER.info("subscribing #1");
        Subscription subscription1 = obs.subscribe((i) -> LOGGER.info("subscriber#1 is printing x-coordinate " + i));

        Thread.sleep(1000);
        LOGGER.info("subscribing #2");
        Subscription subscription2 = obs.subscribe((i) -> LOGGER.info("subscriber#2 is printing x-coordinate " + i));
        Thread.sleep(1000);
        LOGGER.info("unsubscribe#1");
        subscription1.unsubscribe();
        Thread.sleep(1000);
        LOGGER.info("unsubscribe#2");
        subscription2.unsubscribe();
    }

    public static void subscribeBeforeConnect() throws InterruptedException {

        ConnectableObservable obs = getObservable().publish();

        LOGGER.info("subscribing #1");
        Subscription subscription1 = obs.subscribe((i) -> LOGGER.info("subscriber#1 is printing x-coordinate " + i));
        Thread.sleep(1000);
        LOGGER.info("subscribing #2");
        Subscription subscription2 = obs.subscribe((i) -> LOGGER.info("subscriber#2 is printing x-coordinate " + i));
        Thread.sleep(1000);
        LOGGER.info("connecting:");
        Subscription s = obs.connect();
        Thread.sleep(1000);
        LOGGER.info("unsubscribe connected");
        s.unsubscribe();

    }

    public static void connectBeforeSubscribe() throws InterruptedException {

        ConnectableObservable obs = getObservable().doOnNext(x -> LOGGER.info("saving " + x)).publish();
        LOGGER.info("connecting:");
        Subscription s = obs.connect();
        Thread.sleep(1000);
        LOGGER.info("subscribing #1");
        obs.subscribe((i) -> LOGGER.info("subscriber#1 is printing x-coordinate " + i));
        Thread.sleep(1000);
        LOGGER.info("subscribing #2");
        obs.subscribe((i) -> LOGGER.info("subscriber#2 is printing x-coordinate " + i));
        Thread.sleep(1000);
        s.unsubscribe();

    }

    public static void autoConnectAndSubscribe() throws InterruptedException {
        Observable obs = getObservable().doOnNext(x -> LOGGER.info("saving " + x)).publish().autoConnect();

        LOGGER.info("autoconnect()");
        Thread.sleep(1000);
        LOGGER.info("subscribing #1");
        Subscription s1 = obs.subscribe((i) -> LOGGER.info("subscriber#1 is printing x-coordinate " + i));
        Thread.sleep(1000);
        LOGGER.info("subscribing #2");
        Subscription s2 = obs.subscribe((i) -> LOGGER.info("subscriber#2 is printing x-coordinate " + i));

        Thread.sleep(1000);
        LOGGER.info("unsubscribe 1");
        s1.unsubscribe();
        Thread.sleep(1000);
        LOGGER.info("unsubscribe 2");
        s2.unsubscribe();
    }

    public static void refCountAndSubscribe() throws InterruptedException {
        Observable obs = getObservable().doOnNext(x -> LOGGER.info("saving " + x)).publish().refCount();

        LOGGER.info("refcount()");
        Thread.sleep(1000);
        LOGGER.info("subscribing #1");
        Subscription subscription1 = obs.subscribe((i) -> LOGGER.info("subscriber#1 is printing x-coordinate " + i));
        Thread.sleep(1000);
        LOGGER.info("subscribing #2");
        Subscription subscription2 = obs.subscribe((i) -> LOGGER.info("subscriber#2 is printing x-coordinate " + i));

        Thread.sleep(1000);
        LOGGER.info("unsubscribe#1");
        subscription1.unsubscribe();
        Thread.sleep(1000);
        LOGGER.info("unsubscribe#2");
        subscription2.unsubscribe();

    }

    private static Observable getObservable() {
        return Observable.create(subscriber -> {
            frame.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    subscriber.onNext(e.getX());
                }
            });
            subscriber.add(Subscriptions.create(() -> {
                LOGGER.info("Clear resources");
                for (MouseListener listener : frame.getListeners(MouseListener.class)) {
                    frame.removeMouseListener(listener);
                }
            }));
        });
    }
}
