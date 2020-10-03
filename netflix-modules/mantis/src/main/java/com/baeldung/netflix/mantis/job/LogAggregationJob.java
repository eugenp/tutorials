package com.baeldung.netflix.mantis.job;

import com.baeldung.netflix.mantis.model.LogAggregate;
import com.baeldung.netflix.mantis.source.RandomLogSource;
import com.baeldung.netflix.mantis.stage.CountLogStage;
import com.baeldung.netflix.mantis.stage.GroupLogStage;
import com.baeldung.netflix.mantis.stage.TransformLogStage;
import io.mantisrx.runtime.Job;
import io.mantisrx.runtime.MantisJob;
import io.mantisrx.runtime.MantisJobProvider;
import io.mantisrx.runtime.Metadata;
import io.mantisrx.runtime.sink.Sinks;

public class LogAggregationJob extends MantisJobProvider<LogAggregate> {

    @Override
    public Job<LogAggregate> getJobInstance() {

        return MantisJob
          .source(new RandomLogSource())
          .stage(new TransformLogStage(), TransformLogStage.stageConfig())
          .stage(new GroupLogStage(), GroupLogStage.config())
          .stage(new CountLogStage(), CountLogStage.config())
          .sink(Sinks.eagerSubscribe(Sinks.sse(LogAggregate::toJsonString)))
          .metadata(new Metadata.Builder().build())
          .create();

    }

}
