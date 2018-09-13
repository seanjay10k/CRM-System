 package com.sp.services.diary;

import java.util.List;

import com.sp.domain.Action;

/**
 * This interface defines the functionality required in the Diary Management Service.
 */
public interface DiaryManagementService 
{

	public void recordAction(Action action); // records an action into a diary
	
	public List<Action> getAllIncompleteActions(String requiredUser); // return all incomplete action for a user
}
