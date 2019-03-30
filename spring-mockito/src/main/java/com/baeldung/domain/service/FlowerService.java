package com.baeldung.domain.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class FlowerService {

    private List<String> flowers = Arrays.asList("Poppy", "Ageratum", "Carnation", "Diascia", "Lantana");

    public String analize(String name) {
        if(flowers.contains(name)) {
            return "flower";
        }
        return null;
    }

    public boolean isABigFlower(String name, int petals) {
        if(flowers.contains(name)) {
            if(petals > 10) {
                return true;
            }
        }
        return false;
    }
}
