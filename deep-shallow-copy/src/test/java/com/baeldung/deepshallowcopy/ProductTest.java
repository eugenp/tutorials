package com.baeldung.deepshallowcopy;
import com.baeldung.deepshallowcopy.models.Category;
import com.baeldung.deepshallowcopy.models.Product;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
    @Test
    public void givenProductAndCategory_whenShallowCopy_thenCategoryIsShared() {
        Category category = new Category("Electronics");
        Product originalProduct = new Product("TV", category);
        Product copiedProduct = originalProduct.shallowCopy();
        assertEquals("Electronics", copiedProduct.getCategory().getName());
        assertEquals("TV", copiedProduct.getName());

        //Change the category
        copiedProduct.getCategory().setName("Appliances");
        assertEquals("Appliances", originalProduct.getCategory().getName());
        assertEquals("Appliances", copiedProduct.getCategory().getName());
    }

    @Test
    public void givenProductAndCategory_whenDeepCopy_thenCategoryIsNotShared() {
        Category category = new Category("Electronics");
        Product originalProduct = new Product("TV", category);
        Product copiedProduct = originalProduct.deepCopy();
        assertEquals("Electronics", copiedProduct.getCategory().getName());
        assertEquals("TV", copiedProduct.getName());

        //Change the category
        copiedProduct.getCategory().setName("Appliances");
        assertEquals("Appliances", copiedProduct.getCategory().getName());
        assertEquals("Electronics", originalProduct.getCategory().getName());
    }
}