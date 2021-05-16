package com.baeldung.netflix.mantis.sink;

import com.baeldung.netflix.mantis.model.LogEvent;
import io.mantisrx.runtime.Context;
import io.mantisrx.runtime.PortRequest;
import io.mantisrx.runtime.sink.SelfDocumentingSink;
import io.mantisrx.runtime.sink.ServerSentEventsSink;
import io.mantisrx.runtime.sink.Sink;
import io.mantisrx.runtime.sink.predicate.Predicate;
import rx.Observable;

public class LogSink implements Sink<LogEvent> {

    @Override
    public void call(Context context, PortRequest portRequest, Observable<LogEvent> logEventObservable) {

        SelfDocumentingSink<LogEvent> sink = new ServerSentEventsSink.Builder<LogEvent>()
          .withEncoder(LogEvent::toJsonString)
          .withPredicate(filterByLogMessage())
          .build();

        logEventObservable.subscribe();

        sink.call(context, portRequest, logEventObservable);
    }

    private Predicate<LogEvent> filterByLogMessage() {
        return new Predicate<>("filter by message",
          parameters -> {
            if (parameters != null && parameters.containsKey("filter")) {
                return logEvent -> logEvent.getMessage().contains(parameters.get("filter").get(0));
            }
            return logEvent -> true;
        });
    }

}
