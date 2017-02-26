package com.baeldung.ejb.stateless.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

@Stateless(name = "ItemStatelessRemote")
public class ItemStateless implements ItemStatelessRemote {

    private List<String> itemList;

    public ItemStateless() {
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
