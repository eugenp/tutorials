package java.com.baeldung.annotations.conditional;

import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.context.annotation.Conditional;

public class Java8OrJava9 extends AnyNestedCondition  {
    Java8OrJava9() {
        super(ConfigurationPhase.REGISTER_BEAN);
    }

    @Conditional(Java8Condition.class)
    static class Java8 {
    }

    @Conditional(Java9Condition.class)
    static class Java9 {
    }
}
