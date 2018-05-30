package com.baeldung.list.listoflist;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ListOfListsUnitTest {

    private List<List<? extends Stationery>> listOfLists = new ArrayList<>();
    private List<Pen> penList = new ArrayList<>();
    private List<Pencil> pencilList = new ArrayList<>();
    private List<Rubber> rubberList = new ArrayList<>();

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
        assertEquals("Pen 1", ((Pen) listOfLists.get(0).get(0)).getName());
        assertEquals("Pencil 1", ((Pencil) listOfLists.get(1).get(0)).getName());
        assertEquals("Rubber 1", ((Rubber) listOfLists.get(2).get(0)).getName());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void givenListOfLists_whenRemovingElements_thenCheckNames() {

        ((ArrayList<Pencil>) listOfLists.get(1)).remove(0);
        listOfLists.remove(1);
        assertEquals("Rubber 1", ((Rubber) listOfLists.get(1).get(0)).getName());
        listOfLists.remove(0);
        assertEquals("Rubber 1", ((Rubber) listOfLists.get(0).get(0)).getName());
    }

    @Test
    public void givenThreeList_whenCombineIntoOneList_thenCheckList() {
        ArrayList<Pen> pens = new ArrayList<>();
        pens.add(new Pen("Pen 1"));
        pens.add(new Pen("Pen 2"));
        ArrayList<Pencil> pencils = new ArrayList<>();
        pencils.add(new Pencil("Pencil 1"));
        pencils.add(new Pencil("Pencil 2"));
        ArrayList<Rubber> rubbers = new ArrayList<>();
        rubbers.add(new Rubber("Rubber 1"));
        rubbers.add(new Rubber("Rubber 2"));

        List<ArrayList<? extends Stationery>> list = new ArrayList<ArrayList<? extends Stationery>>();
        list.add(pens);
        list.add(pencils);
        list.add(rubbers);

        assertEquals("Pen 1", ((Pen) list.get(0).get(0)).getName());
        assertEquals("Pencil 1", ((Pencil) list.get(1).get(0)).getName());
        assertEquals("Rubber 1", ((Rubber) list.get(2).get(0)).getName());
    }
}
