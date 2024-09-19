package com.testing.mvc;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@RestController
public class SortingController {

    private final SortingService sortingService;

    public SortingController(SortingService sortingService){
        this.sortingService=sortingService;
    }

    @GetMapping
    public ResponseEntity<String> helloWorld(){
        return ResponseEntity.ok("Hello, World!");
    }

    @PostMapping
    public ResponseEntity<List<Integer>> sort(@RequestBody List<Integer> arr){
        return ResponseEntity.ok(sortingService.sortArray(arr));
    }
}
