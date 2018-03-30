package com.baeldung.apache.opennlp;

import java.io.FileInputStream;
import java.io.InputStream;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.SimpleTokenizer;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class POSTaggerTest {

    @Test
    public void givenSentence_whenPOSTagging_thenGetTags() throws Exception {

        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        String[] tokens = tokenizer.tokenize("John has a sister named Penny.");

        InputStream inputStreamPOSTagger = new FileInputStream("src/main/resources/models/en-pos-maxent.bin");
        POSModel posModel = new POSModel(inputStreamPOSTagger);
        POSTaggerME posTagger = new POSTaggerME(posModel);
        String tags[] = posTagger.tag(tokens);
        assertThat(tags).contains("NNP", "VBZ", "DT", "NN", "VBN", "NNP", ".");
    }
}
