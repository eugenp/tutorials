package com.baeldung.operators.deptrack;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.fabric8.kubernetes.client.KubernetesClient;
import io.javaoperatorsdk.operator.springboot.starter.test.EnableMockOperator;

@SpringBootTest
@EnableMockOperator(crdPaths = "classpath:META-INF/fabric8/deptrackresources.com.baeldung-v1.yml")
class ApplicationLongRunningUnitTest {
    @Autowired
    KubernetesClient client;
    @Test
    void whenContextLoaded_thenCrdRegistered() {

        assertThat(
          client
            .apiextensions()
            .v1()
            .customResourceDefinitions()
            .withName("deptrackresources.com.baeldung")
            .get())
          .isNotNull();
    }

}