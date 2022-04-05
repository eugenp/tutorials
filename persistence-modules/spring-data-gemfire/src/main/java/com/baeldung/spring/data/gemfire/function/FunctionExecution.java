package com.baeldung.spring.data.gemfire.function;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.gemfire.function.annotation.FunctionId;
import org.springframework.data.gemfire.function.annotation.OnRegion;
import org.springframework.data.gemfire.function.annotation.RegionData;

import java.util.Map;
import java.util.Set;

@OnRegion(region="employee")
public interface FunctionExecution {
    @FunctionId("greeting")
    public void execute(String message);

}
