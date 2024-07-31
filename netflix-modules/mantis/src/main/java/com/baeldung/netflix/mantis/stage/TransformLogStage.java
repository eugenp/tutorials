package com.baeldung.netflix.mantis.stage;

import com.baeldung.netflix.mantis.model.LogEvent;
import io.mantisrx.runtime.Context;
import io.mantisrx.runtime.ScalarToScalar;
import io.mantisrx.runtime.codec.JacksonCodecs;
import io.mantisrx.runtime.computation.ScalarComputation;
import rx.Observable;

public class TransformLogStage implements ScalarComputation<String, LogEvent> {

    @Override
    public Observable<LogEvent> call(Context context, Observable<String> logEntry) {
        return logEntry
          .map(log -> log.split("#"))
          .filter(parts -> parts.length == 3)
          .map(LogEvent::new);
    }

    public static ScalarToScalar.Config<String, LogEvent> stageConfig() {
        return new ScalarToScalar.Config<String, LogEvent>()
          .codec(JacksonCodecs.pojo(LogEvent.class));
    }
}
