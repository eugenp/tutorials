package com.baeldung.boot.documentation.springwolf.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@Schema(description = "Outgoing payload model")
public class OutgoingPayloadDto {

    @Schema(description = "Foo field", example = "bar", requiredMode = NOT_REQUIRED)
    private String foo;

    @Schema(description = "IncomingPayload field", requiredMode = REQUIRED)
    private IncomingPayloadDto incomingWrapped;
}
