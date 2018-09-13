package com.sp.services.calls;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sp.domain.Action;
import com.sp.domain.Call;
import com.sp.services.customers.CustomerManagementService;
import com.sp.services.customers.CustomerNotFoundException;
import com.sp.services.diary.DiaryManagementService;

@Transactional
@Service("callService")
public class CallHandlingServiceImpl implements CallHandlingService {

	private CustomerManagementService customerService;
	private DiaryManagementService diaryService;
	@Autowired
	public CallHandlingServiceImpl(CustomerManagementService cms, DiaryManagementService dms) {
	
	this.customerService=cms;
	this.diaryService=dms;
				
	}
	
	//@Transactional(rollbackFor="if there needs to be any checked exception.class")
	public void recordCall(String customerId, Call newCall, Collection<Action> actions)
			throws CustomerNotFoundException {
		
		// call cust service to record a call
		customerService.recordCall(customerId, newCall);
		
		//call diary service to record actions
		for(Action a: actions) {
			
			diaryService.recordAction(a);
		}
		
		

	}

}
