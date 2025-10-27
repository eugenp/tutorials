package com.baeldung.context;

import org.apache.http.HttpStatus;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ContextServletUnitTest extends BaseServletTest {

    private static final List<String> CONTEXT_LABELS = List.of(ContextServlet.LABEL_FROM_HTTP_SERVLET, ContextServlet.LABEL_FROM_SERVLET_CONFIG,
        ContextServlet.LABEL_FROM_HTTP_SERVLET_REQUEST, ContextServlet.LABEL_FROM_HTTP_SESSION);

    @Override
    protected void configure(ServletContextHandler ctx) {
        ctx.addServlet(ContextServlet.class, ContextServlet.PATH);
    }

    @Test
    void givenContextServlet_whenGetRequest_thenResponseContainsSameContextInstance() throws Exception {
        ContentResponse response = httpClient.GET(URI.create(baseUri() + ContextServlet.PATH));

        assertEquals(HttpStatus.SC_OK, response.getStatus());

        String body = response.getContentAsString();

        assertContextLinesIn(body);

        List<String> tokens = parseServletContextTokens(body);
        assertAllEqual(tokens);
    }

    private static void assertContextLinesIn(String body) {
        for (String label : CONTEXT_LABELS) {
            assertTrue(body.contains(label));
        }
    }

    private static List<String> parseServletContextTokens(String body) {
        List<String> targetLines = body.lines()
            .filter(line -> CONTEXT_LABELS.stream()
                .anyMatch(line::startsWith))
            .collect(Collectors.toList());

        assertEquals(CONTEXT_LABELS.size(), targetLines.size());

        return targetLines.stream()
            .map(line -> {
                int indexOf = line.indexOf(':');
                assertTrue(indexOf >= 0);
                return line.substring(indexOf + 1)
                    .trim();
            })
            .collect(Collectors.toList());
    }

    private static void assertAllEqual(List<String> tokens) {
        Set<String> distinct = Set.copyOf(tokens);
        assertEquals(1, distinct.size());
    }
}
