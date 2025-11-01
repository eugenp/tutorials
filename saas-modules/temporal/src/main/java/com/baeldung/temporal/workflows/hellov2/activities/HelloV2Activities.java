package com.baeldung.temporal.workflows.hellov2.activities;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface HelloV2Activities {
    @ActivityMethod
    String sayHello(String person);
    String sayGoodbye(String person);
}
