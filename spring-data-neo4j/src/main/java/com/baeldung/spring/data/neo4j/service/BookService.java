package com.baeldung.spring.data.neo4j.service;


import com.baeldung.spring.data.neo4j.model.Book;
import com.baeldung.spring.data.neo4j.repostory.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    private Map<String, Object> toD3Format(final Iterator<Map<String, Object>> result) {
        List<Map<String,Object>> nodes = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> rels= new ArrayList<Map<String,Object>>();
        int i=0;
        while (result.hasNext()) {
            Map<String, Object> row = result.next();
            nodes.add(map("title",row.get("book"),"label","book"));
            int target=i;
            i++;
            for (Object name : (Collection) row.get("cast")) {
                Map<String, Object> actor = map("title", name,"label","actor");
                int source = nodes.indexOf(actor);
                if (source == -1) {
                    nodes.add(actor);
                    source = i++;
                }
                rels.add(map("source",source,"target",target));
            }
        }
        return map("nodes", nodes, "links", rels);
    }

    private Map<String, Object> map(final String key1, final Object value1, final String key2, final Object value2) {
        Map<String, Object> result = new HashMap<String,Object>(2);
        result.put(key1,value1);
        result.put(key2,value2);
        return result;
    }

    public Map<String, Object> graph(final int limit) {
        Iterator<Map<String, Object>> result = bookRepository.graph(limit).iterator();
        return toD3Format(result);
    }

    public Book save(final Book book){
       return bookRepository.save(book);
    }

    public Book findBookById(final Long id){
        return bookRepository.findOne(id);
    }

    public void deleteAllInGraph(){
        bookRepository.deleteAll();
    }
}