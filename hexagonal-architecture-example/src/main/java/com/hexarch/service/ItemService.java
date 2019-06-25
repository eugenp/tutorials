package com.hexarch.service;

import com.hexarch.domain.Item;
import com.hexarch.jpa.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService{

    @Autowired
    ItemRepository itemRepository;

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public Item retrieveItem(Integer itemId) {
        Item defalultItem = new Item(201,"SKU000", "000", 00.00);
        return itemRepository.findById(itemId).orElse(defalultItem); // returns default item if its not found
    }

    public Item updateItem(Item item) {

        return itemRepository.save(item);
    }

    public void deleteItem(Integer itemId) {

        itemRepository.deleteById(itemId);
    }

    public List<Item> retrieveAllItems() {
        List<Item> itemList = new ArrayList<>();
         itemRepository.findAll().forEach((item ->itemList.add(item) ));
        return itemList;
    }

    public boolean checkItemAvailable(Item item) {

        if(ObjectUtils.isEmpty(item) || item.getId()==null){
            return false;
        }else{
            return itemRepository.findById(item.getId()).isPresent();
        }
    }
}
