package com.baeldung.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.exception.RecordNotFoundException;
import com.baeldung.pojo.BorrowingRecord;
import com.baeldung.service.LibraryService;

@RestController
@RequestMapping("/library")
public class LibraryRestController {

    @Autowired
    private LibraryService useCase;

    @GetMapping("/borrow")
    public BorrowingRecord borrowBook(@RequestParam String uid, @RequestParam String bookNo) throws RecordNotFoundException {
        return useCase.borrowBook(uid, bookNo);
    }

    @GetMapping("/return")
    public BorrowingRecord returnBook(@RequestParam long recNo) throws RecordNotFoundException {
        return useCase.returnBook(recNo);
    }

}
