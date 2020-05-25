package com.baeldung.postprocessor;

import com.google.common.eventbus.EventBus;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionException;
import org.springframework.expression.spel.standard.SpelExpressionParser;

@SuppressWarnings("ALL")
public class GuavaEventBusBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SpelExpressionParser expressionParser = new SpelExpressionParser();

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        for (Iterator<String> names = beanFactory.getBeanNamesIterator(); names.hasNext(); ) {
            Object proxy = this.getTargetObject(beanFactory.getBean(names.next()));
            final Subscriber annotation = AnnotationUtils.getAnnotation(proxy.getClass(), Subscriber.class);
            if (annotation == null)
                continue;
            this.logger.info("{}: processing bean of type {} during initialization", this.getClass().getSimpleName(),
              proxy.getClass().getName());
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
                eventBus.register(proxy);
            } catch (ExpressionException ex) {
                this.logger.error("{}: unable to parse/evaluate expression {} for bean of type {}", this.getClass().getSimpleName(),
                  annotationValue, proxy.getClass().getName());
            }
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
