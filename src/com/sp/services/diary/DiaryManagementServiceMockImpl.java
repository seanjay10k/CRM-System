package com.sp.services.diary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sp.domain.Action;

public class DiaryManagementServiceMockImpl implements DiaryManagementService {

	private Set<Action> allActions= new HashSet<Action>();
	
	@Override
	public void recordAction(Action action) {
		allActions.add(action);

	}

	@Override
	public List<Action> getAllIncompleteActions(String requiredUser) {
		List<Action> results= new ArrayList<Action> ();
		
		for(Action a: allActions) {
			if(a.getOwningUser().equals(requiredUser) && !a.isComplete()) {
				results.add(a);
			}
		}
		
		return results;
	}

}
