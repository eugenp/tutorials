package com.baeldung.springai.alibaba.graph;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.cloud.ai.graph.action.AsyncEdgeAction;
import com.alibaba.cloud.ai.graph.action.AsyncNodeAction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.cloud.ai.graph.CompiledGraph;
import com.alibaba.cloud.ai.graph.KeyStrategy;
import com.alibaba.cloud.ai.graph.KeyStrategyFactory;
import com.alibaba.cloud.ai.graph.StateGraph;
import com.alibaba.cloud.ai.graph.exception.GraphStateException;
import com.alibaba.cloud.ai.graph.state.strategy.AppendStrategy;
import com.alibaba.cloud.ai.graph.state.strategy.ReplaceStrategy;

@Configuration
class ExcuseGraphConfiguration {

    @Bean
    KeyStrategyFactory excuseKeyStrategyFactory() {
        return () -> {
            Map<String, KeyStrategy> strategies = new HashMap<>();

            strategies.put("situation", new ReplaceStrategy());
            strategies.put("excuse", new ReplaceStrategy());
            strategies.put("managerReplies", new AppendStrategy());
            strategies.put("believability", new ReplaceStrategy());
            strategies.put("attempts", new ReplaceStrategy());

            return strategies;
        };
    }

    @Bean
    CompiledGraph excuseGraph(
        KeyStrategyFactory keyStrategyFactory,
        InventExcuseNode inventExcuseNode,
        ManagerReactsNode managerReactsNode,
        ExcuseDispatcher excuseDispatcher
    ) throws GraphStateException {
        StateGraph excuseGraph = new StateGraph(keyStrategyFactory)
            .addNode("invent_excuse", AsyncNodeAction.node_async(inventExcuseNode))
            .addNode("manager_reacts", AsyncNodeAction.node_async(managerReactsNode))
            .addEdge(StateGraph.START, "invent_excuse")
            .addEdge("invent_excuse", "manager_reacts")
            .addConditionalEdges(
                "manager_reacts",
                AsyncEdgeAction.edge_async(excuseDispatcher),
                Map.of(
                    "escalate", "invent_excuse",
                    "stop", StateGraph.END
                )
            );

        return excuseGraph.compile();
    }
}