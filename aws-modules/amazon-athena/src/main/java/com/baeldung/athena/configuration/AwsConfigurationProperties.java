package com.baeldung.athena.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "com.baeldung.aws")
public class AwsConfigurationProperties {

    @NotBlank(message = "AWS access key must be configured")
    private String accessKey;

    @NotBlank(message = "AWS secret key must be configured")
    private String secretKey;

    @Valid
    private Athena athena = new Athena();

    @Getter
    @Setter
    public class Athena {

        @Nullable
        private String database = "default";

        @NotBlank(message = "S3 output location must be configured")
        private String s3OutputLocation;

    }

}
