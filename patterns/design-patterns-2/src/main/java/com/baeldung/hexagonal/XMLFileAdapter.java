package com.baeldung.hexagonal;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class XMLFileAdapter implements DataPort {

    private CoffeeRecipeData parseXMLFile() {
        CoffeeRecipeData coffeeRecipeData = null;
        try {
            File file = new File(getClass().getClassLoader()
                .getResource("com/baeldung/hexagonal/flatFile.xml")
                .getFile());

            JAXBContext jaxbContext = JAXBContext.newInstance(CoffeeRecipeData.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            coffeeRecipeData = (CoffeeRecipeData) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return coffeeRecipeData;
    }

    @Override
    public CoffeeRecipe getCoffeeRecipeById(Integer id) {
        CoffeeRecipeData coffeeRecipeData = parseXMLFile();
        CoffeeRecipe coffeeRecipe = null;

        if (coffeeRecipeData != null) {
            coffeeRecipe = coffeeRecipeData.getCoffeeRecipes()
                .stream()
                .filter(item -> item.getId()
                    .equals(id))
                .findAny()
                .orElse(null);

        }

        return coffeeRecipe;
    }

}
