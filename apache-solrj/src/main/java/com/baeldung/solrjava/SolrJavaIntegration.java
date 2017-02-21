package com.baeldung.solrjava;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.common.SolrInputDocument;

public class SolrJavaIntegration {

    private HttpSolrClient solrClient;

    public SolrJavaIntegration(String clientUrl) {

        solrClient = new HttpSolrClient.Builder(clientUrl).build();
        solrClient.setParser(new XMLResponseParser());
    }

    public void addSolrDocument(String documentId, String itemName, String itemPrice) throws SolrServerException, IOException {

        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", documentId);
        document.addField("name", itemName);
        document.addField("price", itemPrice);
        solrClient.add(document);
        solrClient.commit();
    }

    public void deleteSolrDocument(String documentId) throws SolrServerException, IOException {

        solrClient.deleteById(documentId);
        solrClient.commit();
    }

    protected HttpSolrClient getSolrClient() {
        return solrClient;
    }

    protected void setSolrClient(HttpSolrClient solrClient) {
        this.solrClient = solrClient;
    }
}
