package com.baeldung.batch.understanding;

import java.util.logging.Logger;

import javax.batch.api.AbstractBatchlet;
import javax.batch.api.BatchProperty;
import javax.batch.runtime.BatchStatus;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class InjectSimpleBatchLet extends AbstractBatchlet {
    @Inject
    @BatchProperty(name = "name")
    private String nameString;
    Logger logger = Logger.getLogger(InjectSimpleBatchLet.class.getName());

    @Override
    public String process() throws Exception {
        logger.info(nameString);
        return BatchStatus.COMPLETED.toString();
    }
}
