package com.baeldung.jpa.cloneentity;

import static jakarta.persistence.Persistence.createEntityManagerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.modelmapper.ModelMapper;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class ProductService {

    public Product manualClone(Product original) {
        Product clone = new Product();
        clone.setName(original.getName());
        clone.setCategory(original.getCategory());
        clone.setPrice(original.getPrice());
        return clone;
    }

    public Product manualDeepClone(Product original) {
        Product clone = new Product();
        clone.setName(original.getName());
        clone.setPrice(original.getPrice());
        if (original.getCategory() != null) {
            Category categoryClone = new Category();
            categoryClone.setName(original.getCategory()
                .getName());
            clone.setCategory(categoryClone);
        }
        return clone;
    }

    public Product cloneUsingSerialization(Product original) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(original);
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);

        Product clone = (Product) in.readObject();
        in.close();
        clone.setId(null);
        return clone; // Return deep clone
    }

    public Product cloneUsingBeanUtils(Product original) throws InvocationTargetException, IllegalAccessException {
        Product clone = new Product();
        BeanUtils.copyProperties(original, clone);
        clone.setId(null);
        return clone;
    }

    public Product cloneUsingModelMapper(Product original) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
            .setDeepCopyEnabled(true);
        Product clone = modelMapper.map(original, Product.class);
        clone.setId(null);
        return clone;
    }
}
