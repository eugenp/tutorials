package com.baeldung.boot.documentation.springwolf.service;

import com.baeldung.boot.documentation.springwolf.adapter.outgoing.OutgoingProducer;
import com.baeldung.boot.documentation.springwolf.dto.OutgoingPayloadDto;
import com.baeldung.boot.documentation.springwolf.dto.IncomingPayloadDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProcessorService {

    private final OutgoingProducer outgoingProducer;

    public void doHandle(IncomingPayloadDto payload) {
        OutgoingPayloadDto message = OutgoingPayloadDto.builder()
                .foo("Foo message")
                .incomingWrapped(payload)
                .build();

        outgoingProducer.publish(message);
    }
}
