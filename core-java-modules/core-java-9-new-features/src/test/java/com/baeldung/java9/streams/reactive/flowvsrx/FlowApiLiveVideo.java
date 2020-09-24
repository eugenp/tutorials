package com.baeldung.java9.streams.reactive.flowvsrx;

import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class FlowApiLiveVideo {

    static class VideoPlayer implements Flow.Subscriber<VideoFrame> {
        Flow.Subscription subscription = null;
        private long consumerDelay = 30;

        public VideoPlayer(long consumerDelay) {
            this.consumerDelay = consumerDelay;
        }

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            subscription.request(1);
        }

        @Override
        public void onNext(VideoFrame item) {
            try {
                Thread.sleep(consumerDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            subscription.request(1);
        }

        @Override
        public void onError(Throwable throwable) {
        }

        @Override
        public void onComplete() {
        }
    }

    static class VideoStreamServer extends SubmissionPublisher<VideoFrame> {
        ScheduledExecutorService executor = null;

        public VideoStreamServer(int bufferSize) {
            super(Executors.newSingleThreadExecutor(), bufferSize);
            executor = Executors.newScheduledThreadPool(1);
        }

        void startStreaming(long produceDelay, Runnable onDrop) {
            AtomicLong frameNumber = new AtomicLong();
            executor.scheduleWithFixedDelay(() -> {
                offer(new VideoFrame(frameNumber.getAndIncrement()), (subscriber, videoFrame) -> {
                    subscriber.onError(new RuntimeException("Frame#" + videoFrame.getNumber() + " dropped because of back pressure"));
                    onDrop.run();
                    return true;
                });
            }, 0, produceDelay, TimeUnit.MILLISECONDS);
        }
    }

    public static void streamLiveVideo(long produceDelay, long consumeDelay, int bufferSize, Runnable onError){
        FlowApiLiveVideo.VideoStreamServer streamServer = new FlowApiLiveVideo.VideoStreamServer(bufferSize);
        streamServer.subscribe(new FlowApiLiveVideo.VideoPlayer(consumeDelay));
        streamServer.startStreaming(produceDelay, onError);
    }

}
