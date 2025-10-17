package com.baeldung.micrometer.tags;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import io.micrometer.common.annotation.ValueExpressionResolver;
import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.aop.CountedMeterTagAnnotationHandler;
import io.micrometer.core.aop.MeterTagAnnotationHandler;
import io.micrometer.core.aop.TimedAspect;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public MeterTagAnnotationHandler meterTagAnnotationHandler() {
        return new MeterTagAnnotationHandler(
            aClass -> Object::toString,
            aClass -> (exp, param) -> "");
    }

    @Bean
    public CountedAspect countedAspect() {
        CountedAspect aspect = new CountedAspect();
        CountedMeterTagAnnotationHandler tagAnnotationHandler = new CountedMeterTagAnnotationHandler(
            aClass -> o -> new SpelValueExpressionResolver().resolve(o.toString(), null),
            aClass -> new SpelValueExpressionResolver());
        aspect.setMeterTagAnnotationHandler(tagAnnotationHandler);
        return aspect;
    }

    public static class SpelValueExpressionResolver implements ValueExpressionResolver {

        @Override
        public String resolve(String expression, Object parameter) {
            try {
                SpelParserConfiguration config = new SpelParserConfiguration(true, true);
                StandardEvaluationContext context = new StandardEvaluationContext(parameter);

                ExpressionParser expressionParser = new SpelExpressionParser(config);
                return expressionParser.parseExpression(expression)
                    .getValue(context, String.class);

                //                ExpressionParser expressionParser = new SpelExpressionParser();
//                Expression expressionToEvaluate = expressionParser.parseExpression(expression);
//                return expressionToEvaluate.getValue(parameter, String.class);
            }
            catch (Exception ex) {
                ex.printStackTrace(); // todo: proper logging
            }
            return String.valueOf(parameter);
        }
    }
}
