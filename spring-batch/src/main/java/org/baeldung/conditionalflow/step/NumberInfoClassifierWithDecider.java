package org.baeldung.conditionalflow.step;

import org.baeldung.conditionalflow.model.NumberInfo;
import org.springframework.batch.core.listener.ItemListenerSupport;
import org.springframework.batch.item.ItemProcessor;

public class NumberInfoClassifierWithDecider extends ItemListenerSupport<NumberInfo, Integer> implements ItemProcessor<NumberInfo, Integer> {

    @Override
    public Integer process(NumberInfo numberInfo) {
        return Integer.valueOf(numberInfo.getNumber());
    }
}
