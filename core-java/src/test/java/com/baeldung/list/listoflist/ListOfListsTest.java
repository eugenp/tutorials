package com.baeldung.list.listoflist;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class ListOfListsTest {

    private List<ArrayList<? extends Stationery>> listOfLists = new ArrayList<ArrayList<? extends Stationery>>();
    private ArrayList<Pen> penList = new ArrayList<>();
    private ArrayList<Pencil> pencilList = new ArrayList<>();
    private ArrayList<Rubber> rubberList = new ArrayList<>();

    @SuppressWarnings("unchecked")
    @Before
    public void init() {
        listOfLists.add(penList);
        listOfLists.add(pencilList);
        listOfLists.add(rubberList);

        ((ArrayList<Pen>) listOfLists.get(0)).add(new Pen("Pen 1"));
        ((ArrayList<Pencil>) listOfLists.get(1)).add(new Pencil("Pencil 1"));
        ((ArrayList<Rubber>) listOfLists.get(2)).add(new Rubber("Rubber 1"));
    }

    @Test
    public void givenListOfLists_thenCheckNames() {
        assertEquals("Pen 1", ((Pen) listOfLists.get(0)
            .get(0)).getName());
        assertEquals("Pencil 1", ((Pencil) listOfLists.get(1)
            .get(0)).getName());
        assertEquals("Rubber 1", ((Rubber) listOfLists.get(2)
            .get(0)).getName());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void givenListOfLists_whenRemovingElements_thenCheckNames() {
        ((ArrayList<Pencil>) listOfLists.get(1)).remove(0);        
        listOfLists.remove(1);
        assertEquals("Rubber 1", ((Rubber) listOfLists.get(1)
            .get(0)).getName());
        listOfLists.remove(0);
        assertEquals("Rubber 1", ((Rubber) listOfLists.get(0)
            .get(0)).getName());
    }
}
