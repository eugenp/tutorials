package com.baeldung.pagination.dto;

import lombok.Getter;

@Getter
public class PageInfo {
    private final boolean hasNextPage;
    private final String endCursor;

    public PageInfo(boolean hasNextPage, String endCursor) {
        this.hasNextPage = hasNextPage;
        this.endCursor = endCursor;
    }
}