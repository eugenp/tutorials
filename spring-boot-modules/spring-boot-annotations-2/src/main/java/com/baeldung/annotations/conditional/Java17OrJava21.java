package com.baeldung.annotations.conditional;

import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.context.annotation.Conditional;

public class Java17OrJava21 extends AnyNestedCondition  {
    Java17OrJava21() {
        super(ConfigurationPhase.REGISTER_BEAN);
    }

    @Conditional(Java17Condition.class)
    static class Java17 { }

    @Conditional(Java21Condition.class)
    static class Java21 { }
}
