package com.baeldung.hexagonal;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class FlatFileAdapter implements DataPort {

    private CoffeeRecepieData parseFile() {
        CoffeeRecepieData coffeeRecepieData = null;
        try {
            File file = new File(getClass().getClassLoader()
                .getResource("com/baeldung/hexagonal/flatFile.xml")
                .getFile());

            JAXBContext jaxbContext = JAXBContext.newInstance(CoffeeRecepieData.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            coffeeRecepieData = (CoffeeRecepieData) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return coffeeRecepieData;
    }

    @Override
    public CoffeeRecepie getCoffeeRecepieById(Integer id) {
        CoffeeRecepieData coffeeRecepieData = parseFile();
        CoffeeRecepie coffeeRecepie = null;

        if (coffeeRecepieData != null) {
            coffeeRecepie = coffeeRecepieData.getCoffeeRecepies()
                .stream()
                .filter(item -> item.getId()
                    .equals(id))
                .findAny()
                .orElse(null);

        }

        return coffeeRecepie;
    }

}
