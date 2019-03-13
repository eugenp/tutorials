package view;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import core.CustomerModel;

@RestController("/")
public interface CustomerDetailsViewPort {

	@RequestMapping(value = "/register/{name}", method = RequestMethod.POST)
	public void register(@PathVariable String name);
	
	@RequestMapping(value = "/find/{customerId}", method = RequestMethod.GET)
	public CustomerModel view(@PathVariable String customerId);

}
