package org.baeldung.conditionalflow.step;

import org.baeldung.conditionalflow.model.NumberInfo;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.listener.ItemListenerSupport;
import org.springframework.batch.item.ItemProcessor;

public class NumberInfoClassifier extends ItemListenerSupport<NumberInfo, Integer>
        implements ItemProcessor<NumberInfo, Integer> {
    private StepExecution stepExecution;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

    @Override
    public void afterProcess(NumberInfo item, Integer result) {
        super.afterProcess(item, result);
        if (item.isPositive()) {
            stepExecution.setExitStatus(new ExitStatus("NOTIFY"));
        }
    }

    @Override
    public Integer process(NumberInfo numberInfo) throws Exception {
        return Integer.valueOf(numberInfo.getNumber());
    }
}
