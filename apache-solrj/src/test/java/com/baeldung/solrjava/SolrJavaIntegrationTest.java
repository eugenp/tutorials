package com.baeldung.solrjava;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Before;
import org.junit.Test;

public class SolrJavaIntegrationTest {

    private SolrJavaIntegration solrJavaIntegration;

    @Before
    public void setUp() throws Exception {

        solrJavaIntegration = new SolrJavaIntegration("http://localhost:8983/solr/bigboxstore");
        solrJavaIntegration.addSolrDocument("123456", "Kenmore Dishwasher", "599.99");
    }

    @Test
    public void whenAdd_thenVerifyAdded() throws SolrServerException, IOException {

        SolrQuery query = new SolrQuery();
        query.set("q", "id:123456");
        QueryResponse response = null;

        response = solrJavaIntegration.getSolrClient().query(query);

        SolrDocumentList docList = response.getResults();
        assertEquals(docList.getNumFound(), 1);

        for (SolrDocument doc : docList) {
            assertEquals((String) doc.getFieldValue("id"), "123456");
            assertEquals((Double) doc.getFieldValue("price"), (Double) 599.99);
        }
    }

    @Test
    public void whenDelete_thenVerifyDeleted() throws SolrServerException, IOException {

        solrJavaIntegration.deleteSolrDocument("123456");

        SolrQuery query = new SolrQuery();
        query.set("q", "id:123456");
        QueryResponse response = null;

        response = solrJavaIntegration.getSolrClient().query(query);

        SolrDocumentList docList = response.getResults();
        assertEquals(docList.getNumFound(), 0);
    }
}
