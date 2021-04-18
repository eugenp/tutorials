package com.baeldung.pattern.hexagonal.architecture.application.adapters;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.baeldung.pattern.hexagonal.architecture.application.dto.AddressDto;
import com.baeldung.pattern.hexagonal.architecture.application.factory.AddressFactory;
import com.baeldung.pattern.hexagonal.architecture.domain.ports.AddressService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddressQueueListener {

    private final AddressService addressService;

    @RabbitListener(queues = "${address.queue}")
    public void receiveMessage(AddressDto addressDto) {
        addressService.save(AddressFactory.create(addressDto));
    }
}
