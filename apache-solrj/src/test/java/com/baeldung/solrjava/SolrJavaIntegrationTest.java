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
    public void whenAdd_thenVerifyAddedByQueryOnId() throws SolrServerException, IOException {

        SolrQuery query = new SolrQuery();
        query.set("q", "id:123456");
        QueryResponse response = null;

        response = solrJavaIntegration.getSolrClient().query(query);

        SolrDocumentList docList = response.getResults();
        assertEquals(docList.getNumFound(), 1);

        for (SolrDocument doc : docList) {
            assertEquals((String) doc.getFieldValue("name"), "Kenmore Dishwasher");
            assertEquals((Double) doc.getFieldValue("price"), (Double) 599.99);
        }
    }

    @Test
    public void whenAdd_thenVerifyAddedByQueryOnPrice() throws SolrServerException, IOException {

        SolrQuery query = new SolrQuery();
        query.set("q", "price:599.99");
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
    public void whenAdd_thenVerifyAddedByQuery() throws SolrServerException, IOException {

        SolrDocument doc = solrJavaIntegration.getSolrClient().getById("123456");
        assertEquals((String) doc.getFieldValue("name"), "Kenmore Dishwasher");
        assertEquals((Double) doc.getFieldValue("price"), (Double) 599.99);
    }

    @Test
    public void whenAddBean_thenVerifyAddedByQuery() throws SolrServerException, IOException {

        ProductBean pBean = new ProductBean("888", "Apple iPhone 6s", "299.99");
        solrJavaIntegration.addProductBean(pBean);

        SolrDocument doc = solrJavaIntegration.getSolrClient().getById("888");
        assertEquals((String) doc.getFieldValue("name"), "Apple iPhone 6s");
        assertEquals((Double) doc.getFieldValue("price"), (Double) 299.99);
    }

    @Test
    public void whenDeleteById_thenVerifyDeleted() throws SolrServerException, IOException {

        solrJavaIntegration.deleteSolrDocumentById("123456");

        SolrQuery query = new SolrQuery();
        query.set("q", "id:123456");
        QueryResponse response = solrJavaIntegration.getSolrClient().query(query);

        SolrDocumentList docList = response.getResults();
        assertEquals(docList.getNumFound(), 0);
    }

    @Test
    public void whenDeleteByQuery_thenVerifyDeleted() throws SolrServerException, IOException {

        solrJavaIntegration.deleteSolrDocumentByQuery("name:Kenmore Dishwasher");

        SolrQuery query = new SolrQuery();
        query.set("q", "id:123456");
        QueryResponse response = null;

        response = solrJavaIntegration.getSolrClient().query(query);

        SolrDocumentList docList = response.getResults();
        assertEquals(docList.getNumFound(), 0);
    }
}
