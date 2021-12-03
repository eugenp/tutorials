package com.baeldung.architecture.hexagonal.server.rest.dto;

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
public class MessageDTO {
    private Long id;
    private String body;
    private String sender;
    private String receiver;
}
