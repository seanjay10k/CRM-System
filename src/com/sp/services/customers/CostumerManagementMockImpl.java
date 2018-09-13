package com.sp.services.customers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sp.domain.Call;
import com.sp.domain.Customer;

public class CostumerManagementMockImpl implements CustomerManagementService {

	private HashMap<String, Customer> customers;
	
	public CostumerManagementMockImpl() {
	
		customers= new HashMap<String,Customer>();
		customers.put("23255", new Customer("23255", "Acme Ltd", "Amazing roadrunner"));
		customers.put("23266", new Customer("23266", "Austin 5", "Amazing dresses"));
		customers.put("23277", new Customer("23277", "Microsoft", "Amazing software"));
	}

	@Override
	public void newCustomer(Customer newCustomer) {
		customers.put(newCustomer.getCustomerId(), newCustomer);

	}

	@Override
	public void updateCustomer(Customer changedCustomer) {
		customers.put(changedCustomer.getCustomerId(), changedCustomer);

	}

	@Override
	public void deleteCustomer(Customer oldCustomer) {
		customers.remove(oldCustomer.getCustomerId());

	}

	@Override
	public Customer findCustomerById(String customerId) throws CustomerNotFoundException {
		Customer found= customers.get(customerId);
		if(found==null) {
			throw new CustomerNotFoundException(); 
		}
		return found;
	}

	@Override
	public List<Customer> findCustomersByName(String name) {
		List <Customer> cList= new ArrayList<Customer>();
		for(Customer c: customers.values()) {
			if(c.getCompanyName().equals(name)) {
			cList.add(c);	
			}
		}
		
		return cList;
	}

	@Override
	public List<Customer> getAllCustomers() {
		
		return new ArrayList<Customer>(customers.values());
	}

	@Override
	public Customer getFullCustomerDetail(String customerId) throws CustomerNotFoundException {
		
		
		
		return this.findCustomerById(customerId);
	}

	@Override
	public void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException {
		
		Customer customer= this.getFullCustomerDetail(customerId);
		customer.addCall(callDetails);

	}

}
