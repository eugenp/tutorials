package com.baeldung.springai.agenticpatterns.aimodels;

import java.util.Map;

public final class OpsRouterClientPrompts {

    private OpsRouterClientPrompts() {
    }

    /**
     * Array of available routing options of Ops Model
     */
    public static final Map<String, String> OPS_ROUTING_OPTIONS = Map.of(
      // option 1: route to run pipeline
      "pipeline", """
        We'll need make a request to ChainWorkflow. Return only the PR link the user provided""",
      // option 2: route to deploy an image in 1 or more envs
      "deployment", """
        We'll need make a request to ParallelizationWorkflow. Return 3 lines.
          First: the container link the user provided, eg 'host/service/img-name/repo:1.12.1'.
          Second: the environments, separated with comma, no spaces. eg: 'test,dev,int'
          Third: the max concurent workers the client asked for, eg: '3'.""");
}
