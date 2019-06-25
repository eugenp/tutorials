package com.hexarch.controller;

import com.hexarch.domain.Item;
import com.hexarch.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.hexarch.constants.ItemConstants.ALL_ITEMS;
import static com.hexarch.constants.ItemConstants.SINGLE_ITEM;
import static com.hexarch.constants.ItemConstants.ITEM_URL;

@RestController
public class ItemController {

    @Autowired
    ItemService itemService;

    @PostMapping(ITEM_URL)
    public ResponseEntity<Item> saveItem(@RequestBody @Valid Item item){
        return  ResponseEntity.status(HttpStatus.CREATED).body(itemService.saveItem(item));
    }


    @GetMapping(ALL_ITEMS)
    public ResponseEntity<List<Item>> retrieveAllItems(){
        return  ResponseEntity.status(HttpStatus.OK).body(itemService.retrieveAllItems());
    }


    @GetMapping(SINGLE_ITEM)
    public ResponseEntity<Item> retrieveItem(@PathVariable("id") Integer itemId){
        return  ResponseEntity.ok(itemService.retrieveItem(itemId));
    }

    @PutMapping(ITEM_URL)
    public ResponseEntity<?> updateItem(@RequestBody Item item){

        if(itemService.checkItemAvailable(item)){
            return  ResponseEntity.ok(itemService.updateItem(item));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item Not Found");
        }
    }



    @DeleteMapping(SINGLE_ITEM)
    public ResponseEntity<?> deleteItem(@PathVariable("id") Integer itemId){
        try{
            itemService.deleteItem(itemId);
        }catch (EmptyResultDataAccessException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item Not Found");
        }
        return  ResponseEntity.ok().body("Item Successfully Deleted");
    }
}
