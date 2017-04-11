package com.baeldung;

import com.baeldung.ChargeRequest.Currency;
import com.stripe.exception.StripeException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Log
@Controller
public class ChargeController {

    @Autowired
    StripeService paymentsService;

    @RequestMapping(value = "/charge", method = POST)
    public String charge(ChargeRequest chargeRequest, Model model) throws StripeException {
        chargeRequest.setDescription("Example charge");
        chargeRequest.setCurrency(Currency.EUR);
        paymentsService.charge(chargeRequest);
        return "result";
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "result";
    }
}
