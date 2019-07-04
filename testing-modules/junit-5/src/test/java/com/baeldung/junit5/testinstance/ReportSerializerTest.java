package com.baeldung.junit5.testinstance;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.rules.ExpectedException;

@TestInstance(Lifecycle.PER_CLASS)
public class ReportSerializerTest {

    private String largeContent;
    private String content = "Lorem ipsum dolor sit amet, eam no tale solet patrioque, est " + "ne dico veri. Copiosae petentium no eum, has at wisi dicunt causae. Duo ea ";
    private String smallContent;

    private Report report;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeAll
    void setUpFixture() {

        StringBuilder reportBuilder = new StringBuilder();
        smallContent = "Lorem ipsum dolor";

        reportBuilder = new StringBuilder();
        for (int i = 0; i < 100000; i++) {
            reportBuilder.append(content);
        }

        largeContent = reportBuilder.toString();
        report = new Report();
        report.setId("AX1346");
    }

    @Test
    void serializeReportThrowsExceptionWhenMessageIsTooLarge() throws IOException {
        report.setContent(largeContent);

        ReportException reportException = assertThrows(ReportException.class, () -> new ReportSerializer(report).serialize());
        assertTrue(reportException.getMessage()
            .contains("Report is too large"));
    }

    @Test
    void serializeReportThrowsExceptionWhenMessageIsTooSmall() throws IOException {
        report.setContent(smallContent);

        ReportException reportException = assertThrows(ReportException.class, () -> new ReportSerializer(report).serialize());
        assertTrue(reportException.getMessage()
            .contains("Report is too small"));
    }

    @Test
    void serializeReport() throws IOException {
        report.setContent(content);
        byte[] content = new ReportSerializer(report).serialize();
        assertThat(content, is(notNullValue()));
    }

}
