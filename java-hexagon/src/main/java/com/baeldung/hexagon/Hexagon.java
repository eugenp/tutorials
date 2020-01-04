
package com.baeldung.hexagon;

import java.util.List;
import com.baeldung.hexagon.adapters.ProductAdapter;
import com.baeldung.hexagon.domain.Product;
import com.baeldung.hexagon.domain.ProductSummary;
import com.baeldung.hexagon.factory.AdapterFactory;

public class Hexagon {

    public static void main(String[] args) {
        List<Product> products = AdapterFactory.getProducts();
        ProductAdapter prodAdapter = new ProductAdapter();
        products.add(prodAdapter.addProduct("Watch", "Titan Watch", 10));
        products.add(prodAdapter.addProduct("Watch", "Lamex Watch", 20));
        products.add(prodAdapter.addProduct("Shoes", "Nike", 30));
        products.add(prodAdapter.addProduct("Shoes", "Addidas", 15));
        ProductSummary viewSummary = prodAdapter.viewSummary("Shoes");
        System.out.println(viewSummary.toString());
    }

}
