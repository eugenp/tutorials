package com.github.lihongjie.controller;

import com.github.lihongjie.domain.Billing;
import com.github.lihongjie.model.BillingForm;
import com.github.lihongjie.service.BillingService;
import com.github.lihongjie.utils.PagedResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/billings")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAllBillings(@PageableDefault Pageable pageable) {

        Page<Billing> billings = billingService.findAll(pageable);
        PagedResource<Billing> response =
                new PagedResource<Billing>(billings.getContent(), billings.getSize(), billings.getNumber(),
                        billings.getTotalElements(), billings.getTotalPages());
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createBilling(@RequestBody BillingForm billingForm) {

        Billing billing = billingService.create(billingForm.toBillingRequest());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @RequestMapping(value = "/{billingId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteBilling(@PathVariable Long billingId) {

        billingService.deleteByBillingId(billingId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @RequestMapping(value = "/{billingId}", method = RequestMethod.PATCH)
    public ResponseEntity updateBilling(@RequestBody BillingForm billingForm, @PathVariable Long billingId) {

        billingService.updateBilling(billingForm.toBillingRequest(), billingId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @RequestMapping(value = "/test1")
    @ResponseBody
    public String t() {
        return "he";
    }

}
