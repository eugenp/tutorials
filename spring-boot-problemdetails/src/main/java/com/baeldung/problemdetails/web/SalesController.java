package com.baeldung.problemdetails.web;

import com.baeldung.problemdetails.model.InvalidOperandException;
import com.baeldung.problemdetails.model.OperationCode;
import com.baeldung.problemdetails.model.OperationRequest;
import com.baeldung.problemdetails.model.OperationResult;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("calculator")
public class CalculatorController {

  @PostMapping("/calculate")
  public ResponseEntity<OperationResult> calculate(
      @Validated @RequestBody OperationRequest operationRequest) {

    final OperationCode operationCode = operationRequest.operationCode();
    OperationResult operationResult = null;
    switch (operationCode) {
      case ADD -> {
        operationResult =
            new OperationResult(
                operationRequest.operationCode(),
                operationRequest.firstNumber(),
                operationRequest.secondNumber(),
                operationRequest.firstNumber() + operationRequest.secondNumber());
      }
      case SUBTRACT -> {
        operationResult =
            new OperationResult(
                operationRequest.operationCode(),
                operationRequest.firstNumber(),
                operationRequest.secondNumber(),
                operationRequest.firstNumber() - operationRequest.secondNumber());
      }
      case MULTIPLY -> {
        operationResult =
            new OperationResult(
                operationRequest.operationCode(),
                operationRequest.firstNumber(),
                operationRequest.secondNumber(),
                operationRequest.firstNumber() * operationRequest.secondNumber());
      }
      case DIVIDE -> {
        if (operationRequest.secondNumber() == 0) {
          throw new InvalidOperandException("Divide by zero error.");
        } else {
          operationResult =
              new OperationResult(
                  operationRequest.operationCode(),
                  operationRequest.firstNumber(),
                  operationRequest.secondNumber(),
                  operationRequest.firstNumber() / operationRequest.secondNumber());
        }
      }
    }
    return ResponseEntity.ok(operationResult);
  }
}
