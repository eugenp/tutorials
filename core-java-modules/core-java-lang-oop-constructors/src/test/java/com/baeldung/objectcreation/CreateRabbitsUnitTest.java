package com.baeldung.objectcreation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import com.baeldung.objectcreation.objects.ClonableRabbit;
import com.baeldung.objectcreation.objects.Rabbit;
import com.baeldung.objectcreation.objects.RabbitType;
import com.baeldung.objectcreation.objects.SerializableRabbit;
import com.baeldung.objectcreation.utils.CreateRabbits;

public class CreateRabbitsUnitTest {

    @Test
    public void whenUsingNewOperator_thenRabbitIsReturned() {
        assertTrue(CreateRabbits.createRabbitUsingNewOperator() instanceof Rabbit);
    }

    @Test
    public void whenUsingClassForName_thenRabbitIsReturned() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        assertTrue(CreateRabbits.createRabbitUsingClassForName() instanceof Rabbit);
    }
    
    @Test
    public void whenUsingClassNewInstance_thenRabbitIsReturned() throws InstantiationException, IllegalAccessException {
        assertTrue(CreateRabbits.createRabbitUsingClassNewInstance() instanceof Rabbit);
    }

    @Test
    public void whenUsingConstructorNewInstance_thenRabbitIsReturned() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException {
        assertTrue(CreateRabbits.createRabbitUsingConstructorNewInstance() instanceof Rabbit);
    }
    
    @Test
    public void givenClonableRabbit_whenUsingCloneMethod_thenClonedRabbitIsReturned() throws CloneNotSupportedException {
        //given
        ClonableRabbit originalRabbit = new ClonableRabbit("Peter");
        
        //when
        ClonableRabbit clonedRabbit = CreateRabbits.createRabbitUsingClone(originalRabbit);
        
        //then
        assertTrue(clonedRabbit instanceof ClonableRabbit);
        assertNotEquals(originalRabbit, clonedRabbit);
        assertEquals("Peter", clonedRabbit.getName());
    }
    
    @Test
    public void givenSerializedRabbit_whenDeserializing_thenNewRabbitIsReturned() throws IOException, ClassNotFoundException {        
        //given
        SerializableRabbit originalRabbit = new SerializableRabbit();
        originalRabbit.setName("Peter");

        File resourcesFolder = new File("src/test/resources");
        resourcesFolder.mkdirs(); //creates the directory in case it doesn't exist
        
        File file = new File(resourcesFolder, "rabbit.ser");
        
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);) {
            objectOutputStream.writeObject(originalRabbit);
        }
        
        //when
        SerializableRabbit newRabbit = CreateRabbits.createRabbitUsingDeserialization(file);
        
        //then
        assertTrue(newRabbit instanceof SerializableRabbit);
        assertNotEquals(originalRabbit, newRabbit);
        assertEquals("Peter", newRabbit.getName());
        
        //clean up
        file.delete();
    }
    
    @Test
    public void whenUsingSupplier_thenRabbitIsReturned() {
        assertTrue(CreateRabbits.createRabbitUsingSupplier() instanceof Rabbit);
    }
    
    @Test
    public void whenUsingArrays_thenRabbitArrayIsReturned() {
        assertTrue(CreateRabbits.createRabbitArray() instanceof Rabbit[]);
    }
    
    @Test
    public void whenUsingEnums_thenRabbitTypeIsReturned() {
        assertTrue(CreateRabbits.createRabbitTypeEnum() instanceof RabbitType);
    }
}
