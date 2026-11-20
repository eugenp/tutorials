package com.baeldung.junit5.templates;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

public class UserIdGeneratorImplUnitTest {
    @TestTemplate
    @ExtendWith(UserIdGeneratorTestInvocationContextProvider.class)
    public void whenUserIdRequested_thenUserIdIsReturnedInCorrectFormat(UserIdGeneratorTestCase testCase) {
        UserIdGenerator userIdGenerator = new UserIdGeneratorImpl(testCase.isFeatureEnabled());

        String actualUserId = userIdGenerator.generate(testCase.getFirstName(), testCase.getLastName());

        assertThat(actualUserId).isEqualTo(testCase.getExpectedUserId());
    }
}