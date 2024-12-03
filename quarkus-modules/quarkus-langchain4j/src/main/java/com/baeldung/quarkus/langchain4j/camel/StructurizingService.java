package com.baeldung.quarkus.langchain4j.camel;

import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.Handler;
import org.apache.camel.Header;
import org.apache.camel.jsonpath.JsonPath;

@RegisterAiService
@ApplicationScoped
public interface StructurizingService {

    String EXTRACT_PROMPT = """
            Extract information about a patient from the text delimited by triple backticks: ```{text}```.
            The customerBirthday field should be formatted as {dateFormat}.
            The summary field should concisely relate the patient visit reason.
            The expected fields are: patientName, patientBirthday, visitReason, allergies, medications.
            Return only a data structure without format name.
            """;

    @UserMessage(EXTRACT_PROMPT)
    @Handler
    String structurize(@JsonPath("$.content") String text, @Header("expectedDateFormat") String dateFormat);
}
