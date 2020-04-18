package com.baeldung.patterns.hexagonalarchitecture.people.evidence.adapter.web;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baeldung.patterns.hexagonalarchitecture.people.evidence.adapter.persistence.InMemoryPersonRepository;
import com.baeldung.patterns.hexagonalarchitecture.people.evidence.application.domain.Person;
import com.baeldung.patterns.hexagonalarchitecture.people.evidence.application.port.primary.SearchPersonUseCase;

public class PeopleEvidenceController extends HttpServlet {

    private SearchPersonUseCase searchPersonUseCase;

    public PeopleEvidenceController() {
        searchPersonUseCase = new SearchPersonUseCase(new InMemoryPersonRepository());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        Optional<Person> personOpt = searchPersonUseCase.findByName(name);
        if (personOpt.isPresent()) {
            request.setAttribute("person", personOpt.get());
            forward(request, response, "person-view");
        } else {
            forward(request, response, "person-not-found");
        }
    }

    protected void forward(HttpServletRequest request, HttpServletResponse response, String target) throws ServletException, IOException {
        target = String.format("/WEB-INF/jsp/%s.jsp", target);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(target);
        dispatcher.forward(request, response);
    }
}
