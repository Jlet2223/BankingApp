package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.pojo.Customer;
import com.revature.util.ConnectionFactory;

public class CustomersDaoImpl implements CustomersDao {

	Connection connect  = ConnectionFactory.getInstance().getConnection();

	public void createAccount(Customer customer) {
		String sql = "INSERT INTO CUSTOMER (USERNAME , PSSWRD , FIRSTNAME, LASTNAME) VALUES (? , ? , ?, ?)";
		
		try{
			connect.setAutoCommit(false);
			
			PreparedStatement create = connect.prepareStatement(sql);
			create.setString(1,customer.getUsername());
			create.setString(2,customer.getPsswrd());
			create.setString(3,customer.getFirstname());
			create.setString(4,customer.getLastname());
			create.executeQuery();
			connect.commit();
			connect.close();
		} catch (SQLException e){
			e.printStackTrace();
		}
		
	}

	public Customer getCustomer(String username, String password) {
		Customer customer = new Customer();
		
		String sql = "SELECT * FROM CUSTOMER WHERE USERNAME = ? AND PSSWRD = ?";
		
		try {
			PreparedStatement select = ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			select.setString(1,username);
			select.setString(2, password);
			ResultSet output = select.executeQuery();
			
			while(output.next()){
				customer.setCustomer_id(output.getInt(1));
				customer.setUsername(output.getString(2));
				customer.setPsswrd(output.getString(3));
				customer.setFirstname(output.getString(4));
				customer.setLastname(output.getString(5));
				customer.setEmail(output.getString(6));
				customer.setAddress(output.getString(7));
			}
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customer;
	}

	public void updateAddress(Customer customer, String info) {
		String sql = "UPDATE CUSTOMER SET ADDRESS = ? WHERE CUSTOMER_ID = ?";
		
		try {
			connect.setAutoCommit(false);
			PreparedStatement update = ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			update.setString(1, info);	
			update.setInt(2, customer.getCustomer_id());
			update.executeQuery();
			connect.commit();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void updateEmail(Customer customer, String info) {
		String sql = "UPDATE CUSTOMER SET EMAIL = ? WHERE CUSTOMER_ID = ?";
		try {
			connect.setAutoCommit(false);
			PreparedStatement update = ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			update.setString(1, info);	
			update.setInt(2, customer.getCustomer_id());
			update.executeQuery();
			connect.commit();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public List<Customer> getAllCustomers() {
		List<Customer> customers = new ArrayList<Customer>();
		String sql = "SELECT * FROM CUSTOMER ";
		
		try {
			PreparedStatement select = ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			ResultSet output = select.executeQuery();
			
			while(output.next()){
				Customer customer = new Customer();
				customer.setCustomer_id(output.getInt(1));
				customer.setUsername(output.getString(2));
				customer.setPsswrd(output.getString(3));
				customer.setFirstname(output.getString(4));
				customer.setLastname(output.getString(5));
				customer.setEmail(output.getString(6));
				customer.setAddress(output.getString(7));
				customers.add(customer);
			}
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customers;
	}

}
