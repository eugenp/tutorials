package com.baeldung.apache.opennlp;

import java.io.InputStream;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class TokenizerUnitTest {

    @Test
    public void givenEnglishModel_whenTokenize_thenTokensAreDetected() throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("/models/en-token.bin");
        TokenizerModel model = new TokenizerModel(inputStream);
        TokenizerME tokenizer = new TokenizerME(model);
        String[] tokens = tokenizer.tokenize("Baeldung is a Spring Resource.");
        assertThat(tokens).contains("Baeldung", "is", "a", "Spring", "Resource", ".");
    }
    
    @Test
    public void givenWhitespaceTokenizer_whenTokenize_thenTokensAreDetected() throws Exception {
        WhitespaceTokenizer tokenizer = WhitespaceTokenizer.INSTANCE;
        String[] tokens = tokenizer.tokenize("Baeldung is a Spring Resource.");
        assertThat(tokens).contains("Baeldung", "is", "a", "Spring", "Resource.");
    }
    
    @Test
    public void givenSimpleTokenizer_whenTokenize_thenTokensAreDetected() throws Exception {
        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        String[] tokens = tokenizer.tokenize("Baeldung is a Spring Resource.");
        assertThat(tokens).contains("Baeldung", "is", "a", "Spring", "Resource", ".");
    }

}
