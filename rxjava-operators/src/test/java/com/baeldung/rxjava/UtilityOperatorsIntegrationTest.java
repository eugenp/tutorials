package com.baeldung.rxjava;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import rx.Observable;
import rx.Observer;
import rx.exceptions.OnErrorNotImplementedException;
import rx.schedulers.Schedulers;
import rx.schedulers.Timestamped;

import java.util.concurrent.TimeUnit;

import static com.jayway.awaitility.Awaitility.await;
import static org.junit.Assert.assertTrue;

public class UtilityOperatorsIntegrationTest {

    private int emittedTotal = 0;
    private int receivedTotal = 0;
    private String result = "";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void givenObservable_whenObserveOnAfterOnNext_thenEmitsEventsOnComputeScheduler() throws InterruptedException {

        Observable.range(1, 5)
          .map(i -> i * 100)
          .doOnNext(i -> {
              emittedTotal += i;
              System.out.println("Emitting " + i
                + " on thread " + Thread.currentThread().getName());
          })
          .observeOn(Schedulers.computation())
          .map(i -> i * 10)
          .subscribe(i -> {
              receivedTotal += i;
              System.out.println("Received " + i + " on thread "
                + Thread.currentThread().getName());
          });

        await().until(() -> {
              assertTrue(emittedTotal == 1500);
              assertTrue(receivedTotal == 15000);
          }
        );
    }

    @Test
    public void givenObservable_whenObserveOnBeforeOnNext_thenEmitsEventsOnComputeScheduler() throws InterruptedException {

        Observable.range(1, 5)
          .map(i -> i * 100)
          .observeOn(Schedulers.computation())
          .doOnNext(i -> {
              emittedTotal += i;
              System.out.println("Emitting " + i
                + " on thread " + Thread.currentThread().getName());
          })
          .map(i -> i * 10)
          .subscribe(i -> {
              receivedTotal += i;
              System.out.println("Received " + i + " on thread "
                + Thread.currentThread().getName());
          });

        await().until(() -> {
            assertTrue(emittedTotal == 1500);
            assertTrue(receivedTotal == 15000);
        });
    }

    @Test
    public void givenObservable_whenSubscribeOn_thenEmitsEventsOnComputeScheduler() throws InterruptedException {

        Observable.range(1, 5)
          .map(i -> i * 100)
          .doOnNext(i -> {
              emittedTotal += i;
              System.out.println("Emitting " + i
                + " on thread " + Thread.currentThread().getName());
          })
          .subscribeOn(Schedulers.computation())
          .map(i -> i * 10)
          .subscribe(i -> {
              receivedTotal += i;
              System.out.println("Received " + i + " on thread "
                + Thread.currentThread().getName());
          });

        await().until(() -> {
            assertTrue(emittedTotal == 1500);
            assertTrue(receivedTotal == 15000);
        });
    }

    @Test
    public void givenObservableWithOneEvent_whenSingle_thenEmitEvent() {

        Observable.range(1, 1)
          .single()
          .subscribe(i -> receivedTotal += i);
        assertTrue(receivedTotal == 1);
    }

    @Test
    public void givenObservableWithNoEvents_whenSingle_thenThrowException() {

        Observable.range(1, 3)
          .single()
          .onErrorReturn(e -> receivedTotal += 10)
          .subscribe();
        assertTrue(receivedTotal == 10);
    }

    @Test
    public void givenObservableWihNoEvents_whenSingleOrDefault_thenDefaultMessage() {

        Observable.empty()
          .singleOrDefault("Default")
          .subscribe(i -> result += i);
        assertTrue(result.equals("Default"));
    }

    @Test
    public void givenObservableWithManyEvents_whenSingleOrDefault_thenThrowException() {

        Observable.range(1, 3)
          .singleOrDefault(5)
          .onErrorReturn(e -> receivedTotal += 10)
          .subscribe();
        assertTrue(receivedTotal == 10);
    }

