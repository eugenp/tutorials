package com.baeldung.gradle;

import com.google.common.collect.ImmutableList;
import io.reactivex.Observable;
import java.util.List;

/** Demonstrates a library type that returns an RxJava type. */
public class RxHelloWorld {

  /** @return an {@link Observable} that emits events "hello" and "world" before completing. */
  public static Observable<String> hello() {
    // Guava ImmutableList class is an implementation detail.
    List<String> values = ImmutableList.of("hello", "world");
    return Observable.fromIterable(values);
  }

  private RxHelloWorld() {}
}
