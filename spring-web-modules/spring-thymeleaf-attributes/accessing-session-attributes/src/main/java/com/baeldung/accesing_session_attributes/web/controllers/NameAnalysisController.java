package com.baeldung.accesing_session_attributes.web.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.web.IWebSession;
import org.thymeleaf.web.servlet.IServletWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import com.baeldung.accesing_session_attributes.business.NameAnalysisService;
import com.baeldung.accesing_session_attributes.business.beans.NameRequest;
import com.baeldung.accesing_session_attributes.business.entities.NameAnalysisEntity;
import com.baeldung.accesing_session_attributes.web.beans.SessionNameRequest;
import com.baeldung.accesing_session_attributes.web.factories.SessionNameRequestFactory;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class NameAnalysisController {

    private NameAnalysisService nameAnalysisService;
    private HttpServletRequest request;
    private SessionNameRequestFactory sessionNameRequestFactory;
    private HttpServletResponse response;
    private JakartaServletWebApplication jakartaServletWebApplication;

    @Autowired
    public NameAnalysisController(JakartaServletWebApplication jakartaServletWebApplication, HttpServletRequest request, HttpServletResponse response, NameAnalysisService nameAnalysisService, SessionNameRequestFactory sessionNameRequestFactory) {
        super();
        this.jakartaServletWebApplication = jakartaServletWebApplication;
        this.request = request;
        this.response = response;
        this.nameAnalysisService = nameAnalysisService;
        this.sessionNameRequestFactory = sessionNameRequestFactory;
    }

    @ModelAttribute("nameRequest")
    public NameRequest nameRequest() {
        return nameAnalysisService.getLastNameRequest();
    }

    @RequestMapping({ "/", "/name-analysis" })
    public String showNameAnalysis() {
        return "name-analysis";
    }

    @RequestMapping(value = "/name-analysis", params = { "search" })
    public String performNameAnalysis(final NameRequest nameRequest, final BindingResult bindingResult) {
        performNameRequest(nameRequest);
        return "name-analysis";
    }

    @RequestMapping(value = "/name-analysis/clear")
    public String clearNameAnalysis() {
        clearAnalysis();
        return "redirect:/name-analysis";
    }

    @RequestMapping(value = "/name-analysis/remove-history-request", params = { "id" })
    public String removeRequest() {
        try {
            final Integer rowId = Integer.valueOf(request.getParameter("id"));
            removeRequest(rowId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/name-analysis";
    }

    private void removeRequest(Integer rowId) {
        IWebSession session = getIWebSession();
        Object requests = session.getAttributeValue("requests");
        if (rowId != null && requests != null && (requests instanceof List)) {
            ((List<SessionNameRequest>) requests).remove(rowId.intValue());
        }
    }

    private void performNameRequest(final NameRequest nameRequest) {
        try {
            CompletableFuture<NameAnalysisEntity> nameAnalysis = this.nameAnalysisService.searchForName(nameRequest);
            NameAnalysisEntity nameAnalysisEntity = nameAnalysis.get(30, TimeUnit.SECONDS);
            sessionRegisterRequest(nameRequest);
            sessionRegisterAnalysis(nameAnalysisEntity);
            sessionClearAnalysisError();
        } catch (Exception e) {
            e.printStackTrace();
            sessionSetAnalysisError(nameRequest);
        }
    }

    private void sessionClearAnalysisError() {
        IWebSession session = getIWebSession();
        session.setAttributeValue("analysisError", null);
    }

    private void sessionSetAnalysisError(NameRequest nameRequest) {
        IWebSession session = getIWebSession();
        session.setAttributeValue("analysisError", nameRequest);
    }

    private void clearAnalysis() {
        IWebSession session = getIWebSession();
        session.setAttributeValue("lastAnalysis", null);
    }

    private void sessionRegisterAnalysis(NameAnalysisEntity analysis) {
        IWebSession session = getIWebSession();
        session.setAttributeValue("lastAnalysis", analysis);
    }

    private void sessionRegisterRequest(NameRequest nameRequest) {
        IWebSession session = getIWebSession();
        session.setAttributeValue("lastRequest", nameRequest);

        SessionNameRequest sessionNameRequest = sessionNameRequestFactory.getInstance(nameRequest);
        List<SessionNameRequest> requests = getRequestsFromSession(session);
        requests.add(0, sessionNameRequest);
    }

    private List<SessionNameRequest> getRequestsFromSession(IWebSession session) {
        Object requests = session.getAttributeValue("requests");
        if (requests == null || !(requests instanceof List)) {
            List<SessionNameRequest> sessionNameRequests = new ArrayList<>();
            session.setAttributeValue("requests", sessionNameRequests);
            requests = sessionNameRequests;
        }
        return (List<SessionNameRequest>) requests;
    }

    private IWebSession getIWebSession() {
        IServletWebExchange webExchange = this.jakartaServletWebApplication.buildExchange(request, response);
        return webExchange.getSession();
    }
}
