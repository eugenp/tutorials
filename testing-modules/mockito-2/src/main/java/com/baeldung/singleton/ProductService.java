package com.baeldung.singleton;

public class ProductService {

    private final ProductDAO productDAO;
    private final CacheManager cacheManager;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
        this.cacheManager = CacheManager.getInstance();
    }

    public ProductService(ProductDAO productDAO, CacheManager cacheManager) {
        this.productDAO = productDAO;
        this.cacheManager = cacheManager;
    }

    public Product getProduct(String productName) {
        Product product = cacheManager.getValue(productName, Product.class);
        if (product == null) {
            product = productDAO.getProduct(productName);
        }

        return product;
    }
}
