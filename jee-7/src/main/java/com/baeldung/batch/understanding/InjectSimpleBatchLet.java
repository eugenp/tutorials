package com.baeldung.batch.understanding;

import java.util.Properties;
import java.util.logging.Logger;

import javax.batch.api.AbstractBatchlet;
import javax.batch.api.BatchProperty;
import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.context.JobContext;
import javax.batch.runtime.context.StepContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class InjectSimpleBatchLet extends AbstractBatchlet {
    Logger logger = Logger.getLogger(InjectSimpleBatchLet.class.getName());

    @Inject
    @BatchProperty(name = "name")
    private String nameString;

    @Inject
    StepContext stepContext;
    private Properties stepProperties;    
    @Inject
    JobContext jobContext;
    private Properties jobProperties;

    @Override
    public String process() throws Exception {
        logger.info("BatchProperty : " + nameString);
        stepProperties = stepContext.getProperties();
        jobProperties = jobContext.getProperties();
        logger.info("Step property : "+ stepProperties.getProperty("stepProp1"));
        logger.info("job property : "+jobProperties.getProperty("jobProp1"));
        return BatchStatus.COMPLETED.toString();
    }
}
