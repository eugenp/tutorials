package com.baeldung.performancetests.dozer;

import com.baeldung.performancetests.Converter;
import com.baeldung.performancetests.model.source.SourceOrder;
import com.baeldung.performancetests.model.destination.Order;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

public class DozerConverter implements Converter {
    private final Mapper mapper;

    public DozerConverter() {
        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.addMapping(DozerConverter.class.getResourceAsStream("/dozer-mapping.xml"));
        this.mapper = mapper;
    }

    @Override
    public Order convert(SourceOrder sourceOrder) {
        return mapper.map(sourceOrder, Order.class);
    }
}
