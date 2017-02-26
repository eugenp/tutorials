package com.baeldung.ejb.stateful.beans;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface ItemStatefulRemote {

    void addItem(String itemName);

    List<String> getItemList();

}
