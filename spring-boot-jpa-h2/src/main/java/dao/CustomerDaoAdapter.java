package dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import core.CustomerModel;

@Component
public class CustomerDaoAdapter implements CustomerDaoPort {

	@PersistenceContext
    private EntityManager em;

	@Transactional
    public void create(String name) {
    	CustomerModel customer = new CustomerModel();
    	customer.setName(name);
        em.persist(customer);
    }
    
	@Override
	public CustomerModel getCustomer(String customerId) {
		return em.find(CustomerModel.class, customerId);
	}
}
