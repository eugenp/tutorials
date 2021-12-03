package com.baeldung.architecture.hexagonal.domain.message.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder(toBuilder = true)
public class Message {
    private Long id;
    private String body;
    private String sender;
    private String receiver;
}
