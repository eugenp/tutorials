package com.baeldung.templates;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.output.StringOutput;

public class JteUnitTest {
    @Test
    void whenRenderingJTE_thenTheOutputIsCorrect() {
        TemplateEngine templateEngine = TemplateEngine.createPrecompiled(ContentType.Html);
        StringOutput output = new StringOutput();
        templateEngine.render("JteDemo.jte", new JteModel("Baeldung"), output);

        assertEquals("""
            <html>
                <head>
                </head>
                <body>
            	    <h1>Demo</h1>
            	    <p>Hello Baeldung!</p>
                </body>
            </html>
            """, output.toString());
    }

}
