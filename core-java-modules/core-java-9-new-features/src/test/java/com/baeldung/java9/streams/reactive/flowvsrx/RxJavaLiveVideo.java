package com.baeldung.java9.streams.reactive.flowvsrx;

import io.reactivex.rxjava3.core.BackpressureOverflowStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class RxJavaLiveVideo {

    public static Disposable streamLiveVideo(long produceDelay, long consumeDelay, int bufferSize, Runnable onError) {
        return Flowable
          .fromStream(Stream.iterate(new VideoFrame(0), videoFrame -> {
              sleep(produceDelay);
              return new VideoFrame(videoFrame.getNumber() + 1);
          }))
          .subscribeOn(Schedulers.from(Executors.newSingleThreadScheduledExecutor()), true)
          .onBackpressureBuffer(bufferSize, null, BackpressureOverflowStrategy.ERROR)
          .observeOn(Schedulers.from(Executors.newSingleThreadExecutor()))
          .subscribe(item -> {
              sleep(consumeDelay);
          }, throwable -> {
              onError.run();
          });
    }

    private static void sleep(long i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
