package com.baeldung.hexagonalarchitecture.domain;

import com.baeldung.hexagonalarchitecture.ports.IDiscountProvider;
import com.baeldung.hexagonalarchitecture.ports.IProductProvider;
import com.baeldung.hexagonalarchitecture.ports.IPromotionProvider;

import java.util.List;
import java.util.Optional;

/**
 * IDiscountSale is the port of the StoreManager for consumers that need an order price
 * IProductProvider is the port of the StoreManager to obtain products
 * IPromotionProvider is the port of the StoreManager to obtain promotions
 */
public class StoreManager implements IDiscountProvider {

    private IProductProvider productProvider;

    private IPromotionProvider promotionProvider;

    public StoreManager(IProductProvider inventory, IPromotionProvider promotions) {
        this.productProvider = inventory;
        this.promotionProvider = promotions;
    }

    public double getOrderPrice(List<Integer> productIds) {
        if (productIds == null || productIds.isEmpty()) {
            return 0.0;
        }

        return productIds.stream().map(productId -> {

            Optional<Product> maybeProduct = productProvider
                    .getProducts()
                    .stream()
                    .filter(product -> product.getId() == productId)
                    .findFirst();

            Product product = maybeProduct
                    .orElseThrow(() -> new IllegalStateException("No inventory for product " + productId));

            double discount = promotionProvider
                    .getPromotions()
                    .stream()
                    .filter(y -> y.getProductId() == product.getId())
                    .map(Promotion::getDiscount)
                    .findAny()
                    .orElse(0.0); //No discount available

            return product.getPrice() - (product.getPrice() * (discount / 100));

        }).reduce(0.00, Double::sum);
    }
}
