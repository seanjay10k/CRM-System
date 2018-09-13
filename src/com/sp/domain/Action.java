package com.sp.domain;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Represents the details of an action to be carried out.
 */

@Entity
public class Action 
{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int actionId; // Some kind of neutral key
	private String details; // Details of the action - eg "Ask John in accounts if we can afford a new order"
	private Calendar requiredBy; // The "required by" date and time.
	private String owningUser; // The user that owns this action.
	private boolean complete; //  Is this action complete?
	
	/**
	 * Constructor of Action
	 * @param details eg "Ask John in accounts if we can afford a new order"
	 * @param requiredBy eg "10am, 5 October 2009"
	 * @param owningUser "Mike Jones"
	 */
	public Action(String details, Calendar requiredBy, String owningUser)
	{
		this.details = details;
		this.requiredBy = requiredBy;
		this.owningUser = owningUser;
		this.complete = false;
	}
	
	public Action(String actionId, String details, Calendar requiredBy, String owningUser, boolean complete)
	{
		this.details = details;
		this.requiredBy = requiredBy;
		this.owningUser = owningUser;
		this.complete = complete;
		
		/** this looks a little odd; it is needed because in an earlier chapter
		// I used a string for actionIds but when recording for JPA I realised
		// we needed to use ints. So I've done this adapting to avoiding breaking
		// the calling code.*/
		this.actionId = new Integer(actionId);
	}
	
	/*
	 * Determines if this Action is overdue
	 */
	public boolean isOverdue()
	{
		Calendar dateNow = new java.util.GregorianCalendar();
		
		return dateNow.after(this.requiredBy);
	}
	
	/**
	 * A simple toString - feel free to change it
	 */
	public String toString()
	{
		return "Action for " + this.owningUser + ": " + this.details + ", required by " + this.requiredBy.getTime();
	}

	/**
	 * Completes this action
	 */
	public void completeAction()
	{
		this.complete = true;
	}
	
	/**
	 * Returns whether or not this action has been completed
	 */
	public boolean isComplete() 
	{
		return this.complete;
	}

	public String getOwningUser() {
		return owningUser;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Calendar getRequiredBy() {
		return requiredBy;
	}

	public void setRequiredBy(Calendar requiredBy) {
		this.requiredBy = requiredBy;
	}

	public void setOwningUser(String owningUser) {
		this.owningUser = owningUser;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public int getActionId() {
		return actionId;
	}

	public void setActionId(int actionId) {
		this.actionId = actionId;
	}
	
	// needed for JPA - ignore until then
	public Action() {}
}
