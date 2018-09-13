package com.sp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Represents a customer in the CRM system.
 */

@Entity
public class Customer 
{
	
	@Id
	private String customerId; // A simple unique value for the customer - not necessarily the database primary key.

	private String companyName; //The company name
	
	private String email; // The contact's email address
	
	private String telephone; //The contact's telephone number
	
	private String notes; //Any notes associated with this customer

	@OneToMany(cascade=CascadeType.ALL) //References to any Calls this customer has made
	private List<Call> calls;	
	
	
	
	/*
	 * Constructor - id, notes and name are required
	 */
	public Customer(String customerId, String companyName, String email,
			        String telephone, String notes)
	{
		this(customerId, companyName, notes);
		this.email = email;
		this.telephone = telephone;
	}
	
	/* Constructor - email and telephone are optional
	 */
	public Customer(String customerId, String companyName, String notes)
	{
		this.customerId = customerId;
		this.companyName = companyName;
		this.notes = notes;
		this.calls = new ArrayList<Call>();
	}
	
	/* Add a new call for this customer
	 */
	public void addCall(Call callDetails) 
	{
		this.calls.add(callDetails);		
	}
	
	/**
	 * A Simple toString implementation
	 */
	public String toString()
	{
		return this.customerId + ": " + this.companyName +": "+this.notes;
	}

	public String getCustomerId() 
	{
		return this.customerId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getEmail() {
		return email;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getNotes() {
		return notes;
	}

	public List<Call> getCalls() {
		return calls;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setCalls(List<Call> calls) {
		this.calls = calls;
	}

	// needed for JPA - ignore until then
	public Customer() {}
}
