package com.sp.dataaccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sp.domain.Call;
import com.sp.domain.Customer;

@Repository
public class CustomerDaoImpl implements CustomerDao {

	

	private JdbcTemplate template;

	private static final String CREATE_CUSTOMER_TABLE="CREATE TABLE CUSTOMER(CUSTOMER_ID VARCHAR(20),COMPANY_NAME VARCHAR(50),EMAIL VARCHAR(50),TELEPHONE VARCHAR(20), NOTES VARCHAR(255))";
	private static final String CREATE_CALL_TABLE="CREATE TABLE TBL_CALL(NOTES VARCHAR(255),TIME_AND_DATE DATE, CUSTOMER_ID VARCHAR(20))";
	private static final String INSERT_CUSTOMER="INSERT INTO CUSTOMER (CUSTOMER_ID,COMPANY_NAME,EMAIL,TELEPHONE,NOTES) VALUES(?,?,?,?,?) ";
	private static final String SELECT_CUSTOMER_BY_ID = "SELECT * FROM CUSTOMER WHERE CUSTOMER_ID=?";
	
	@Autowired
	public CustomerDaoImpl(JdbcTemplate template) {

		this.template = template;
	}
	@Autowired
	public void createTable() {

		// CREATE CUSTOMER TABLE WHEN INITIALIZED
		try {
			template.update(CREATE_CUSTOMER_TABLE);
			
		}catch(BadSqlGrammarException bse)
		{
			/*BadSqlGrammerException is thrown by Spring.
			  Given that our SQl statement is correct, this BadSqlGrammarException exception would imply that table already exists.
			  if table already exist, its o.k to just swallow it,since client will be given already existing table to work on.
			  Furthermore, instead of swallowing we can log it, which i'm not doing here*/
			System.out.println("Customer Table already exists!");
		}

		// CREATE CALL TABLE WHEN INITIALIZED
		try {
			template.update(CREATE_CALL_TABLE);

		}catch(BadSqlGrammarException bse) {
			System.out.println("Call table already exists!");
		}
	}

	@Override
	public void create(Customer customer) {

		template.update(INSERT_CUSTOMER, customer.getCustomerId(),customer.getCompanyName(),customer.getEmail(),customer.getTelephone(),customer.getNotes());

	}

	@Override
	public Customer getById(String customerId) throws RecordNotFoundException {
	
		try {
		return template.queryForObject(SELECT_CUSTOMER_BY_ID, new ResultMapperCustomer(),customerId );		
		}
		catch(IncorrectResultSizeDataAccessException ie) {
			throw new RecordNotFoundException();
		}
	}

	@Override
	public List<Customer> getByName(String name) {
		
		return template.query("SELECT * FROM CUSTOMER WHERE COMPANY_NAME=?", new ResultMapperCustomer(),name);
	}

	@Override
	public void update(Customer customerToUpdate) throws RecordNotFoundException {
		
		int rowsAffected=template.update("UPDATE CUSTOMER SET COMPANY_NAME=?,EMAIL=?,TELEPHONE=?,NOTES=? WHERE CUSTOMER_ID=?",
							customerToUpdate.getCompanyName(),
							customerToUpdate.getEmail(),
							customerToUpdate.getTelephone(),
							customerToUpdate.getNotes(),
							customerToUpdate.getCustomerId());
		if(rowsAffected!=1) {
			throw new RecordNotFoundException();
		}
		
	}

	@Override
	public void delete(Customer oldCustomer) throws RecordNotFoundException {
		
		int rowsAffected= template.update("DELETE FROM CUSTOMER WHERE CUSTOMER_ID=?",oldCustomer.getCustomerId());
		
		if(rowsAffected!=1) {
			throw new RecordNotFoundException();
		}

	}

	@Override
	public List<Customer> getAllCustomers() {
		return template.query("SELECT * FROM CUSTOMER",new ResultMapperCustomer()); 
		
	}

	@Override
	// also have to get calls
	public Customer getFullCustomerDetail(String customerId) throws RecordNotFoundException {
		
		Customer customer= this.getById(customerId);
		
		List<Call> allCallsForTheCustomer= template.query("SELECT * FROM TBL_CALL WHERE CUSTOMER_ID=?",
																new ResultsMapperCalls(),customerId);
		customer.setCalls(allCallsForTheCustomer);
		
		return customer;
	}

	@Override
	public void addCall(Call newCall, String customerId) throws RecordNotFoundException {
		
		Customer customer=this.getById(customerId);
		template.update("INSERT INTO TBL_CALL(NOTES,TIME_AND_DATE,CUSTOMER_ID) VALUES(?,?,?) ",newCall.getNotes(),newCall.getTimeAndDate(),customerId  );

	}

}
	class ResultMapperCustomer implements RowMapper<Customer>{

		@Override
		public Customer mapRow(ResultSet rs, int rn) throws SQLException {
			String customerId= rs.getString("CUSTOMER_ID");
			String companyName= rs.getString("COMPANY_NAME");
			String email= rs.getString("EMAIL");
			String telephone= rs.getString("TELEPHONE");
			String notes= rs.getString("NOTES");
			
			
			return new Customer(customerId,companyName,email,telephone,notes);
		}
		
	}
	class ResultsMapperCalls implements RowMapper<Call>{

		@Override
		public Call mapRow(ResultSet rs, int rn) throws SQLException {
			//NOTES VARCHAR(255),TIME_AND_DATE DATE, CUSTOMER_ID
			String notes=rs.getString("NOTES");
			Date time_and_date=rs.getDate("TIME_AND_DATE");
	
			return new Call(notes,time_and_date);
		}
		
	}