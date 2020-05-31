package com.baeldung.postprocessor;

import com.google.common.eventbus.EventBus;

import java.util.function.BiConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionException;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * A {@link DestructionAwareBeanPostProcessor} which registers/un-registers subscribers to a Guava {@link EventBus}. The class must
 * be annotated with {@link Subscriber} and each subscribing method must be annotated with
 * {@link com.google.common.eventbus.Subscribe}.
 */
@SuppressWarnings("ALL")
public class GuavaEventBusBeanPostProcessor implements DestructionAwareBeanPostProcessor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SpelExpressionParser expressionParser = new SpelExpressionParser();

    @Override
    public void postProcessBeforeDestruction(final Object bean, final String beanName) throws BeansException {
        this.process(bean, EventBus::unregister, "destruction");
    }

    @Override
    public boolean requiresDestruction(Object bean) {
        return true;
    }

    @Override
    public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
        this.process(bean, EventBus::register, "initialization");
        return bean;
    }

    private void process(final Object bean, final BiConsumer<EventBus, Object> consumer, final String action) {
        Object proxy = this.getTargetObject(bean);
        final Subscriber annotation = AnnotationUtils.getAnnotation(proxy.getClass(), Subscriber.class);
        if (annotation == null)
            return;
        this.logger.info("{}: processing bean of type {} during {}", this.getClass().getSimpleName(), proxy.getClass().getName(),
          action);
        final String annotationValue = annotation.value();
        try {
            final Expression expression = this.expressionParser.parseExpression(annotationValue);
            final Object value = expression.getValue();
            if (!(value instanceof EventBus)) {
                this.logger.error("{}: expression {} did not evaluate to an instance of EventBus for bean of type {}",
                  this.getClass().getSimpleName(), annotationValue, proxy.getClass().getSimpleName());
                return;
            }
            final EventBus eventBus = (EventBus)value;
            consumer.accept(eventBus, proxy);
        } catch (ExpressionException ex) {
            this.logger.error("{}: unable to parse/evaluate expression {} for bean of type {}", this.getClass().getSimpleName(),
              annotationValue, proxy.getClass().getName());
        }
    }

    private Object getTargetObject(Object proxy) throws BeansException {
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            try {
                return ((Advised)proxy).getTargetSource().getTarget();
            } catch (Exception e) {
                throw new FatalBeanException("Error getting target of JDK proxy", e);
            }
        }
        return proxy;
    }
}


