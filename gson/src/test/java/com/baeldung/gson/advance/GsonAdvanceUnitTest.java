package com.baeldung.gson.advance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.baeldung.gson.entities.Dog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.baeldung.gson.entities.Animal;
import com.baeldung.gson.entities.Cow;
import com.baeldung.gson.entities.MyClass;
import com.baeldung.gson.serialization.AnimalDeserializer;
import org.junit.Test;

public class GsonAdvanceUnitTest {

    @Test
    public void givenListOfMyClass_whenSerializing_thenCorrect() {
        List<MyClass> list = Arrays.asList(new MyClass(1, "name1"), new MyClass(2, "name2"));

        Gson gson = new Gson();
        String jsonString = gson.toJson(list);
        String expectedString = "[{\"id\":1,\"name\":\"name1\"},{\"id\":2,\"name\":\"name2\"}]";

        assertEquals(expectedString, jsonString);
    }

    @Test(expected = ClassCastException.class)
    public void givenJsonString_whenIncorrectDeserializing_thenThrowClassCastException() {
        String inputString = "[{\"id\":1,\"name\":\"name1\"},{\"id\":2,\"name\":\"name2\"}]";

        Gson gson = new Gson();
        List<MyClass> outputList = gson.fromJson(inputString, ArrayList.class);

        assertEquals(1, outputList.get(0).getId());
    }

    @Test
    public void givenJsonString_whenDeserializing_thenReturnListOfMyClass() {
        String inputString = "[{\"id\":1,\"name\":\"name1\"},{\"id\":2,\"name\":\"name2\"}]";
        List<MyClass> inputList = Arrays.asList(new MyClass(1, "name1"), new MyClass(2, "name2"));

        Type listOfMyClassObject = new TypeToken<ArrayList<MyClass>>() {}.getType();

        Gson gson = new Gson();
        List<MyClass> outputList = gson.fromJson(inputString, listOfMyClassObject);

        assertEquals(inputList, outputList);
    }

    @Test
    public void givenPolymorphicList_whenSerializeWithTypeAdapter_thenCorrect() {
        String expectedString = "[{\"petName\":\"Milo\",\"type\":\"Dog\"},{\"breed\":\"Jersey\",\"type\":\"Cow\"}]";

        List<Animal> inList = new ArrayList<>();
        inList.add(new Dog());
        inList.add(new Cow());

        String jsonString = new Gson().toJson(inList);

        assertEquals(expectedString, jsonString);
    }

    @Test
    public void givenPolymorphicList_whenDeserializeWithTypeAdapter_thenCorrect() {
        String inputString = "[{\"petName\":\"Milo\",\"type\":\"Dog\"},{\"breed\":\"Jersey\",\"type\":\"Cow\"}]";

        AnimalDeserializer deserializer = new AnimalDeserializer("type");
        deserializer.registerBarnType("Dog", Dog.class);
        deserializer.registerBarnType("Cow", Cow.class);
        Gson gson = new GsonBuilder()
          .registerTypeAdapter(Animal.class, deserializer)
          .create();

        List<Animal> outList = gson.fromJson(inputString, new TypeToken<List<Animal>>(){}.getType());

        assertEquals(2, outList.size());
        assertTrue(outList.get(0) instanceof Dog);
        assertTrue(outList.get(1) instanceof Cow);
    }

    @Test
    public void givenPolymorphicList_whenSerializeWithRuntimeTypeAdapter_thenCorrect() {
        String expectedString = "[{\"petName\":\"Milo\",\"type\":\"Dog\"},{\"breed\":\"Jersey\",\"type\":\"Cow\"}]";

        List<Animal> inList = new ArrayList<>();
        inList.add(new Dog());
        inList.add(new Cow());
        String jsonString = new Gson().toJson(inList);

        assertEquals(expectedString, jsonString);
    }

    @Test
    public void givenPolymorphicList_whenDeserializeWithRuntimeTypeAdapter_thenCorrect() {
        String inputString = "[{\"petName\":\"Milo\",\"type\":\"Dog\"},{\"breed\":\"Jersey\",\"type\":\"Cow\"}]";

        Type listOfAnimals = new TypeToken<ArrayList<Animal>>() {}.getType();

        RuntimeTypeAdapterFactory<Animal> adapter = RuntimeTypeAdapterFactory.of(Animal.class, "type")
          .registerSubtype(Dog.class)
          .registerSubtype(Cow.class);

        Gson gson = new GsonBuilder().registerTypeAdapterFactory(adapter).create();

        List<Animal> outList = gson.fromJson(inputString, listOfAnimals);

        assertEquals(2, outList.size());
        assertTrue(outList.get(0) instanceof Dog);
        assertTrue(outList.get(1) instanceof Cow);
    }
}