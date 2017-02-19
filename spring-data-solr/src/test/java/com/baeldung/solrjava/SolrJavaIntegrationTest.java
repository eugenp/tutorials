package com.baeldung.solrjava;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;

public class SolrJavaIntegrationTest {

    private HttpSolrClient solr;
    private SolrInputDocument document;

    @Before
    public void setUp() throws Exception {

        solr = new HttpSolrClient("http://localhost:8983/solr/bigboxstore");
        solr.setParser(new XMLResponseParser());
        
        document = new SolrInputDocument();
        document.addField("id", "123456");
        document.addField("name", "Kenmore Dishwasher");
        document.addField("price", "599.99");
        solr.add(document);
        solr.commit();
    }

    @Test
    public void whenAdd_thenVerifyAdded() throws SolrServerException, IOException {

        SolrQuery query = new SolrQuery();
        query.set("q", "id:123456");
        QueryResponse response = null;

        response = solr.query(query);

        SolrDocumentList docList = response.getResults();
        assertEquals(docList.getNumFound(), 1);

        for (SolrDocument doc : docList) {
            assertEquals((String) doc.getFieldValue("id"), "123456");
            assertEquals((Double) doc.getFieldValue("price"), (Double) 599.99);
        }
    }

    @Test
    public void whenDelete_thenVerifyDeleted() throws SolrServerException, IOException {

        solr.deleteById("123456");
        solr.commit();

        SolrQuery query = new SolrQuery();
        query.set("q", "id:123456");
        QueryResponse response = null;

        response = solr.query(query);

        SolrDocumentList docList = response.getResults();
        assertEquals(docList.getNumFound(), 0);
    }
}
