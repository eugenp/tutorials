package com.baeldung.enums;

import com.baeldung.enums.Pizza.PizzaStatusEnum;
import org.junit.Test;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class PizzaUnitTest {

    @Test
    public void givenPizaOrder_whenReady_thenDeliverable() {
        Pizza testPz = new Pizza();
        testPz.setStatus(Pizza.PizzaStatusEnum.READY);
        assertTrue(testPz.isDeliverable());
    }

    @Test
    public void givenPizaOrders_whenRetrievingUnDeliveredPzs_thenCorrectlyRetrieved() {
        List<Pizza> pzList = new ArrayList<>();
        Pizza pz1 = new Pizza();
        pz1.setStatus(Pizza.PizzaStatusEnum.DELIVERED);

        Pizza pz2 = new Pizza();
        pz2.setStatus(Pizza.PizzaStatusEnum.ORDERED);

        Pizza pz3 = new Pizza();
        pz3.setStatus(Pizza.PizzaStatusEnum.ORDERED);

        Pizza pz4 = new Pizza();
        pz4.setStatus(Pizza.PizzaStatusEnum.READY);

        pzList.add(pz1);
        pzList.add(pz2);
        pzList.add(pz3);
        pzList.add(pz4);

        List<Pizza> undeliveredPzs = Pizza.getAllUndeliveredPizzas(pzList);
        assertTrue(undeliveredPzs.size() == 3);
    }

    @Test
    public void givenPizaOrders_whenGroupByStatusCalled_thenCorrectlyGrouped() {

        List<Pizza> pzList = new ArrayList<>();
        Pizza pz1 = new Pizza();
        pz1.setStatus(Pizza.PizzaStatusEnum.DELIVERED);

        Pizza pz2 = new Pizza();
        pz2.setStatus(Pizza.PizzaStatusEnum.ORDERED);

        Pizza pz3 = new Pizza();
        pz3.setStatus(Pizza.PizzaStatusEnum.ORDERED);

        Pizza pz4 = new Pizza();
        pz4.setStatus(Pizza.PizzaStatusEnum.READY);

        pzList.add(pz1);
        pzList.add(pz2);
        pzList.add(pz3);
        pzList.add(pz4);

        EnumMap<Pizza.PizzaStatusEnum, List<Pizza>> map = Pizza.groupPizzaByStatus(pzList);
        assertTrue(map.get(Pizza.PizzaStatusEnum.DELIVERED).size() == 1);
        assertTrue(map.get(Pizza.PizzaStatusEnum.ORDERED).size() == 2);
        assertTrue(map.get(Pizza.PizzaStatusEnum.READY).size() == 1);
    }

    @Test
    public void givenPizaOrder_whenDelivered_thenPizzaGetsDeliveredAndStatusChanges() {
        Pizza pz = new Pizza();
        pz.setStatus(Pizza.PizzaStatusEnum.READY);
        pz.deliver();
        assertTrue(pz.getStatus() == Pizza.PizzaStatusEnum.DELIVERED);
    }   
}
