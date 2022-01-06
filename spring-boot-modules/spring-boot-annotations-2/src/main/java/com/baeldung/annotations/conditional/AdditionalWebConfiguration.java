package com.baeldung.annotations.conditional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWarDeployment;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnWarDeployment
public class AdditionalWebConfiguration {
}
