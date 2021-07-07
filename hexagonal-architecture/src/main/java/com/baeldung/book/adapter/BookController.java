package com.baeldung.book.adapter;

import com.baeldung.book.application.port.AddPage;
import com.baeldung.book.application.port.GetPages;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baeldung.book.application.domain.Page;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private AddPage addPage;
    private GetPages getPages;

    public BookController(AddPage addPage, GetPages getPages) {
        this.addPage = addPage;
        this.getPages = getPages;
    }

    @PostMapping("/{name}/pages")
    public ResponseEntity<Void> addPage(@PathVariable("name") String name, @RequestBody String text) {
        addPage.withText(name, text);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{name}/pages")
    public ResponseEntity<List<Page>> getPages(@PathVariable("name") String name) {
        return ResponseEntity.ok(getPages.fromBook(name));
    }

}
