package com.baeldung.ddd.hexagonal;

import static spark.Spark.get;

import com.baeldung.ddd.hexagonal.adapters.AlertServiceImpl;
import com.baeldung.ddd.hexagonal.adapters.InMemoryAlertRepository;

public class AlertManagerApp {
    public static void main(String[] args) throws Exception {
        HttpAlertController app = new HttpAlertController(new AlertServiceImpl(new InMemoryAlertRepository()));
        get("/alert/create", (req, res) -> app.create(req, res));
    }
}
