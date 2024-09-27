package com.testing.mvc;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SortingService {

    public List<Integer> sortArray(List<Integer> arr){
        return arr.stream().sorted().toList();
    }
}
