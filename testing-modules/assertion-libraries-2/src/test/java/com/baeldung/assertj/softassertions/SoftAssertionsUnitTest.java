package com.baeldung.assertj.softassertions;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Tests here are intintionally complete with error and therefore disabled
 */
@Disabled
public class SoftAssertionsUnitTest {

    @Test
    void test_softAssertions() {
        RequestMapper requestMapper = new RequestMapper();

        DomainModel result = requestMapper.map(new Request().setType("COMMON"));

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(result.getId()).isNull();
            softAssertions.assertThat(result.getType()).isEqualTo(1);
            softAssertions.assertThat(result.getStatus()).isEqualTo("DRAFT");
        });
    }

    @Test
    void test_softAssertionsViaInstanceCreation() {
        RequestMapper requestMapper = new RequestMapper();

        DomainModel result = requestMapper.map(new Request().setType("COMMON"));

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(result.getId()).isNull();
        softAssertions.assertThat(result.getType()).isEqualTo(1);
        softAssertions.assertThat(result.getStatus()).isEqualTo("DRAFT");
        softAssertions.assertAll();
    }

    @Test
    void test_HardAssertions() {
        RequestMapper requestMapper = new RequestMapper();

        DomainModel result = requestMapper.map(new Request().setType("COMMON"));

        Assertions.assertThat(result.getId()).isNull();
        Assertions.assertThat(result.getType()).isEqualTo(1);
        Assertions.assertThat(result.getStatus()).isEqualTo("DRAFT");
    }
}
