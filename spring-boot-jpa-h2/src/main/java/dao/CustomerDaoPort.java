package dao;

import org.springframework.stereotype.Component;

import core.CustomerModel;

@Component
public interface CustomerDaoPort {
	void create(String name);

	CustomerModel getCustomer(String id);
}
