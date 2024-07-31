package com.baeldung.netflix.mantis.stage;

import com.baeldung.netflix.mantis.model.LogEvent;
import io.mantisrx.common.MantisGroup;
import io.mantisrx.runtime.Context;
import io.mantisrx.runtime.ScalarToGroup;
import io.mantisrx.runtime.codec.JacksonCodecs;
import io.mantisrx.runtime.computation.ToGroupComputation;
import rx.Observable;

public class GroupLogStage implements ToGroupComputation<LogEvent, String, LogEvent> {

    @Override
    public Observable<MantisGroup<String, LogEvent>> call(Context context, Observable<LogEvent> logEvent) {
        return logEvent.map(log -> new MantisGroup<>(log.getLevel(), log));
    }

    public static ScalarToGroup.Config<LogEvent, String, LogEvent> config(){
        return new ScalarToGroup.Config<LogEvent, String, LogEvent>()
          .description("Group event data by level")
          .codec(JacksonCodecs.pojo(LogEvent.class))
          .concurrentInput();
    }
    
}
