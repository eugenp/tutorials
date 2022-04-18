package com.baeldung.collections.iterable;

import java.util.Iterator;
import java.util.List;

class ProductIterable implements Iterable<Product> {

    List<Product> productList;

    public ProductIterable(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public Iterator<Product> iterator() {
        return productList.iterator();
    }
}
