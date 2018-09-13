package com.sp.client;

import java.util.ArrayList; 
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.runner.Computer;
import org.junit.runner.JUnitCore;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sp.domain.Action;
import com.sp.domain.Call;
import com.sp.domain.Customer;
import com.sp.integrationtests.CustomerManagementIntegrationTests;
import com.sp.services.calls.CallHandlingService;
import com.sp.services.customers.CustomerManagementService;
import com.sp.services.customers.CustomerManagementServiceProductionImpl;
import com.sp.services.customers.CustomerNotFoundException;
import com.sp.services.diary.DiaryManagementService;

public class SimpleClientTest {

	public static void main(String[] args) {

		/* 
		 * RUNNING INTEGRATION TEST
		 * DAO:      CustomerDaoJpaImpl
		 * SERVICES: CustomerManagementServiceProductionImpl
		 			 CallHandlingServiceImpl 
		 			 DiaryManagementServiceProductionImpl
		 * ADVICE:   PerformanceTimingAdvice 
		 */
		
		Computer computer = new Computer();
		JUnitCore jUnitCore = new JUnitCore();
		jUnitCore.run(computer, CustomerManagementIntegrationTests.class);
		
	
		




	}

	public static void runTestMock() {
		/*******************************ignore***************************************/
		ClassPathXmlApplicationContext container= new ClassPathXmlApplicationContext("application-Jpa.xml");
		try {
			CustomerManagementService customerService= container.getBean("customerService",CustomerManagementService.class);
			CallHandlingService callService= container.getBean("callService",CallHandlingService.class);
			DiaryManagementService diaryService= container.getBean("diaryService",DiaryManagementService.class);


			Customer newCustomer= new Customer("23255","Achme","Good Company");
			//begin
			customerService.newCustomer(newCustomer);
			//commit
			Call newCall=new Call("Hi there this is a test");		
			Action action1= new Action("Improve this later ", new GregorianCalendar(2018,0,0),"invalidquantum" );
			Action action2= new Action("Finally, publish to Git",new GregorianCalendar(2018,0,0),"invalidquantum");

			List<Action> actions= new ArrayList<>();
			actions.add(action1);
			actions.add(action2);
			//begin
			try {		
				callService.recordCall("23255", newCall, actions);
			}
			catch(CustomerNotFoundException e) {
				System.out.println("Customer Not Found");
			}//commit

			System.out.println("Here are outstanding actions: ");

			Collection<Action> incompleteActions= diaryService.getAllIncompleteActions("invalidquantum");
			for(Action a: incompleteActions) {
				System.out.println(a);
			}

		}finally {
			container.close();
		}

	}

}
