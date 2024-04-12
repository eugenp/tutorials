package com.baeldung.netflix.mantis.source;

import io.mantisrx.runtime.Context;
import io.mantisrx.runtime.source.Index;
import io.mantisrx.runtime.source.Source;
import lombok.extern.slf4j.Slf4j;
import net.andreinc.mockneat.MockNeat;
import rx.Observable;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RandomLogSource implements Source<String> {

    private MockNeat mockDataGenerator;

    @Override
    public void init(Context context, Index index) {
        mockDataGenerator = MockNeat.threadLocal();
    }

    @Override
    public Observable<Observable<String>> call(Context context, Index index) {
        return Observable.just(
          Observable
            .interval(250, TimeUnit.MILLISECONDS)
            .map(this::createRandomLogEvent));
    }

    private String createRandomLogEvent(Long tick) {
        String level = mockDataGenerator.probabilites(String.class)
          .add(0.5, "INFO")
          .add(0.3, "WARN")
          .add(0.2, "ERROR")
          .get();

        String message = mockDataGenerator.probabilites(String.class)
          .add(0.5, "login attempt")
          .add(0.5, "user created")
          .get();

        return tick + "#" + level + "#" + message;
    }

}
