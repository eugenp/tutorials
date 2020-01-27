package com.baeldung.adapter;
import com.baeldung.port.CommandHandler;
import io.vertx.core.json.JsonObject;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.baeldung.domain.Account;
import com.baeldung.domain.BlacklistedException;
import com.baeldung.domain.Customer;
import com.baeldung.domain.CustomerAccountService;
import com.baeldung.domain.InvalidSizeIdentityException;

public class CreateCustomerCommandHandler implements CommandHandler {

    private Customer customer;
    private CustomerAccountService customerAccountService;

    public CreateCustomerCommandHandler(JsonObject jo) {
        String firstName = jo.getString("firstname");
        String surname = jo.getString("surname");
        String identity = jo.getString("identity");
        String email = jo.getString("email");

        Account customerAccount = new Account();
        customerAccount.setAccountNumber(identity + "1234567F");
        customerAccount.setCustomerId(identity);
        customerAccount.setDateOpened(LocalDate.now());
        customer = Customer.builder().email(email).firstName(firstName).surnameName(surname)
                .account(customerAccount).build();
        customerAccountService = new CustomerAccountService();

    }

    @Override
    public String execute() {
        try {
            return customerAccountService.createAccount(customer);
        } catch (BlacklistedException ex) {
            Logger.getLogger(CreateCustomerCommandHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidSizeIdentityException ex) {
            Logger.getLogger(CreateCustomerCommandHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Unable to create Account";
    }

}
