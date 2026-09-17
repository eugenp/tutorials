package com.baeldung.springai.alibaba.graph;

import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.action.EdgeAction;
import org.springframework.stereotype.Component;

@Component
class ExcuseDispatcher implements EdgeAction {

    private static final double BELIEVABILITY_THRESHOLD = 0.8;
    private static final int MAX_ATTEMPTS = 3;

    @Override
    public String apply(OverAllState state) {
        Double believability = state.value("believability", 0.0);
        Integer attempts = state.value("attempts", 0);

        if (isBelieved(believability) || attempts >= MAX_ATTEMPTS) {
            return "stop";
        }
        return "escalate";
    }

    static boolean isBelieved(Double believability) {
        return believability >= BELIEVABILITY_THRESHOLD;
    }
}