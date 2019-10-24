package com.baeldung.activitiwithspring.servicetasks;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class SendEmailServiceTask implements JavaDelegate {

    public void execute(DelegateExecution execution) {
        //logic to sent email confirmation
    }

}
