package com.baeldung.temporal.workflows.hello.activities;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface SayHelloActivity {
    @ActivityMethod
    String sayHello(String person);
}
