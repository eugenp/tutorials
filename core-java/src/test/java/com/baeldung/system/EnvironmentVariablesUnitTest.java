package com.baeldung.system;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试：获取系统环境变量
 */
public class EnvironmentVariablesUnitTest {

    @Test
    public void givenEnvVars_whenReadPath_thenGetValueinResult() {
        EnvironmentVariables environmentVariables = new EnvironmentVariables();
        System.out.println("environmentVariables.getPath():{}" + environmentVariables.getPath());

        Assert.assertNotNull(environmentVariables.getPath());
    }
}
