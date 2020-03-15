package org.baeldung.conditionalflow.step;

import org.baeldung.conditionalflow.model.NumberInfo;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;

public class NumberInfoClassifierWithDecider
        implements ItemProcessor<NumberInfo, Integer> {
    private StepExecution stepExecution;

    @Override
    public Integer process(NumberInfo numberInfo) throws Exception {
        return Integer.valueOf(numberInfo.getNumber());
    }
}
