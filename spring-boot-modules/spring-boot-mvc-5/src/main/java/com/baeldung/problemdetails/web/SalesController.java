package com.baeldung.problemdetails.web;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.problemdetails.model.InvalidInputException;
import com.baeldung.problemdetails.model.OperationRequest;
import com.baeldung.problemdetails.model.OperationResult;

@RestController
@RequestMapping("sales")
public class SalesController {

    @PostMapping("/calculate")
    public ResponseEntity<OperationResult> calculate(@Validated @RequestBody OperationRequest operationRequest) {
        OperationResult operationResult;
        final Double discount = operationRequest.discount();
        if (discount == null) {
            operationResult = new OperationResult(operationRequest.basePrice(), null, operationRequest.basePrice());
        } else {
            if (discount.intValue() >= 100) {
                throw new InvalidInputException("Free sale is not allowed.");
            } else if (discount.intValue() > 30) {
                throw new IllegalArgumentException("Discount greater than 30% not allowed.");
            } else {
                operationResult = new OperationResult(operationRequest.basePrice(), discount, operationRequest.basePrice() * (100 - discount) / 100);
            }
        }
        return ResponseEntity.ok(operationResult);
    }
}
