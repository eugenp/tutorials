package com.baeldung.patterns.hexagonalarchitecture.people.evidence.adapter.web;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.baeldung.patterns.hexagonalarchitecture.people.evidence.application.domain.Person;
import com.baeldung.patterns.hexagonalarchitecture.people.evidence.application.port.primary.SearchPersonUseCase;

@Controller
public class PeopleEvidenceController {

    private SearchPersonUseCase searchPersonUseCase;

    public PeopleEvidenceController(SearchPersonUseCase searchPersonUseCase) {
        this.searchPersonUseCase = searchPersonUseCase;
    }

    @GetMapping("/")
    public String getPersonInfo(Model model, @RequestParam(value = "name") String name) {
        Optional<Person> personOpt = searchPersonUseCase.findByName(name);
        if (personOpt.isPresent()) {
            model.addAttribute("person", personOpt.get());
            return "person-view";
        } else {
            return "person-not-found-view";
        }
    }
}
