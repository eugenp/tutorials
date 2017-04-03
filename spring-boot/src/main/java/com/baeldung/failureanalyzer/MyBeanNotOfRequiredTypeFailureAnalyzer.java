package com.baeldung.failureanalyzer;

import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

public class MyBeanNotOfRequiredTypeFailureAnalyzer extends AbstractFailureAnalyzer<BeanNotOfRequiredTypeException> {

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, BeanNotOfRequiredTypeException cause) {
        return new FailureAnalysis(getDescription(cause), getAction(cause), cause);
    }

    private String getDescription(BeanNotOfRequiredTypeException ex) {
        return "The bean " + ex.getBeanName() //
                + " could not be injected as " + ex.getRequiredType().getName() //
                + " because it is of type " + ex.getActualType().getName();
    }

    private String getAction(BeanNotOfRequiredTypeException ex) {
        return "Consider creating a bean with name "+ ex.getBeanName() //
        + " of type " + ex.getRequiredType().getName();
    }

}
