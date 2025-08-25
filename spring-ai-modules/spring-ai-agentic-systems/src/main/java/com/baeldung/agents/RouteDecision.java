package com.baeldung.agents;

record RouteDecision(
        String category,
        double confidence,
        String reasoning
    ) {}