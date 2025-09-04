package com.baeldung.customstatefulvalidation.controllers;

import com.baeldung.customstatefulvalidation.model.PurchaseOrderItem;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurchaseOrderController {

    @PostMapping("/api/purchasing/")
    public ResponseEntity<String> createPurchaseOrder(@Valid @RequestBody PurchaseOrderItem item) {
        // start processing this purchase order and tell the caller we've accepted it

        return ResponseEntity.accepted().build();
    }
}
