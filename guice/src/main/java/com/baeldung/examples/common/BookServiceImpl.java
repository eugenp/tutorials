package com.baeldung.examples.common;

import java.util.Arrays;
import java.util.List;

public class BookServiceImpl implements BookService {

    public List<String> findBestSellerBooks() {
        return Arrays.asList("Harry Potter", "Lord of The Rings");
    }

}
