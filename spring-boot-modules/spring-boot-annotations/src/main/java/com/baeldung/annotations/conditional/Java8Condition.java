package java.com.baeldung.annotations.conditional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class Java8Condition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return ConditionalOnJava.JavaVersion.getJavaVersion().equals(ConditionalOnJava.JavaVersion.EIGHT);
    }
}
