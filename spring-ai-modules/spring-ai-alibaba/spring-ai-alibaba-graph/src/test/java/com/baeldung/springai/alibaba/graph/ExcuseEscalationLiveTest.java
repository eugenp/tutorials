package com.baeldung.springai.alibaba.graph;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".*")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ExcuseEscalationLiveTest {

    @Autowired
    private ExcuseController excuseController;

    @Test
    void whenSituationIsMundane_thenManagerBelievesFirstExcuse() {
        var request = new ExcuseController.ExcuseRequest("Employee was 30 seconds late to the daily standup");

        var response = excuseController
            .generateExcuse(request)
            .getBody();

        assertThat(response.finalExcuse()).isNotBlank();
        assertThat(response.attempts()).isEqualTo(1);
        assertThat(response.believed()).isTrue();
        assertThat(response.managerReplies()).hasSize(1);
    }

    @Test
    void whenSituationIsSerious_thenExcuseKeepsEscalating() {
        var request = new ExcuseController.ExcuseRequest("""
            The employee didn't show up to work for 3 days without any notice.
            Yet, the employee uploaded pictures of them partying on their public instagram account.
            """);

        var response = excuseController
            .generateExcuse(request)
            .getBody();

        assertThat(response.finalExcuse()).isNotBlank();
        assertThat(response.attempts()).isGreaterThan(1);
        assertThat(response.managerReplies()).hasSize(response.attempts());
    }
}