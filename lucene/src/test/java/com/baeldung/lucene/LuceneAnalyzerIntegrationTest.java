package com.baeldung.lucene;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Test;

public class LuceneAnalyzerIntegrationTest {

    private static final String SAMPLE_TEXT = "This is baeldung.com Lucene Analyzers test";
    private static final String FIELD_NAME = "sampleName";

    @Test
    public void whenUseStandardAnalyzer_thenAnalyzed() throws IOException {
        List<String> result = analyze(SAMPLE_TEXT, new StandardAnalyzer());

        assertThat(result, contains("baeldung.com", "lucene", "analyzers", "test"));
    }

    @Test
    public void whenUseStopAnalyzer_thenAnalyzed() throws IOException {
        List<String> result = analyze(SAMPLE_TEXT, new StopAnalyzer());

        assertThat(result, contains("baeldung", "com", "lucene", "analyzers", "test"));
    }

    @Test
    public void whenUseSimpleAnalyzer_thenAnalyzed() throws IOException {
        List<String> result = analyze(SAMPLE_TEXT, new SimpleAnalyzer());

        assertThat(result, contains("this", "is", "baeldung", "com", "lucene", "analyzers", "test"));
    }

    @Test
    public void whenUseWhiteSpaceAnalyzer_thenAnalyzed() throws IOException {
        List<String> result = analyze(SAMPLE_TEXT, new WhitespaceAnalyzer());

        assertThat(result, contains("This", "is", "baeldung.com", "Lucene", "Analyzers", "test"));
    }

    @Test
    public void whenUseKeywordAnalyzer_thenAnalyzed() throws IOException {
        List<String> result = analyze(SAMPLE_TEXT, new KeywordAnalyzer());

        assertThat(result, contains("This is baeldung.com Lucene Analyzers test"));
    }

    @Test
    public void whenUseEnglishAnalyzer_thenAnalyzed() throws IOException {
        List<String> result = analyze(SAMPLE_TEXT, new EnglishAnalyzer());

        assertThat(result, contains("baeldung.com", "lucen", "analyz", "test"));
    }

    @Test
    public void whenUseCustomAnalyzerBuilder_thenAnalyzed() throws IOException {
        Analyzer analyzer = CustomAnalyzer.builder()
            .withTokenizer("standard")
            .addTokenFilter("lowercase")
            .addTokenFilter("stop")
            .addTokenFilter("porterstem")
            .addTokenFilter("capitalization")
            .build();
        List<String> result = analyze(SAMPLE_TEXT, analyzer);

        assertThat(result, contains("Baeldung.com", "Lucen", "Analyz", "Test"));
    }

    @Test
    public void whenUseCustomAnalyzer_thenAnalyzed() throws IOException {
        List<String> result = analyze(SAMPLE_TEXT, new MyCustomAnalyzer());

        assertThat(result, contains("Baeldung.com", "Lucen", "Analyz", "Test"));
    }

    // ================= usage example
    
    @Test
    public void givenTermQuery_whenUseCustomAnalyzer_thenCorrect() {
        InMemoryLuceneIndex luceneIndex = new InMemoryLuceneIndex(new RAMDirectory(), new MyCustomAnalyzer());
        luceneIndex.indexDocument("introduction", "introduction to lucene");
        luceneIndex.indexDocument("analyzers", "guide to lucene analyzers");
        Query query = new TermQuery(new Term("body", "Introduct"));

        List<Document> documents = luceneIndex.searchIndex(query);
        assertEquals(1, documents.size());
    }
    
    @Test
    public void givenTermQuery_whenUsePerFieldAnalyzerWrapper_thenCorrect() {
        Map<String,Analyzer> analyzerMap = new HashMap<>();
        analyzerMap.put("title", new MyCustomAnalyzer());
        analyzerMap.put("body", new EnglishAnalyzer());

        PerFieldAnalyzerWrapper wrapper =
          new PerFieldAnalyzerWrapper(new StandardAnalyzer(), analyzerMap);
        InMemoryLuceneIndex luceneIndex = new InMemoryLuceneIndex(new RAMDirectory(), wrapper);
        luceneIndex.indexDocument("introduction", "introduction to lucene");
        luceneIndex.indexDocument("analyzers", "guide to lucene analyzers");
        
        Query query = new TermQuery(new Term("body", "introduct"));
        List<Document> documents = luceneIndex.searchIndex(query);
        assertEquals(1, documents.size());
        
        query = new TermQuery(new Term("title", "Introduct"));

        documents = luceneIndex.searchIndex(query);
        assertEquals(1, documents.size());
    }

    // ===================================================================

    public List<String> analyze(String text, Analyzer analyzer) throws IOException {
        List<String> result = new ArrayList<String>();
        TokenStream tokenStream = analyzer.tokenStream(FIELD_NAME, text);
        CharTermAttribute attr = tokenStream.addAttribute(CharTermAttribute.class);
        tokenStream.reset();
        while (tokenStream.incrementToken()) {
            result.add(attr.toString());
        }
        return result;
    }

}
