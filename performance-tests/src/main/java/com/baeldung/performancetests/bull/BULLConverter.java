package com.baeldung.performancetests.bull;

import com.baeldung.performancetests.Converter;
import com.baeldung.performancetests.model.source.AccountStatus;
import com.baeldung.performancetests.model.source.OrderStatus;
import com.baeldung.performancetests.model.source.PaymentType;
import com.baeldung.performancetests.model.source.SourceCode;
import com.baeldung.performancetests.model.source.SourceOrder;
import com.baeldung.performancetests.model.destination.DestinationCode;
import com.baeldung.performancetests.model.destination.Order;
import om.baeldung.performancetests.model.destination.User;
import com.hotels.beans.BeanUtils;
import com.hotels.beans.model.FieldMapping;
import com.hotels.beans.model.FieldTransformer;

public class BullConverter implements Converter {
    private final BeanUtils beanUtils;

    public BullConverter() {
        BeanUtils beanUtils = new BeanUtils();
        this.beanUtils = beanUtils;
    }

    @Override
    public Order convert(SourceOrder sourceOrder) {
        FieldTransformer<AccountStatus, com.baeldung.performancetests.model.destination.AccountStatus> accountStatusTransformer =
                new FieldTransformer<>("userAccountStatus", userAccountStatus ->
                        new User().conversion(sourceOrder.getOrderingUser().getUserAccountStatus()));
        FieldTransformer<OrderStatus, com.baeldung.performancetests.model.destination.OrderStatus> orderStatusTransformer =
                new FieldTransformer<>("orderStatus", orderStatus -> new Order().conversion(sourceOrder.getStatus()));
        FieldTransformer<PaymentType, com.baeldung.performancetests.model.destination.PaymentType> paymentTypeTransformer =
                new FieldTransformer<>("paymentType", paymentType -> new Order().conversion(sourceOrder.getPaymentType()));
        return beanUtils.getTransformer()
                .withFieldMapping(new FieldMapping("status", "orderStatus"))
                .withFieldTransformer(accountStatusTransformer, orderStatusTransformer, paymentTypeTransformer)
                .transform(sourceOrder, Order.class);
    }

    @Override
    public DestinationCode convert(SourceCode sourceCode) {
        return beanUtils.getTransformer().transform(sourceCode, DestinationCode.class);
    }
}