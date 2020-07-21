package com.architecture.hexagonal.component;

import com.architecture.hexagonal.properties.ProductPathProperties;
import org.springframework.stereotype.Service;

@Service
public class ProductPathConfiguration {

    private ProductPathProperties properties;

    public ProductPathConfiguration(ProductPathProperties properties) {
        this.properties = properties;
    }

    public String getProductPath(int productId) {
        return buildPath(productId);
    }

    private String buildPath(int productId) {
        return properties.getBasePath()
                       + "/"
                       + productId + properties.getFilePostfix()
                       + "."
                       + properties.getFileExtension();
    }

}
