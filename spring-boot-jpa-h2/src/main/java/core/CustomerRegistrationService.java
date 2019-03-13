package core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dao.CustomerDaoPort;

@Component
public class CustomerRegistrationService {
	
	@Autowired
	private CustomerDaoPort customerDao;

	public void create(String name) {
		customerDao.create(name);
	}

	public CustomerModel view(String customerId) {
		return customerDao.getCustomer(customerId);
	}

}
