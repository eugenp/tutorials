package com.baeldung.temporal.workflows.flakyhello.activities;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface FlakySayHelloActivity {
    @ActivityMethod
    String sayHello(String person);
}
