package com.baeldung.teng.invoicing.web;

import com.baeldung.teng.invoicing.domain.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;

import java.util.List;

import static java.util.Objects.requireNonNull;

@Controller
public class InvoiceController {

    private final InvoiceRepository invoices;

    @Autowired
    public InvoiceController(InvoiceRepository invoices, List<ViewResolver> viewResolvers) {
        this.invoices = requireNonNull(invoices);
    }

    @RequestMapping(value = {"/invoice/{id}"})
    public ModelAndView invoice(@PathVariable(required = false) String id) { return invoice("jsp", id); }

    @RequestMapping(value = {"/{engine}/invoice/{id}"})
    public ModelAndView invoice(@PathVariable(required = false) String engine,
                                @PathVariable(required = false) String id) {

        return new ModelAndView("invoice." + engine(engine), "invoice",
                                invoices.getInvoice(id == null || (id = id.trim()).length() == 0 ? "0000" : id));
    }

    private String engine(String engine) {
        if (engine == null || (engine = engine.trim().toLowerCase()).length() == 0) {
            return "jsp";
        }
        if (engine.equals("groovy")) {
            return "tpl"; // allow groovy as well as tpl as engine identifier for Groovy Markup
        }
        return engine;
    }
}
