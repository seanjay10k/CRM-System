package com.sp.integrationtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sp.domain.Action;
import com.sp.domain.Call;
import com.sp.domain.Customer;
import com.sp.services.calls.CallHandlingService;
import com.sp.services.customers.CustomerManagementService;
import com.sp.services.customers.CustomerNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/daos.xml","/services.xml","/misc-beans.xml","/datasource-test.xml"})
@Transactional
public class CustomerManagementIntegrationTests {
	
	@Autowired
	private CustomerManagementService customerService;
	
	@Autowired
	private CallHandlingService callService;
	
	@Test
	public void testCreatingCustomerRecord() {
		System.out.println("\nTest CREATE a customer into customer table:");
		Customer testCustomer= new Customer("0909", "SP","email","telephone","notes");
		customerService.newCustomer(testCustomer);
		List<Customer>customerList= customerService.getAllCustomers();
		int numberCustomer=customerList.size();
		
		assertEquals(1, numberCustomer);
	}
	
	@Test
	public void testCreatingNewCustomer() {
		System.out.println("\nTest QUERY of a customer from customer table:");
		Customer testCustomer= new Customer("0909", "SP","email","telephone","notes");
		customerService.newCustomer(testCustomer);
		
		try {
			Customer foundCustomer= customerService.findCustomerById("0909");
			assertEquals(testCustomer, foundCustomer);
		} catch (CustomerNotFoundException e) {
			fail("Customer not found!");
		}
	}
	
	@Test
	public void testAddingACallToACustomer() {
		System.out.println("\nTest ADDING a call and associated actions for a customer in customer table:");
		Customer testCustomer= new Customer("0909", "SP","email","telephone","notes");
		customerService.newCustomer(testCustomer);
		
		Call testCall= new Call("Just a test call.");
		List<Action> actions= new ArrayList<>();
		
		try {
			callService.recordCall("0909", testCall, actions);
			Customer foundCustomer=  customerService.getFullCustomerDetail("0909");
			Call foundCall= foundCustomer.getCalls().get(0);
			assertEquals(testCall, foundCall);
			
		} catch (CustomerNotFoundException e) {
			fail("No such Customer.");
		}
		
	}
	@Test
	public void testDeletingCustomerRecord() {
		System.out.println("\nTest DELETING a customer from customer table:");
		Customer testCustomer= new Customer("0909", "SP","email","telephone","notes");
		customerService.newCustomer(testCustomer);
		
		try {
			customerService.deleteCustomer(testCustomer);
			List<Customer>customerList= customerService.getAllCustomers();
			int numberCustomer=customerList.size();
			assertEquals(0, numberCustomer);
		} catch (CustomerNotFoundException e) {
			fail("Couldn't delete the customer");
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
}
