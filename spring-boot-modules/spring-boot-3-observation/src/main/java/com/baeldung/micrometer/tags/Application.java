package com.baeldung.micrometer.tags;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;

import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.aop.MeterTagAnnotationHandler;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;

@SpringBootApplication
class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public MeterTagAnnotationHandler meterTagAnnotationHandler() {
        return new MeterTagAnnotationHandler(
            aClass -> Object::toString,
            aClass -> Application::spelValueExpressionResolver
        );
    }

    @Bean
    public TimedAspect timedAspect(MeterRegistry registry, MeterTagAnnotationHandler meterTagAnnotationHandler) {
        TimedAspect timedAspect = new TimedAspect(registry);
        timedAspect.setMeterTagAnnotationHandler(meterTagAnnotationHandler);
        return timedAspect;
    }

    @Bean
    public CountedAspect countedAspect(MeterRegistry registry) {
//        CountedAspect countedAspect = new CountedAspect(registry);
        CountedAspect countedAspect = new CountedAspect(
            registry,
            pjp -> List.of(), // <- maybe here, but it seems overly complicated
            pjp -> false
        );
        // here I don't have an equivalent of .setMeterTagAnnotationHandler()
        return countedAspect;
    }

    private static String spelValueExpressionResolver(String expression, Object parameter) {
        try {
            SimpleEvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding()
                .build();
            ExpressionParser expressionParser = new SpelExpressionParser();
            Expression expressionToEvaluate = expressionParser.parseExpression(expression);
            return expressionToEvaluate.getValue(context, parameter, String.class);
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to evaluate SpEL expression '%s'".formatted(expression), ex);
        }
    }

}
