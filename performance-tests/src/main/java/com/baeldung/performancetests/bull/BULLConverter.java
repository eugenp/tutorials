package com.baeldung.performancetests.bull;

import com.baeldung.performancetests.Converter;
import com.baeldung.performancetests.model.source.AccountStatus;
import com.baeldung.performancetests.model.source.OrderStatus;
import com.baeldung.performancetests.model.source.PaymentType;
import com.baeldung.performancetests.model.source.SourceCode;
import com.baeldung.performancetests.model.source.SourceOrder;
import com.baeldung.performancetests.model.destination.Order;
import com.baeldung.performancetests.model.destination.DestinationCode;
import com.hotels.beans.BeanUtils;
import com.hotels.beans.model.FieldMapping;
import com.hotels.beans.model.FieldTransformer;

public class BULLConverter implements Converter {
    private final BeanUtils beanUtils;

    public BULLConverter() {
        this.beanUtils = new BeanUtils();
    }

    @Override
    public Order convert(SourceOrder sourceOrder) {
        return beanUtils.getTransformer()
                .withFieldMapping(new FieldMapping("status", "orderStatus"))
                .withFieldTransformer(getOrderTransformerFunctions(sourceOrder))
                .transform(sourceOrder, Order.class);
    }

    /**
     * Initialized the lambda functions required for special order field conversion.
     * @param sourceOrder the source object
     * @return an array containing the required lambda functions required for special order field conversion.
     */
    private FieldTransformer[] getOrderTransformerFunctions(final SourceOrder sourceOrder) {
        FieldTransformer<AccountStatus, com.baeldung.performancetests.model.destination.AccountStatus> accountStatusTransformer =
                new FieldTransformer<>("userAccountStatus", userAccountStatus ->
                        new com.baeldung.performancetests.model.destination.User().conversion(sourceOrder.getOrderingUser().getUserAccountStatus()));

        FieldTransformer<OrderStatus, com.baeldung.performancetests.model.destination.OrderStatus> orderStatusTransformer =
                new FieldTransformer<>("orderStatus", orderStatus -> new Order().conversion(sourceOrder.getStatus()));

        FieldTransformer<PaymentType, com.baeldung.performancetests.model.destination.PaymentType> paymentTypeTransformer =
                new FieldTransformer<>("paymentType", paymentType -> new Order().conversion(sourceOrder.getPaymentType()));

        return new FieldTransformer[] {
                accountStatusTransformer, orderStatusTransformer, paymentTypeTransformer
        };
    }

    @Override
    public DestinationCode convert(SourceCode sourceCode) {
        return beanUtils.getTransformer().transform(sourceCode, DestinationCode.class);
    }
}