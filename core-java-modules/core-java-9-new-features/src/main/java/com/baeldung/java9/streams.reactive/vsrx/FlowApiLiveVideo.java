package com.baeldung.java9.streams.reactive.vsrx;

import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlowApiLiveVideo {

    private static Logger log = LoggerFactory.getLogger(FlowApiLiveVideo.class);

    static class VideoPlayer implements Flow.Subscriber<VideoFrame> {
        Flow.Subscription subscription = null;

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            subscription.request(1);
        }

        @Override
        public void onNext(VideoFrame item) {
            log.info("play #{}", item.getNumber());
            sleep(30);
            subscription.request(1);
        }

        @Override
        public void onError(Throwable throwable) {
            log.error("There is an error in video streaming:{}", throwable.getMessage());
        }

        @Override
        public void onComplete() {
            log.error("Video has ended");
        }
    }

    static class VideoStreamServer extends SubmissionPublisher<VideoFrame> {
        public VideoStreamServer() {
            super(Executors.newSingleThreadExecutor(), 1);
        }
    }

    private static void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        VideoStreamServer streamServer = new VideoStreamServer();
        streamServer.subscribe(new VideoPlayer());

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        AtomicLong frameNumber = new AtomicLong();
        executor.scheduleWithFixedDelay(() -> {
            streamServer.offer(new VideoFrame(frameNumber.getAndIncrement()), (subscriber, videoFrame) -> {
                subscriber.onError(new RuntimeException("Frame#" + videoFrame.getNumber() + " droped because of back pressure"));
                return true;
            });
        }, 0, 1, TimeUnit.MILLISECONDS);

        sleep(1000);
    }
}
