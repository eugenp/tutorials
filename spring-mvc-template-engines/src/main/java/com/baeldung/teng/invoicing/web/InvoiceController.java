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

    @RequestMapping({"/invoice/{id}"})
    public ModelAndView invoice(@PathVariable String id) {
        return new ModelAndView("invoice", "invoice",
                                invoices.getInvoice(id == null || id.trim().length() == 0 ? "0000" : id));
    }

    @RequestMapping({"/invoice"})
    public ModelAndView invoice() { return invoice("0000"); }
}
