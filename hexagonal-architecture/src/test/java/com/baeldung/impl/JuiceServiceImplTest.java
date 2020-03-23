package com.baeldung.impl;

import com.baeldung.domain.Juice;
import com.baeldung.port.outbound.JuiceDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class JuiceServiceImplTest {

    @Mock
    private JuiceDao juiceDao;

    private JuiceServiceImpl juiceService;

    @BeforeEach
    public void setUp() {
        juiceService = new JuiceServiceImpl(juiceDao);
    }

    @Test
    public void whenAddingJuice_thenItWillBeAddedToDB() {
        String kiwiJuiceName = "kiwi";
        String[] kiwiJuiceIngredients = new String[]{"water", "kiwi", "sugar"};
        Juice kiwiTestJuice = new Juice(kiwiJuiceName, kiwiJuiceIngredients);

        Mockito.doNothing().when(juiceDao).addJuice(kiwiTestJuice);

        juiceService.createJuice(kiwiTestJuice);

        Mockito.verify(juiceDao, Mockito.times(1)).addJuice(kiwiTestJuice);
    }

    @Test
    public void whenGettingJuiceByName_thenItWillBeRetrievedFromDB() {
        String kiwiJuiceName = "kiwi";
        String[] kiwiJuiceIngredients = new String[]{"water", "kiwi", "sugar"};
        Juice kiwiTestJuice = new Juice(kiwiJuiceName, kiwiJuiceIngredients);

        Mockito.when(juiceDao.getJuice(kiwiJuiceName)).thenReturn(kiwiTestJuice);

        Assertions.assertEquals(kiwiTestJuice, juiceService.retrieveJuice(kiwiJuiceName));
    }

    @Test
    public void whenAllJuicesAreRetrieved_thenCorrectListIsReturned() {
        String kiwiJuiceName = "kiwi";
        String[] kiwiJuiceIngredients = new String[]{"water", "kiwi", "sugar"};
        Juice kiwiTestJuice = new Juice(kiwiJuiceName, kiwiJuiceIngredients);

        String appleJuiceName = "apple";
        String[] appleJuiceIngredients = new String[]{"water", "apple", "sugar"};
        Juice appleTestJuice = new Juice(appleJuiceName, appleJuiceIngredients);

        List<Juice> expectedJuiceList = new ArrayList<>(Arrays.asList(kiwiTestJuice, appleTestJuice));

        Mockito.when(juiceDao.getAllJuice()).thenReturn(expectedJuiceList);

        Assertions.assertEquals(expectedJuiceList, juiceService.listJuices());

    }

    @Test
    public void whenJuicesAreRetrieved_thenImmutableListIsReturned() {
        String kiwiJuiceName = "kiwi";
        String[] kiwiJuiceIngredients = new String[]{"water", "kiwi", "sugar"};
        Juice kiwiTestJuice = new Juice(kiwiJuiceName, kiwiJuiceIngredients);

        String appleJuiceName = "apple";
        String[] appleJuiceIngredients = new String[]{"water", "apple", "sugar"};
        Juice appleTestJuice = new Juice(appleJuiceName, appleJuiceIngredients);

        List<Juice> expectedJuiceList = new ArrayList<>(Arrays.asList(kiwiTestJuice, appleTestJuice));

        Mockito.when(juiceDao.getAllJuice()).thenReturn(expectedJuiceList);

        List<Juice> actualList = juiceService.listJuices();

        Assertions.assertThrows(UnsupportedOperationException.class, () -> actualList.add(appleTestJuice));

    }
}
