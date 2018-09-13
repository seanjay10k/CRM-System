package com.sp.services.customers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sp.dataaccess.CustomerDao;
import com.sp.dataaccess.RecordNotFoundException;
import com.sp.domain.Call;
import com.sp.domain.Customer;

@Transactional
@Service("customerService")
public class CustomerManagementServiceProductionImpl implements CustomerManagementService {

	private CustomerDao dao;
	
	
	@Autowired
	public CustomerManagementServiceProductionImpl(CustomerDao dao) {
		
		this.dao = dao;
	}

	@Override
	public void newCustomer(Customer newCustomer) {
		
		dao.create(newCustomer);

	}

	//@Transactional(rollbackFor=CustomerNotFoundException.class)
	public void updateCustomer(Customer changedCustomer) throws CustomerNotFoundException{
		try {
			dao.update(changedCustomer);
		} catch (RecordNotFoundException e) {
			throw new CustomerNotFoundException();
		}

	}

	@Override
	public void deleteCustomer(Customer oldCustomer) throws CustomerNotFoundException{
		try {
			dao.delete(oldCustomer);
		} catch (RecordNotFoundException e) {
			throw new CustomerNotFoundException();
		}

	}

	@Override
	public Customer findCustomerById(String customerId) throws CustomerNotFoundException {
		try {
			return dao.getById(customerId);
		} catch (RecordNotFoundException e) {
			throw new CustomerNotFoundException();
		}
		
	}

	@Override
	public List<Customer> findCustomersByName(String name) {
		
		return dao.getByName(name);
	}

	@Override
	public List<Customer> getAllCustomers() {
		
		return dao.getAllCustomers();
	}

	@Override
	public Customer getFullCustomerDetail(String customerId) throws CustomerNotFoundException {
		// TODO Auto-generated method stub
		try {
			return dao.getFullCustomerDetail(customerId);
		} catch (RecordNotFoundException e) {
		throw new CustomerNotFoundException();	
		}
	}

	@Override
	public void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException {
		// TODO Auto-generated method stub
		try {
			dao.addCall(callDetails, customerId);
		} catch (RecordNotFoundException e) {
			throw new CustomerNotFoundException();
		}
	}

}
