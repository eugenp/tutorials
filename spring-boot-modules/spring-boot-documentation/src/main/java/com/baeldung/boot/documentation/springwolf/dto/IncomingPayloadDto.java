package com.baeldung.boot.documentation.springwolf.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(description = "Incoming payload model")
public class IncomingPayloadDto {
    @Schema(description = "Some string field", example = "some string value", requiredMode = REQUIRED)
    private String someString;

    @Schema(description = "Some long field", example = "5", requiredMode = NOT_REQUIRED)
    private long someLong;

    @Schema(description = "Some enum field", example = "FOO2", requiredMode = REQUIRED)
    private IncomingPayloadEnum someEnum;

    public enum IncomingPayloadEnum {
        FOO1, FOO2, FOO3
    }

}
