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

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class NameAnalysisController {

    private NameAnalysisService nameAnalysisService;
    private SessionNameRequestFactory sessionNameRequestFactory;
    private JakartaServletWebApplication webApp;

    @Autowired
    public NameAnalysisController(NameAnalysisService nameAnalysisService, SessionNameRequestFactory sessionNameRequestFactory, ServletContext servletContext) {
        super();
        this.nameAnalysisService = nameAnalysisService;
        this.sessionNameRequestFactory = sessionNameRequestFactory;
        this.webApp = JakartaServletWebApplication.buildApplication(servletContext);
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
    public String performNameAnalysis(final NameRequest nameRequest, final BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        IWebSession webSession = getIWebSession(request, response);
        performNameRequest(nameRequest, webSession);
        return "name-analysis";
    }

    @RequestMapping(value = "/name-analysis/clear")
    public String clearNameAnalysis(HttpServletRequest request, HttpServletResponse response) {
        IWebSession webSession = getIWebSession(request, response);
        clearAnalysis(webSession);
        return "redirect:/name-analysis";
    }

    @RequestMapping(value = "/name-analysis/remove-history-request", params = { "id" })
    public String removeRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            IWebSession webSession = getIWebSession(request, response);
            final Integer rowId = Integer.valueOf(request.getParameter("id"));
            removeRequest(rowId, webSession);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/name-analysis";
    }

    private void removeRequest(Integer rowId, IWebSession webSession) {
        if (rowId != null) {
            List<SessionNameRequest> requests = getRequestsFromSession(webSession);
            if (requests != null) {
                requests.remove(rowId.intValue());
            }
        }
    }

    private void performNameRequest(final NameRequest nameRequest, IWebSession webSession) {
        try {
            CompletableFuture<NameAnalysisEntity> nameAnalysis = this.nameAnalysisService.searchForName(nameRequest);
            NameAnalysisEntity nameAnalysisEntity = nameAnalysis.get(30, TimeUnit.SECONDS);
            sessionRegisterRequest(nameRequest, webSession);
            sessionRegisterAnalysis(nameAnalysisEntity, webSession);
            sessionClearAnalysisError(webSession);
        } catch (Exception e) {
            e.printStackTrace();
            sessionSetAnalysisError(nameRequest, webSession);
        }
    }

    private void sessionClearAnalysisError(IWebSession webSession) {
        webSession.removeAttribute("analysisError");
    }

    private void sessionSetAnalysisError(NameRequest nameRequest, IWebSession webSession) {
        webSession.setAttributeValue("analysisError", nameRequest);
    }

    private void clearAnalysis(IWebSession webSession) {
        webSession.removeAttribute("lastAnalysis");
    }

    private void sessionRegisterAnalysis(NameAnalysisEntity analysis, IWebSession webSession) {
        webSession.setAttributeValue("lastAnalysis", analysis);
    }

    private void sessionRegisterRequest(NameRequest nameRequest, IWebSession webSession) {
        webSession.setAttributeValue("lastRequest", nameRequest);

        SessionNameRequest sessionNameRequest = sessionNameRequestFactory.getInstance(nameRequest);
        List<SessionNameRequest> requests = getRequestsFromSession(webSession);
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

    private IWebSession getIWebSession(HttpServletRequest request, HttpServletResponse response) {
        IServletWebExchange exchange = webApp.buildExchange(request, response);
        return exchange == null ? null : exchange.getSession();
    }
}
