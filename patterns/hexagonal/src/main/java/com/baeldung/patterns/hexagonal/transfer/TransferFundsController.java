package com.baeldung.patterns.hexagonal.transfer;

import com.baeldung.patterns.hexagonal.transfer.TransferFundsUseCase.TransferFundsCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TransferFundsController {

    private TransferFundsUseCase useCase;

    @PostMapping("/transfer")
    public ResponseEntity post(@RequestBody RequestModel model) {
        TransferFundsCommand command = TransferFundsCommand.builder()
            .amount(model.getAmount())
            .to(model.getTo())
            .from(model.getFrom())
            .build();

        useCase.transferFunds(command);

        return ResponseEntity.ok()
            .build();
    }

    @Getter
    public static class RequestModel {
        private String from;
        private String to;
        private int amount;

        // Getters omitted
    }
}
