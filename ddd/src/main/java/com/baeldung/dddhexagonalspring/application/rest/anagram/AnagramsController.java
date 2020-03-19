package com.baeldung.dddhexagonalspring.application.rest.anagram;

import com.baeldung.dddhexagonalspring.domain.service.anagram.IAnagramServicePort;

import java.util.Collections;
import java.util.Map;

import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class AnagramsController {
    private static final String NON_ALPHA_MESSAGE = "Contains non-alpha character.Only alphabets are allowed [A-Za-z].";

    @Autowired
    IAnagramServicePort service;

    @RequestMapping(value = "/anagrams/{string1}/{string2}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map areAnagrams(@PathVariable @Pattern(regexp = "^[A-Za-z]+$", message = NON_ALPHA_MESSAGE) String string1, @PathVariable @Pattern(regexp = "^[A-Za-z]+$", message = NON_ALPHA_MESSAGE) String string2) {
        return Collections.singletonMap("areAnagrams", service.isAnagram(string1, string2));
    }

}
