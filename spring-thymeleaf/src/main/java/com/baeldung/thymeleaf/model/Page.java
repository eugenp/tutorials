package com.baeldung.thymeleaf.model;

import java.util.Collections;
import java.util.List;

public class Page<T> {

    private List<T> list;

    private int pageSize = 0;

    private int currentPage = 0;

    private int totalPages = 0;

    public Page(List<T> list, int pageSize, int currentPage) {

        if (list.isEmpty()) {
            this.list = list;
        }

        if (pageSize <= 0 || pageSize > list.size() || currentPage <= 0) {
            throw new IllegalArgumentException("invalid page size or current page!");
        }

        this.pageSize = pageSize;

        this.currentPage = currentPage;

        if (list.size() % pageSize == 0) {
            this.totalPages = list.size() / pageSize;
        } else {
            this.totalPages = list.size() / pageSize + 1;
        }

        int startItem = (currentPage - 1) * pageSize;
        if (list.size() < startItem) {
            this.list = Collections.emptyList();
        }

        int toIndex = Math.min(startItem + pageSize, list.size());
        this.list = list.subList(startItem, toIndex);
    }

    public List<T> getList() {
        return list;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

}
