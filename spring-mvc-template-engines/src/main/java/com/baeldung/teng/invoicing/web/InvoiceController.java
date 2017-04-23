package com.baeldung.teng.invoicing.web;

import com.baeldung.teng.invoicing.domain.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static java.util.Objects.requireNonNull;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class InvoiceController {

    private InvoiceRepository invoices;

    @Autowired
    public InvoiceController(InvoiceRepository invoices) { this.invoices = requireNonNull(invoices); }

    @RequestMapping(value = "/invoice/{id}", method = GET)
    public ModelAndView invoice(@PathVariable String id) {
        return new ModelAndView("invoice", "invoice",
                                invoices.getInvoice(id == null || id.trim().length() == 0 ? "0000" : id));
    }
}
