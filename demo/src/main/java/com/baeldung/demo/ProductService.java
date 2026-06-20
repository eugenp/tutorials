package com.baeldung.demo;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ObjectProvider<ProductCatalogInitializer>
            initializerProvider;

    public ProductService(
            ObjectProvider<ProductCatalogInitializer>
                    initializerProvider) {

        this.initializerProvider = initializerProvider;
    }

    public void printStatus() {

        ProductCatalogInitializer initializer =
                initializerProvider.getObject();

        System.out.println(
                "Bean status: "
                        + initializer.getStatus()
        );
    }
}
