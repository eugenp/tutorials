package com.baeldung.solr.fulltext.search.service;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;

import com.baeldung.solr.fulltext.search.model.Item;

public interface ItemSearchService {

    public void index(String id, String description, String category, float price) throws SolrServerException, IOException;

    public void indexBean(Item item) throws IOException, SolrServerException;

}
