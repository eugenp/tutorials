package com.baeldung.ddd.hexagonal;

import static spark.Spark.get;

import com.baeldung.ddd.hexagonal.adapters.AlertServiceImpl;
import com.baeldung.ddd.hexagonal.adapters.InMemoryAlertRepository;
import com.baeldung.ddd.hexagonal.domain.Alert;
import com.baeldung.ddd.hexagonal.domain.Severity;
import com.baeldung.ddd.hexagonal.ports.AlertRepository;
import com.baeldung.ddd.hexagonal.ports.AlertService;
import com.fasterxml.jackson.databind.ObjectMapper;

import spark.Request;
import spark.Response;

public class HttpAlertController {
    private AlertRepository repository;
    private AlertService alertService;

    private ObjectMapper mapper = new ObjectMapper();

    public HttpAlertController() {
        repository = new InMemoryAlertRepository();
        alertService = new AlertServiceImpl(repository);
    }

    public String create(final Request req, final Response res) throws Exception {
        String summary = req.queryParams("summary");
        String desc = req.queryParams("description");
        String sev = req.queryParams("severity");
        Alert alert = alertService.create(summary, desc, Severity.valueOf(sev));
        String output = mapper.writeValueAsString(alert);
        return output;
    }

    public static void main(String[] args) throws Exception {
        HttpAlertController app = new HttpAlertController();
        get("/alert/create", (req, res) -> app.create(req, res));
    }

}
