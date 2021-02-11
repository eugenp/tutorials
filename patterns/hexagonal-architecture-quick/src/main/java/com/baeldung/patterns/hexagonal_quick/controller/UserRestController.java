package com.baeldung.patterns.hexagonal_quick.controller;

import org.springframework.web.bind.annotation.*;

import com.baeldung.patterns.hexagonal_quick.controller.model.ApiBorrowRequest;
import com.baeldung.patterns.hexagonal_quick.controller.model.ApiBorrowResponse;
import com.baeldung.patterns.hexagonal_quick.controller.model.ApiReturnRequest;
import com.baeldung.patterns.hexagonal_quick.controller.model.ApiReturnResponse;
import com.baeldung.patterns.hexagonal_quick.domain.BorrowRecord;
import com.baeldung.patterns.hexagonal_quick.domain.ReturnRecord;
import com.baeldung.patterns.hexagonal_quick.port.BorrowInputPort;

@RestController
public class UserRestController {
    private final BorrowInputPort borrowInputPort;

    public UserRestController(BorrowInputPort borrowInputPort) {
        this.borrowInputPort = borrowInputPort;
    }

    @PostMapping("/borrow")
    public ApiBorrowResponse borrowBook(@RequestBody ApiBorrowRequest borrowRequest) {
        BorrowRecord borrowRecord = null;
        for (String isbn : borrowRequest.getIsbns()) {
            borrowRecord = borrowInputPort.borrowBook(isbn, borrowRequest.getUsername());
        }
        return ApiBorrowResponse.createFrom(borrowRecord);
    }

    @PostMapping("/borrow/{referenceId}/return")
    public ApiReturnResponse returnBorrowedBook(
            @PathVariable String referenceId, @RequestBody ApiReturnRequest returnRequest) {
        ReturnRecord returnRecord = borrowInputPort.returnBook(referenceId, returnRequest.getIsbn());
        return ApiReturnResponse.createFrom(returnRecord);
    }
}
