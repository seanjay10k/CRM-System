package com.sp.services.customers;

import java.util.List;

import com.sp.domain.Call;
import com.sp.domain.Customer;

/**
 * This interface defines the functionality we want the Customer Management Service to offer. 
 */
public interface CustomerManagementService 
{
	
	public void newCustomer(Customer newCustomer); //adds customer to database
	
	public void updateCustomer(Customer changedCustomer) throws CustomerNotFoundException;// updates customer in DB

	public void deleteCustomer(Customer oldCustomer) throws CustomerNotFoundException; // delete customer from DB
	
	public Customer findCustomerById(String customerId) throws CustomerNotFoundException;

	public List<Customer> findCustomersByName(String name);// find customer by name

	public List<Customer> getAllCustomers(); //complete list of all customers
	/**
	 * For the specified customer, returns the customer object together will all calls associated with that customer
	 */
	public Customer getFullCustomerDetail(String customerId) throws CustomerNotFoundException;// 		
	/**
	 * Records a call against a particular customer
	 *	(could be achieved by calling the update method, but this is a more convenient interface)
	 */
	public void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException;

	
	
	
}
