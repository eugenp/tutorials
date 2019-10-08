package com.baeldung.apache.opennlp;

import java.io.InputStream;
import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.SimpleTokenizer;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class LemmetizerUnitTest {

    @Test
    public void givenEnglishDictionary_whenLemmatize_thenLemmasAreDetected() throws Exception {

        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        String[] tokens = tokenizer.tokenize("John has a sister named Penny.");

        InputStream inputStreamPOSTagger = getClass().getResourceAsStream("/models/en-pos-maxent.bin");
        POSModel posModel = new POSModel(inputStreamPOSTagger);
        POSTaggerME posTagger = new POSTaggerME(posModel);
        String tags[] = posTagger.tag(tokens);
        InputStream dictLemmatizer = getClass().getResourceAsStream("/models/en-lemmatizer.dict");
        DictionaryLemmatizer lemmatizer = new DictionaryLemmatizer(dictLemmatizer);
        String[] lemmas = lemmatizer.lemmatize(tokens, tags);

        assertThat(lemmas).contains("O", "have", "a", "sister", "name", "O", "O");
    }
}
