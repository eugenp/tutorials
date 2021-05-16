package java.com.baeldung.annotations.conditional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

@Service
@Conditional(IsDevEnvCondition.class)
@ConditionalOnProperty(
  value = "logging.enabled",
  havingValue = "true",
  matchIfMissing = true)
@ConditionalOnExpression("${logging.enabled:true} and ${logging.level:DEBUG}")
@ConditionalOnJava(ConditionalOnJava.JavaVersion.EIGHT)
@Conditional(IsWindowsCondition.class)
@Conditional(Java8Condition.class)
public class LoggingService {
}
