package com.baeldung.ejb.stateless.beans;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface ItemStatelessRemote {

    void addItem(String itemName);

    List<String> getItemList();
}
