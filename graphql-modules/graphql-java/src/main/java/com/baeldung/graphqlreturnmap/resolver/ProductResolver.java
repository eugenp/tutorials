package com.baeldung.graphqlreturnmap.resolver;

import com.baeldung.graphqlreturnmap.entity.Product;
import com.baeldung.graphqlreturnmap.model.AttributeKeyValueModel;
import com.coxautodev.graphql.tools.GraphQLResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedList;
import java.util.List;

public class ProductResolver implements GraphQLResolver<Product> {
    public ProductResolver(){
    }

    public List<AttributeKeyValueModel> getAttribute_list(Product product){
        List<AttributeKeyValueModel> attributeModelList = new LinkedList<>();
        product.getAttributes().forEach((key, val) -> attributeModelList.add(new AttributeKeyValueModel(key, val)));
        return attributeModelList;
    }

    public String getAttribute_string(Product product){
        try {
            return new ObjectMapper().writeValueAsString(product.getAttributes());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
