package com.revature.dao;

import java.util.List;

import com.revature.pojo.Customer;

public interface CustomersDao {

	public void createAccount(Customer customer);
	
	public Customer getCustomer(String username, String password);
		
	public void updateAddress(Customer customer, String info);
	
	public void updateEmail(Customer customer, String info);
	
	public List<Customer> getAllCustomers();
	
}
