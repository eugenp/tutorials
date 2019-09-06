package com.baeldung.performancetests.bull;

import com.baeldung.performancetests.Converter;
import com.baeldung.performancetests.model.destination.DestinationCode;
import com.baeldung.performancetests.model.destination.Order;
import com.baeldung.performancetests.model.source.AccountStatus;
import com.baeldung.performancetests.model.source.OrderStatus;
import com.baeldung.performancetests.model.source.PaymentType;
import com.baeldung.performancetests.model.source.SourceCode;
import com.baeldung.performancetests.model.source.SourceOrder;

import com.hotels.beans.BeanUtils;
import com.hotels.beans.model.FieldMapping;
import com.hotels.beans.model.FieldTransformer;
import com.hotels.beans.transformer.Transformer;

/**
 * BULL converter configuration.
 * @see <a href="https://github.com/HotelsDotCom/bull">https://github.com/HotelsDotCom/bull</a>
 */
public class BullConverter implements Converter {
    private final Transformer beanTransformer;

    public BullConverter() {
        beanTransformer = new BeanUtils().getTransformer()
                .withFieldMapping(new FieldMapping("status", "orderStatus"))
                .setDefaultValueSetEnabled(false)
                .setFlatFieldNameTransformation(true);
    }

    @Override
    public Order convert(SourceOrder sourceOrder) {
        FieldTransformer<AccountStatus, com.baeldung.performancetests.model.destination.AccountStatus> accountStatusTransformer =
                new FieldTransformer<>("userAccountStatus", userAccountStatus ->
                        new com.baeldung.performancetests.model.destination.User().conversion(sourceOrder.getOrderingUser().getUserAccountStatus()));
        FieldTransformer<OrderStatus, com.baeldung.performancetests.model.destination.OrderStatus> orderStatusTransformer =
                new FieldTransformer<>("orderStatus", orderStatus -> new Order().conversion(sourceOrder.getStatus()));
        FieldTransformer<PaymentType, com.baeldung.performancetests.model.destination.PaymentType> paymentTypeTransformer =
                new FieldTransformer<>("paymentType", paymentType -> new Order().conversion(sourceOrder.getPaymentType()));
        return beanTransformer.withFieldTransformer(accountStatusTransformer, orderStatusTransformer, paymentTypeTransformer)
                .transform(sourceOrder, Order.class);
    }

    @Override
    public DestinationCode convert(SourceCode sourceCode) {
        return beanTransformer.transform(sourceCode, DestinationCode.class);
    }
}
