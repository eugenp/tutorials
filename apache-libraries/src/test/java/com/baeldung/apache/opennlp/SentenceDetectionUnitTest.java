package com.baeldung.apache.opennlp;

import java.io.InputStream;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class SentenceDetectionUnitTest {

    @Test
    public void givenEnglishModel_whenDetect_thenSentencesAreDetected() throws Exception {

        String paragraph = "This is a statement. This is another statement. Now is an abstract word for time, "
                + "that is always flying. And my email address is google@gmail.com.";

        InputStream is = getClass().getResourceAsStream("/models/en-sent.bin");
        SentenceModel model = new SentenceModel(is);

        SentenceDetectorME sdetector = new SentenceDetectorME(model);

        String sentences[] = sdetector.sentDetect(paragraph);
        assertThat(sentences).contains("This is a statement.",
                "This is another statement.",
                "Now is an abstract word for time, that is always flying.",
                "And my email address is google@gmail.com.");
    }
}
