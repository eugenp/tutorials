package view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import core.CustomerModel;
import core.CustomerRegistrationService;

@RestController("/")
public class CustomerDetailsViewAdapter implements CustomerDetailsViewPort {
	
	@Autowired
	private CustomerRegistrationService customerRegService;

	@Override
	public void register(@PathVariable String name) {
		System.out.println(name+" registered");
		customerRegService.create(name);
	}

	@Override
	public CustomerModel view(@PathVariable String customerId) {
		CustomerModel employee = customerRegService.view(customerId);
		return employee;
	}
}
