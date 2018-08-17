package com.baeldung.apache.opennlp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.util.Span;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class NamedEntityRecognitionUnitTest {

    @Test
    public void givenEnglishPersonModel_whenNER_thenPersonsAreDetected() throws Exception {
        
        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        String[] tokens = tokenizer.tokenize("John is 26 years old. His best friend's name is Leonard. He has a sister named Penny.");
        
        InputStream inputStreamNameFinder = getClass().getResourceAsStream("/models/en-ner-person.bin");
        TokenNameFinderModel model = new TokenNameFinderModel(inputStreamNameFinder);
        NameFinderME nameFinderME = new NameFinderME(model);
        List<Span> spans = Arrays.asList(nameFinderME.find(tokens));
        assertThat(spans.toString()).isEqualTo("[[0..1) person, [13..14) person, [20..21) person]");
        List<String> names = new ArrayList<String>();
        int k = 0;
        for (Span s : spans) {
            names.add("");
            for (int index = s.getStart(); index < s.getEnd(); index++) {
                names.set(k, names.get(k) + tokens[index]);
            }
            k++;
        }
        assertThat(names).contains("John","Leonard","Penny");
    }

}
