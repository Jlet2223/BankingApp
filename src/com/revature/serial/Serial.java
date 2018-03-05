package com.revature.serial;

import java.io.FileInputStream;
import com.revature.util.LoggingUtil;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.revature.account.Accounts;
import com.revature.banker.Customer;

public class Serial {

	private static final String datafile = "Customers.dat";
	private static final String requestfile = "Requests.dat";
	//// Deserializes the Customer.dat file for reading
	public static List<Customer> customerdeserial(){
		List<Customer> customers  = new ArrayList<Customer>();
		
		try(ObjectInputStream inputcustomers = new ObjectInputStream(new FileInputStream(datafile))){
			LoggingUtil.logTrace("Customers.dat is being opened");
			customers = (List<Customer>) inputcustomers.readObject();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LoggingUtil.logTrace("Customers.dat has been Closed");
		return customers;
	}
	//// Deserializes the Customer.dat file by taking in a list of customers
	public static void customerserial(List<Customer> customer){
		
		try(ObjectOutputStream outputcustomer = new ObjectOutputStream(new FileOutputStream(datafile))){
			LoggingUtil.logTrace("Customers.dat is being opened");
			outputcustomer.writeObject(customer);
			outputcustomer.flush();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LoggingUtil.logTrace("Customers.dat has been Closed");

	}
	//// Deserializes the Request.dat file for reading
	public static List<Accounts> accountdeserial(){
		List<Accounts> requestaccount = new  ArrayList<Accounts>();
		
		try(ObjectInputStream inputrequests = new ObjectInputStream(new FileInputStream(requestfile))){
			LoggingUtil.logTrace("Requests.dat is being opened");
			requestaccount = (List<Accounts>) inputrequests.readObject();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LoggingUtil.logTrace("Resquest.dat has been Closed");

		return requestaccount;
	}
	//// Serializes the Request.dat file  by taking in a list of accounts that are the Requests
	public static void accountserial(List<Accounts> account){
		
		try(ObjectOutputStream outputcustomer = new ObjectOutputStream(new FileOutputStream(requestfile))){
			LoggingUtil.logTrace("Requests.dat is being opened");
			outputcustomer.writeObject(account);
			outputcustomer.flush();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LoggingUtil.logTrace("Resquest.dat has been Closed");


	}
}
