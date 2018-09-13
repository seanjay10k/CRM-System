package com.sp.domain.test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;
import static org.junit.Assert.*;

import com.sp.domain.Action;

/**
 * We've added this short test just to demonstrate the kind of
 * tests we'd expect to see in a unit test.
 */
public class TestActions
{

	@Test
	public void futureActionsAreNotOverdue()
	{
		System.out.println("Unit test I for isOverdue method in Action class");
		GregorianCalendar requiredBy = new GregorianCalendar();
		requiredBy.add(Calendar.DAY_OF_WEEK, 1);		
		Action testAction = new Action("A Test Action", requiredBy, null);
		boolean overdue = testAction.isOverdue();		
		assertFalse(overdue);
	}
	
	@Test
	public void pastActionsAreOverdue()
	{		
		System.out.println("Unit test II for isOverdue method in Action class");
		GregorianCalendar requiredBy = new GregorianCalendar(1980, 1, 1, 1, 1, 1);
		Action testAction = new Action("A Test Action", requiredBy, null);
		boolean overdue = testAction.isOverdue();	
		assertTrue(overdue);
	}
}
