package com.sp.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represents a call made by a customer.
 * For example : "at 4:30pm on the 2nd December, Martha from Acme Corp called 
 * asking if we are interested in buying their Giant Rubber Bands (for tripping road runners)"
 */

@Entity
@Table(name="TBL_CALL")
public class Call 
{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id; // database neutral id: used for JPA, ignore until then
	
	private Date timeAndDate; // Records the time and date of the call. 
	
	private String notes;// General notes about the call
	
	
	/**
	 * Constructor for Call
	 * @param notes eg "Martha from Acme Corp Called etc"
	 */
	public Call(String notes)
	{
		// this defaults to a timestamp of "now"
		this (notes, new java.util.Date());
	}
	
	/**
	 * Constructor for Call if the automatic timestamp needs to be overridden
	 */
	public Call(String notes, Date timestamp)
	{
		// this defaults to a timestamp of "now"
		this.timeAndDate = timestamp;	
		this.notes = notes;
	}
	
	
	/**
	 * A simple toString
	 */
	public String toString()
	{
		return this.timeAndDate + " : " + this.notes;
	}

	public Date getTimeAndDate() {
		return timeAndDate;
	}

	public void setTimeAndDate(Date timeAndDate) {
		this.timeAndDate = timeAndDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	// needed for JPA - ignore until then
	public Call() {}
}
