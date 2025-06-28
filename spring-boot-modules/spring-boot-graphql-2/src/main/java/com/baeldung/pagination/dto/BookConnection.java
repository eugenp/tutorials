package com.baeldung.pagination.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class BookConnection {
    private final List<BookEdge> edges;
    private final PageInfo pageInfo;

    public BookConnection(List<BookEdge> edges, PageInfo pageInfo) {
        this.edges = edges;
        this.pageInfo = pageInfo;
    }
}