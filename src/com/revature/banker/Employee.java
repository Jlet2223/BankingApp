package com.revature.banker;

import java.util.List;
import java.util.Scanner;

import com.revature.serial.Serial;
import com.revature.util.LoggingUtil;

public class Employee implements Banker  {

	public Employee(){
		super();
	}
	
	////Comamands that would prompt the employee what they can do with their options
	public void commands() {
		Scanner com = new Scanner(System.in);
		System.out.println("What would you like to do?(view ACCOUNTS, USERS ,EXIT)");
		String input = com.nextLine().toUpperCase();
		while( !input.equals("ACCOUNTS") && !input.equals("USERS")  && !input.equals("EXIT")){
			System.out.println("Invaild Input");
			System.out.println("What would you like to do?(view ACCOUNTS, USERS ,EXIT)");
			input = com.nextLine().toUpperCase();
		}
		if (input.equals("ACCOUNTS") ){
			System.out.println("What is the account name?");
			String account = com.nextLine().toUpperCase();
			Accounts(account);
			commands();
		}else if (input.equals("USERS") ){
			System.out.println("What is the username?");
			String username = com.nextLine().toUpperCase();
			Users(username);
			commands();
		}else{
			System.out.println("Thank You! Come Again!");
		}
	}

	//// Displays the Customer information based no the user name if it even exists;
	public String Users(String input) {
		
		Customer customer = Customer.findcustomer(input);
		if(customer == null){
			System.out.println("Customer does not exist");
			LoggingUtil.logWarn("Customer does not exist.");

			return "";
		}else{
			System.out.println(customer.toString());
			return "Customer Found";
		}		
	}
	/// Displays account information based on the input of the account name if it does not exist then a message would appear
	public String Accounts(String input) {	
		List<Customer> customers  = Serial.customerdeserial();	

		int account = -1;
		int customer = -1;

		for(int i = 0; i < customers.size();i++){
			for (int j = 0; j < customers.get(i).getAccounts().size();j++){
				if(customers.get(i).getAccounts().get(j).getName().equals(input)){
					account = j;
					customer = i;
				}
			}
		}
		if(account == -1){
			System.out.println("account does not exist");
			LoggingUtil.logWarn("Account does not exist.");
			return "";
		}else{
			System.out.println(customers.get(customer).getAccounts().get(account).toString());
			return "Account Found";
			
		}
		
						
	}







}
