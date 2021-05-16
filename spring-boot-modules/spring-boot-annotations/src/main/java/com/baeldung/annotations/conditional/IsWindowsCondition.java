package java.com.baeldung.annotations.conditional;

import org.apache.commons.lang3.SystemUtils;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class IsWindowsCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return SystemUtils.IS_OS_WINDOWS;;
    }
}
