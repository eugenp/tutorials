package com.baeldung.mapinprotobuf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

import com.baeldung.generated.Food;

public class FoodDelivery {

    private static final Logger logger = Logger.getLogger(FoodDelivery.class.getName());
    private final String FILE_PATH = "src/main/resources/foodfile.bin";

    public FoodDelivery() {

    }

    public Food.FoodDelivery buildData() {
        Food.FoodDelivery.Builder foodData = Food.FoodDelivery.newBuilder();
        Food.Menu pizzaMenu = Food.Menu.newBuilder()
            .putItems("Margherita", 12.99f)
            .putItems("Pepperoni", 14.99f)
            .build();

        Food.Menu sushiMenu = Food.Menu.newBuilder()
            .putItems("Salmon Roll", 10.50f)
            .putItems("Tuna Roll", 12.33f)
            .build();

        foodData.putRestaurants("Pizza Place", pizzaMenu);
        foodData.putRestaurants("Sushi Place", sushiMenu);

        return foodData.build();
    }

    public void serializeToFile(Food.FoodDelivery delivery) {
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
            delivery.writeTo(fos);
            logger.info("Successfully wrote to the file.");
        } catch (IOException ioe) {
            logger.warning("Error serializing the Map or writing the file");
        }
    }

    public Food.FoodDelivery deserializeFromFile(Food.FoodDelivery delivery) {
        try (FileInputStream fis = new FileInputStream(FILE_PATH)) {
            return Food.FoodDelivery.parseFrom(fis);
        } catch (FileNotFoundException e) {
            logger.severe(String.format("File not found: %s location", FILE_PATH));
            return Food.FoodDelivery.newBuilder()
                .build();
        } catch (IOException e) {
            logger.warning(String.format("Error reading file: %s location", FILE_PATH));
            return Food.FoodDelivery.newBuilder()
                .build();
        }
    }

    public void displayRestaurants(Food.FoodDelivery delivery) {
        Map<String, Food.Menu> restaurants = delivery.getRestaurantsMap();
        for (Map.Entry<String, Food.Menu> restaurant : restaurants.entrySet()) {
            logger.info("Restaurant: " + restaurant.getKey());
            restaurant.getValue()
                .getItemsMap()
                .forEach((menuItem, price) -> logger.info(String.format(" - %s costs $ %f", menuItem, price)));
        }
    }
}
