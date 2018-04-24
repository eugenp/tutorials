package com.baeldung.performancetests.jmapper;

import com.baeldung.performancetests.Converter;
import com.baeldung.performancetests.model.source.SourceOrder;
import com.baeldung.performancetests.model.destination.Order;
import com.googlecode.jmapper.JMapper;
import com.googlecode.jmapper.api.JMapperAPI;

public class JMapperConverter implements Converter {
    JMapper mapper;
    public JMapperConverter() {
        JMapperAPI api = new JMapperAPI().add(JMapperAPI.mappedClass(Order.class));
        api = api.add(JMapperAPI.mappedClass(Order.class));
        mapper = new JMapper(Order.class, SourceOrder.class, api);
    }

    @Override
    public Order convert(SourceOrder sourceOrder) {
        return (Order) mapper.getDestination(sourceOrder);
    }
}
