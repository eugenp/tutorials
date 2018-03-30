package com.baeldung.apache.opennlp;

import java.io.FileInputStream;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class TokenizerTest {

    @Test
    public void givenString_whenTokenize_thenGetTokens() throws Exception {
         FileInputStream fileInputStream = new FileInputStream("src/main/resources/models/en-token.bin");
        TokenizerModel model = new TokenizerModel(fileInputStream);
        TokenizerME tokenizer = new TokenizerME(model);
        String[] tokens = tokenizer.tokenize("Baeldung is a Spring Resource.");
        assertThat(tokens).contains("Baeldung", "is", "a", "Spring", "Resource", ".");
    }
    
    @Test
    public void givenString_whenWhitespaceTokenizer_thenGetTokens() throws Exception {
        WhitespaceTokenizer tokenizer = WhitespaceTokenizer.INSTANCE;
        String[] tokens = tokenizer.tokenize("Baeldung is a Spring Resource.");
        assertThat(tokens).contains("Baeldung", "is", "a", "Spring", "Resource.");
    }
    
    @Test
    public void givenString_whenSimpleTokenizer_thenGetTokens() throws Exception {
        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        String[] tokens = tokenizer.tokenize("Baeldung is a Spring Resource.");
        assertThat(tokens).contains("Baeldung", "is", "a", "Spring", "Resource", ".");
    }

}
