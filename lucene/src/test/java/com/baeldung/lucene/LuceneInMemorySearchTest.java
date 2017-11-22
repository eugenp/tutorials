package com.baeldung.lucene;

import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Assert;
import org.junit.Test;

public class LuceneInMemorySearchTest {

    @Test
    public void givenSearchQueryWhenFetchedDocumentThenCorrect() {
        InMemoryLuceneIndex inMemoryLuceneIndex = new InMemoryLuceneIndex(new RAMDirectory(), new StandardAnalyzer());
        inMemoryLuceneIndex.indexDocument("Hello world", "Some hello world ");

        List<Document> documents = inMemoryLuceneIndex.searchIndex("body", "world");

        Assert.assertEquals("Hello world", documents.get(0).get("title"));
    }

}
