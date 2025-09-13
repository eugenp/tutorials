package com.baeldung.partitionkey;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    SalesRepository salesRepository;

    @GetMapping
    public ResponseEntity<List<Sales>> getAllPartition() {
        return ResponseEntity.ok()
          .body(salesRepository.findAll());
    }

    @GetMapping("add")
    public ResponseEntity testPartition() {
        return ResponseEntity.ok()
          .body(salesRepository.save(new Sales(104L, LocalDate.of(2024, 02, 01), BigDecimal.valueOf(Double.parseDouble("8476.34d")))));
    }
}
