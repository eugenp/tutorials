package com.baeldung.performancetests.bull;

import com.baeldung.performancetests.Converter;
import com.baeldung.performancetests.model.destination.DestinationCode;
import com.baeldung.performancetests.model.destination.Order;
import com.baeldung.performancetests.model.source.SourceCode;
import com.baeldung.performancetests.model.source.SourceOrder;
import com.hotels.beans.BeanUtils;
import com.hotels.beans.model.FieldMapping;
import com.hotels.beans.model.FieldTransformer;
import com.hotels.beans.transformer.Transformer;

/**
 * BULL library converter.
 * @author fborriello
 */
public class BULLConverter implements Converter {
    private final BeanUtils beanUtils;

    /**
     * Default constructor.
     */
    public BULLConverter() {
        this.beanUtils = new BeanUtils();
    }

    @Override
    public Order convert(final SourceOrder sourceOrder) {
        return beanUtils.getTransformer()
                .withFieldMapping(new FieldMapping("status", "orderStatus")).transform(sourceOrder, Order.class);
    }

    @Override
    public DestinationCode convert(final SourceCode sourceCode) {
        return beanUtils.getTransformer().transform(sourceCode, DestinationCode.class);
    }
}
