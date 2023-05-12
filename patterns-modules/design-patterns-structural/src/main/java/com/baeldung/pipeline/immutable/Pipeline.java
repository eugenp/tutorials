package com.baeldung.pipeline.immutable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Pipeline<IN, OUT> {

    private Collection<Pipe<?, ?>> pipes;

    private Pipeline(Pipe<IN, OUT> pipe) {
        pipes = Collections.singletonList(pipe);
    }

    private Pipeline(Collection<Pipe<?, ?>> pipes) {
        this.pipes = new ArrayList<>(pipes);
    }

    public static <IN, OUT> Pipeline<IN, OUT> of(Pipe<IN, OUT> pipe) {
        return new Pipeline<>(pipe);
    }


    public <NEW_OUT> Pipeline<IN, NEW_OUT> withNextPipe(Pipe<OUT, NEW_OUT> pipe) {
        final ArrayList<Pipe<?, ?>> newPipes = new ArrayList<>(pipes);
        newPipes.add(pipe);
        return new Pipeline<>(newPipes);
    }

    public OUT process(IN input) {
        Object output = input;
        for (final Pipe pipe : pipes) {
            output = pipe.process(output);
        }
        return (OUT) output;
    }
}
