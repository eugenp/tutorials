package com.baeldung.performancetests.modelmapper;

import com.baeldung.performancetests.Converter;
import com.baeldung.performancetests.model.destination.DestinationCode;
import com.baeldung.performancetests.model.source.SourceCode;
import com.baeldung.performancetests.model.source.SourceOrder;
import com.baeldung.performancetests.model.destination.Order;
import org.modelmapper.ModelMapper;

    public class ModelMapperConverter implements Converter {
        private ModelMapper modelMapper;

        public ModelMapperConverter() {
            modelMapper = new ModelMapper();
        }

        @Override
        public Order convert(SourceOrder sourceOrder) {
           return modelMapper.map(sourceOrder, Order.class);
        }

        @Override
        public DestinationCode convert(SourceCode sourceCode) {
            return modelMapper.map(sourceCode, DestinationCode.class);
        }
    }
