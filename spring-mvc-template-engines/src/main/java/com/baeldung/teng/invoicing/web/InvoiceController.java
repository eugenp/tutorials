package com.baeldung.teng.invoicing.web;

import com.baeldung.teng.invoicing.domain.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static java.util.Objects.requireNonNull;

@Controller
public class InvoiceController {

    private final InvoiceRepository invoices;

    @Autowired
    public InvoiceController(InvoiceRepository invoices) { this.invoices = requireNonNull(invoices); }

    @RequestMapping(value = {"/invoice/{id}"})
    public ModelAndView invoice(@PathVariable(required = false) String id) { return invoice("jsp", id); }

    @RequestMapping(value = {"/{engine}/invoice/{id}"})
    public ModelAndView invoice(@PathVariable(required = false) String engine,
                                @PathVariable(required = false) String id) {
        if (engine == null || (engine = engine.trim().toLowerCase()).length() == 0) {
            engine = "jsp";
        }
        if (id == null || (id = id.trim().toLowerCase()).length() == 0) {
            id = "0000";
        }
        return new ModelAndView("invoice." + engine, "invoice", invoices.getInvoice(id));
    }
}
