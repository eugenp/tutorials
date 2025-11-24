package com.baeldung.mapper;

import com.baeldung.dto.PaymentDto;
import com.baeldung.entity.Payment;
import org.mapstruct.Mapper;

@Mapper
public interface PaymentMapper {

    PaymentDto toDto(Payment payment);

}
