package com.sp.dataaccess;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.sp.domain.Call;
import com.sp.domain.Customer;


public class CustomerDaoJpaImpl implements CustomerDao {
	
	@PersistenceContext
	private EntityManager em;
	@Override
	public void create(Customer customer) {
		em.persist(customer);
	}

	@Override
	public Customer getById(String customerId) throws RecordNotFoundException {
	try {
		return	(Customer) em.createQuery("SELECT customer FROM Customer as customer WHERE customer.customerId=:customerId")
			.setParameter("customerId", customerId)
			.getSingleResult();
	}
	catch(NoResultException e) {
		throw new RecordNotFoundException();
	}
	}

	@Override
	public List<Customer> getByName(String name) {		
		return em.createQuery("SELECT customer from Customer where customer.companyName=:companyName")
				.setParameter("companyName", name)
				.getResultList();
	}

	@Override
	public void update(Customer customerToUpdate) throws RecordNotFoundException {
		em.merge(customerToUpdate);//merge acts as update or insert
	}

	@Override
	public void delete(Customer oldCustomer) throws RecordNotFoundException {
		//make this object be persistent
		oldCustomer=em.merge(oldCustomer);
		//remove
		em.remove(oldCustomer);
	}

	@Override
	public List<Customer> getAllCustomers() {
		
		return em.createQuery("SELECT customer FROM Customer as customer").getResultList();
	}

	@Override
	public Customer getFullCustomerDetail(String customerId) throws RecordNotFoundException {
		//have to do left join fetch
		
		return (Customer)em.createQuery("SELECT customer FROM Customer as customer left join fetch customer.calls where customer.customerId=:customerId")
				.setParameter("customerId", customerId)
				.getSingleResult();
	}

	@Override
	public void addCall(Call newCall, String customerId) throws RecordNotFoundException {
		//customer is a persistent object. Hence, EntityManager will track it for changes and if anything changes to it 
		//em will automatically reflect that on DB
		Customer customer=em.find(Customer.class, customerId);
		customer.addCall(newCall);
	}

}
