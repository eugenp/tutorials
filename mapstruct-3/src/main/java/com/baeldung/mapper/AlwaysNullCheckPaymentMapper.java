package com.baeldung.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

import com.baeldung.dto.PaymentDto;
import com.baeldung.entity.Payment;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface AlwaysNullCheckPaymentMapper {

    PaymentDto toDto(Payment payment);

}
