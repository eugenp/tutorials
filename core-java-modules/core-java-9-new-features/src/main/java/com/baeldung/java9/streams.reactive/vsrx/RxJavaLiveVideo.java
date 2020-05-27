package com.baeldung.java9.streams.reactive.vsrx;

import io.reactivex.rxjava3.core.BackpressureOverflowStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.concurrent.Executors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RxJavaLiveVideo {

    private static Logger log = LoggerFactory.getLogger(RxJavaLiveVideo.class);

    public static void main(String[] args) {
        Flowable
          .fromStream(Stream.iterate(new VideoFrame(0), videoFrame -> {
              sleep(1);
              return new VideoFrame(videoFrame.getNumber() + 1);
          }))
          .onBackpressureBuffer(10, null, BackpressureOverflowStrategy.ERROR)
          .subscribeOn(Schedulers.from(Executors.newSingleThreadScheduledExecutor()), true)
          .observeOn(Schedulers.from(Executors.newSingleThreadExecutor()))
          .doOnError(Throwable::printStackTrace)
          .subscribe(item -> {
              log.info("play #" + item.getNumber());
              sleep(30);
          });
    }

    private static void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
