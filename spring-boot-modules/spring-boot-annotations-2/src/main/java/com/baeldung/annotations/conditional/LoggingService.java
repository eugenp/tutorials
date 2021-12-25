package com.baeldung.annotations.conditional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.system.JavaVersion;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

@Service
@Conditional({IsDevEnvCondition.class, IsWindowsCondition.class, Java8Condition.class})
@ConditionalOnProperty(
  value = "logging.enabled",
  havingValue = "true",
  matchIfMissing = true)
@ConditionalOnExpression("${logging.enabled:true} and '${logging.level}'.equals('DEBUG')")
@ConditionalOnJava(JavaVersion.EIGHT)
public class LoggingService {
}
