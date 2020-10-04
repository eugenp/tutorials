package com.baeldung.netflix.mantis.job;

import com.baeldung.netflix.mantis.model.LogEvent;
import com.baeldung.netflix.mantis.sink.LogSink;
import com.baeldung.netflix.mantis.source.RandomLogSource;
import com.baeldung.netflix.mantis.stage.TransformLogStage;
import io.mantisrx.runtime.Job;
import io.mantisrx.runtime.MantisJob;
import io.mantisrx.runtime.MantisJobProvider;
import io.mantisrx.runtime.Metadata;
import io.mantisrx.runtime.ScalarToScalar;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogCollectingJob extends MantisJobProvider<LogEvent> {

    @Override
    public Job<LogEvent> getJobInstance() {

        return MantisJob
          .source(new RandomLogSource())
          .stage(new TransformLogStage(), new ScalarToScalar.Config<>())
          .sink(new LogSink())
          .metadata(new Metadata.Builder().build())
          .create();

    }

}
