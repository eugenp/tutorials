package com.baeldung;

import com.baeldung.ChargeRequest.Currency;
import com.stripe.exception.StripeException;
import java.util.logging.Level;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Log
@Controller
public class ChargeController {

    @Autowired
    StripeService paymentsService;

    @RequestMapping(value = "/charge", method = POST)
    public String charge(ChargeRequest chargeRequest, Model model) {
        try {
            chargeRequest.setDescription("Example charge");
            chargeRequest.setCurrency(Currency.EUR);
            log.log(Level.INFO, "Executing {0}", chargeRequest);
            paymentsService.charge(chargeRequest);
        } catch (StripeException ex) {
            log.severe(ex.getMessage());
            model.addAttribute("message", ex.getMessage());
        }
        return "result";
    }
}
