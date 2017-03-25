package com.baeldung.solr.fulltext.search.service;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;

import com.baeldung.solr.fulltext.search.model.Item;

public class ItemSearchServiceImpl implements ItemSearchService {

    private final SolrClient solrClient;

    public ItemSearchServiceImpl(SolrClient solrClient) {
        this.solrClient = solrClient;
    }

    public void index(String id, String description, String category, float price) throws SolrServerException, IOException {
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", id);
        doc.addField("description", description);
        doc.addField("category", category);
        doc.addField("price", price);
        solrClient.add(doc);
        solrClient.commit();
    }

    public void indexBean(Item item) throws IOException, SolrServerException {
        solrClient.addBean(item);
        solrClient.commit();
    }

}
