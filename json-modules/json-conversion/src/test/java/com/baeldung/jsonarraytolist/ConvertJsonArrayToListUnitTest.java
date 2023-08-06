package com.baeldung.jsonarraytolist;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import entities.Product;

public class ConvertJsonArrayToListUnitTest {

    private static ObjectMapper objectMapper;
    private static List<Product> productList;
    private ConvertJsonArrayToList obj;

    @Before
    public void setup() {
        obj = new ConvertJsonArrayToList();
        productList = getProducts();
        objectMapper = new ObjectMapper();
    }

    private List<Product> getProducts() {
        List<Product> productList = new ArrayList<>();
        Product prod1 = new Product(1, "Icecream", "Sweet and cold");
        Product prod2 = new Product(2, "Apple", "Red and sweet");
        Product prod3 = new Product(3, "Carrot", "Good for eyes");
        productList.add(prod1);
        productList.add(prod2);
        productList.add(prod3);
        return productList;
    }

    @Test
    public void whenUsingGsonLibrary_thenCompareTwoProducts() {
        Gson gson = new Gson();
        String jsonArray = gson.toJson(productList);
        List<Product> arrList = obj.convertJsonArrayUsingGsonLibrary(jsonArray);
        Assert.assertEquals(productList.get(0).getId(), arrList.get(0).getId());
        Assert.assertEquals(productList.get(1).getDescription(), arrList.get(1).getDescription());
        Assert.assertEquals(productList.get(2).getName(), arrList.get(2).getName());
    }

    @Test
    public void whenUsingJacksonLibrary_thenCompareTwoProducts() throws JsonProcessingException {
        String jsonArray = objectMapper.writeValueAsString(productList);
        List<Product> arrList = obj.convertJsonArrayUsingJacksonLibrary(jsonArray);

        Assert.assertEquals(productList.get(0).getId(), arrList.get(0).getId());
        Assert.assertEquals(productList.get(1).getDescription(), arrList.get(1).getDescription());
        Assert.assertEquals(productList.get(2).getName(), arrList.get(2).getName());
    }

}
