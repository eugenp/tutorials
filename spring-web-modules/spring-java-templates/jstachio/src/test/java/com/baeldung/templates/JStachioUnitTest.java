package com.baeldung.templates;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class JStachioUnitTest {
    @Test
    void whenRenderingJStachio_thenTheOutputIsCorrect() {
        JStachioModel model = new JStachioModel("Baeldung");

        StringBuilder sb = new StringBuilder();
        JStachioModelRenderer.of().execute(model, sb);

        assertEquals("""
            <html>
              <body>
                Hello, Baeldung!
              </body>
            </html>
            """, sb.toString());
    }
}
