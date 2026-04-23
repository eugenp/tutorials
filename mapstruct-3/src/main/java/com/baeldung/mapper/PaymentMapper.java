package com.baeldung.mapper;

import org.mapstruct.Mapper;

import com.baeldung.dto.PaymentDto;
import com.baeldung.entity.Payment;

@Mapper
public interface PaymentMapper {

    PaymentDto toDto(Payment payment);

}
