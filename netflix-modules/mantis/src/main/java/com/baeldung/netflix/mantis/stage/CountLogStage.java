package com.baeldung.netflix.mantis.stage;

import com.baeldung.netflix.mantis.model.LogAggregate;
import com.baeldung.netflix.mantis.model.LogEvent;
import io.mantisrx.common.MantisGroup;
import io.mantisrx.runtime.Context;
import io.mantisrx.runtime.GroupToScalar;
import io.mantisrx.runtime.codec.JacksonCodecs;
import io.mantisrx.runtime.computation.GroupToScalarComputation;
import io.mantisrx.runtime.parameter.ParameterDefinition;
import io.mantisrx.runtime.parameter.type.IntParameter;
import io.mantisrx.runtime.parameter.validator.Validators;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CountLogStage implements GroupToScalarComputation<String, LogEvent, LogAggregate> {

    private int duration;

    @Override
    public void init(Context context) {
        duration = (int)context.getParameters().get("LogAggregationDuration", 1000);
    }

    @Override
    public Observable<LogAggregate> call(Context context, Observable<MantisGroup<String, LogEvent>> mantisGroup) {
        return mantisGroup
          .window(duration, TimeUnit.MILLISECONDS)
          .flatMap(o -> o.groupBy(MantisGroup::getKeyValue)
            .flatMap(group -> group.reduce(0, (count, value) ->  count = count + 1)
              .map((count) -> new LogAggregate(count, group.getKey()))
            ));
    }

    public static GroupToScalar.Config<String, LogEvent, LogAggregate> config(){
        return new GroupToScalar.Config<String, LogEvent, LogAggregate>()
          .description("sum events for a log level")
          .codec(JacksonCodecs.pojo(LogAggregate.class))
          .withParameters(getParameters());
    }

    public static List<ParameterDefinition<?>> getParameters() {
        List<ParameterDefinition<?>> params = new ArrayList<>();

        params.add(new IntParameter()
          .name("LogAggregationDuration")
          .description("window size for aggregation in milliseconds")
          .validator(Validators.range(100, 10000))
          .defaultValue(5000)
          .build())	;

        return params;
    }
    
}
