package com.spr.model;

import com.spr.repository.ShopRepository;
import com.spr.service.ShopService;
import com.spr.service.ShopServiceImpl;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.util.List;

/**
 * Created by lihongjie on 2/28/17.
 */
public class App {
    public static void main(String[] args) {

        ShopRepository shopRepository = new SimpleJpaRepository<Shop.class>();
        List<Shop> shops = shopService.findAll();
        System.out.println(shops.size());
    }
}
