package com.baeldung.ejb.stateful.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;

@Stateful(name = "ItemStatefulRemote")
public class ItemStateful implements ItemStatefulRemote {

    private List<String> itemList;

    public ItemStateful() {
        itemList = new ArrayList<String>();
    }

    @Override
    public void addItem(String itemName) {
        itemList.add(itemName);
    }

    @Override
    public List<String> getItemList() {
        return itemList;
    }

}
