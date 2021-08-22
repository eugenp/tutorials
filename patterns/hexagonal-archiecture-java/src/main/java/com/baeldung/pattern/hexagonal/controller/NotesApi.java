package com.baeldung.pattern.hexagonal.controller;

import com.baeldung.pattern.hexagonal.domain.dto.NoteDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notes")
public interface NotesApi {

    @GetMapping(path = "/{noteId}")
    NoteDTO getNote(@PathVariable("noteId") String noteId);

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    NoteDTO addNote(@RequestBody NoteDTO noteDTO);

}
