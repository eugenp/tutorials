package com.github.lihongjie.utils;

import java.util.List;

public class PagedResource <T> {

    private Page page;

    private List<T> content;

    public PagedResource(List<T> content, int size, int number, long totalElements, int totalPages) {

        this.page = new Page(size, number, totalElements, totalPages);
        this.content = content;
    }

    class Page {

        private int size;

        private int number;

        private long totalElements;

        private int totalPages;

        public Page(int size, int number, long totalElements, int totalPages) {

            this.size = size;
            this.number = number;
            this.totalElements = totalElements;
            this.totalPages = totalPages;
        }

        public int getSize() {

            return size;
        }

        public void setSize(int size) {

            this.size = size;
        }

        public int getNumber() {

            return number;
        }

        public void setNumber(int number) {

            this.number = number;
        }

        public long getTotalElements() {

            return totalElements;
        }

        public void setTotalElements(long totalElements) {

            this.totalElements = totalElements;
        }

        public int getTotalPages() {

            return totalPages;
        }

        public void setTotalPages(int totalPages) {

            this.totalPages = totalPages;
        }

        public boolean isHasPrevious() {

            return number > 0;
        }

        public boolean isHasNext() {

            return number + 1 < totalPages;
        }

    }

    public Page getPage() {

        return page;
    }

    public List<T> getContent() {

        return content;
    }

    public void setContent(List<T> content) {

        this.content = content;
    }
}