    @Test
    public void givenObservable_whenDoOnNextAndDoOnCompleted_thenSumAllEventsAndShowMessage() {

        Observable.range(1, 10)
          .doOnNext(r -> receivedTotal += r)
          .doOnCompleted(() -> result = "Completed")
          .subscribe();
        assertTrue(receivedTotal == 55);
        assertTrue(result.equals("Completed"));
    }

    @Test
    public void givenObservable_whenDoOnEachAndDoOnSubscribe_thenSumAllValuesAndShowMessage() {

        Observable.range(1, 10)
          .doOnEach(new Observer<Integer>() {
              @Override
              public void onCompleted() {
                  System.out.println("Complete");
              }

              @Override
              public void onError(Throwable e) {
                  e.printStackTrace();
              }

              @Override
              public void onNext(Integer value) {
                  receivedTotal += value;
              }
          })
          .doOnSubscribe(() -> result = "Subscribed")
          .subscribe();
        assertTrue(receivedTotal == 55);
        assertTrue(result.equals("Subscribed"));
    }

    @Test
    public void givenObservable_whenDoOnErrorDoOnTerminateAndDoAfterTerminate_thenShowErrorTerminateAndAfterTerminateMessages() {

        thrown.expect(OnErrorNotImplementedException.class);
        Observable.empty()
          .single()
          .doOnError(throwable -> {
              throw new RuntimeException("error");
          })
          .doOnTerminate(() -> result += "doOnTerminate")
          .doAfterTerminate(() -> result += "_doAfterTerminate")
          .subscribe();
        assertTrue(result.equals("doOnTerminate_doAfterTerminate"));
    }

    @Test
    public void givenObservable_whenTimestamp_thenEventsShouldAppearTimestamped() {

        Observable.range(1, 10)
          .timestamp()
          .map(o -> result = o.getClass().toString())
          .last()
          .subscribe();
        assertTrue(result.equals("class rx.schedulers.Timestamped"));
    }

    @Test
    public void givenObservables_whenDelay_thenEventsStartAppearAfterATime() throws InterruptedException {

        Observable<Timestamped<Long>> source = Observable.interval(1, TimeUnit.SECONDS)
          .take(5)
          .timestamp();

        Observable<Timestamped<Long>> delay = source.delaySubscription(2, TimeUnit.SECONDS);

        source.<Long>subscribe(
          value -> System.out.println("source :" + value),
          t -> System.out.println("source error"),
          () -> System.out.println("source completed"));

        delay.subscribe(
          value -> System.out.println("delay : " + value),
          t -> System.out.println("delay error"),
          () -> System.out.println("delay completed"));
        //Thread.sleep(8000);
    }

    @Test
    public void givenObservable_whenRepeat_thenSumNumbersThreeTimes() {

        Observable.range(1, 3)
          .repeat(3)
          .subscribe(i -> receivedTotal += i);
        assertTrue(receivedTotal == 18);
    }

    @Test
    public void givenObservable_whenUsing_thenReturnCreatedResource() {

        Observable<Character> values = Observable.using(
          () -> "resource",
          r -> Observable.create(o -> {
              for (Character c : r.toCharArray()) {
                  o.onNext(c);
              }
              o.onCompleted();
          }),
          r -> System.out.println("Disposed: " + r)
        );
        values.subscribe(
          v -> result += v,
          e -> result += e
        );
        assertTrue(result.equals("resource"));
    }

    @Test
    public void givenObservableCached_whenSubscribesWith2Actions_thenEmitsCachedValues() {

        Observable<Integer> source =
          Observable.<Integer>create(subscriber -> {
                System.out.println("Create");
                subscriber.onNext(receivedTotal += 5);
                subscriber.onCompleted();
            }
          ).cache();
        source.subscribe(i -> {
            System.out.println("element 1");
            receivedTotal += 1;
        });
        source.subscribe(i -> {
            System.out.println("element 2");
            receivedTotal += 2;
        });
        assertTrue(receivedTotal == 8);
    }
}
