package com.baeldung.templates;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import com.fizzed.rocker.BindableRockerModel;
import com.fizzed.rocker.ContentType;
import com.fizzed.rocker.Rocker;
import com.fizzed.rocker.RockerOutput;

public class RockerUnitTest {
    @Test
    void whenRenderingRocker_thenTheOutputIsCorrect() {
        BindableRockerModel template = Rocker.template("RockerDemo.rocker.html");
        template.bind("model", new RockerModel("Baeldung"));
        RockerOutput output = template.render();

        assertEquals(ContentType.HTML, output.getContentType());
        assertEquals(StandardCharsets.UTF_8, output.getCharset());

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
