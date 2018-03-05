package com.revature.banker;


import java.util.List;
import java.util.Scanner;

import com.revature.account.Accounts;
import com.revature.serial.Serial;
import com.revature.util.LoggingUtil;

public class Admin extends Employee  {


	
	public Admin(){
		super();
	}
	//// List of commands that the admin can utilize to access accounts and users information
	@Override
	public void commands() {
		Scanner com = new Scanner(System.in);
		System.out.println("What would you like to do?(view ACCOUNTS, USERS , REQUESTS, MOVE MONEY , DELETE,EXIT)");
		String input = com.nextLine().toUpperCase();
		while( !input.equals("ACCOUNTS") && !input.equals("USERS")  && !input.equals("REQUESTS") && !input.equals("MOVE MONEY") && !input.equals("EXIT") && !input.equals("DELETE") ){
			System.out.println("Invaild Input");
			System.out.println("What would you like to do?(view ACCOUNTS, USERS , REQUESTS, MOVE MONEY ,EXIT)");
			input = com.nextLine().toUpperCase();
		}
		if (input.equals("ACCOUNTS") ){
			System.out.println("What is the account name?");
			String account = com.nextLine().toUpperCase();
			Accounts(account);
			commands();
		}else if (input.equals("USERS") ){
			System.out.println("What is the username?");
			String user = com.nextLine().toUpperCase();
			Users(user);
			commands();
		}else if (input.equals("REQUESTS") ){
			requests();
		}else if (input.equals("MOVE MONEY") ){
			movemoney();
		}else if (input.equals("DELETE") ){
			System.out.println("What account would you like to delete?");
			String account = com.nextLine().toUpperCase();
			Delete(account);
			commands();
		}else{
			System.out.println("Thank You! Come Again!");
		}
	}

	///Deletes accounts based on the string input if the account does not exist then the user will be prompted if does then the account will be removed from the customer including joint accounts
	public boolean Delete(String input) {
		int deleted = -1;
		List<Customer > customers = Serial.customerdeserial();
		for(int i = 0; i < customers.size(); i++){
			for(int j = 0; j < customers.get(i).getAccounts().size();j++){
				if(customers.get(i).getAccounts().get(j).getName().equals(input)){
					deleted = j;
					customers.get(i).getAccounts().remove(j);
				}
			}
		}
		if(deleted == -1){
			System.out.println("Account was not found");
			LoggingUtil.logWarn("Account was not found.(Admin)");
			return false;
		}else{
			Serial.customerserial(customers);
			System.out.println("Account Deleted");
			LoggingUtil.logInfo("Account was Deleted.(Admin)");
			return true;
		}
	}

	/// method in which the admin can make withdraws, deposits, and transfers for all accounts;
	private void movemoney() {
		Scanner com = new Scanner(System.in);
		System.out.println("What would you like to do?(view WITHDRAW, DEPOSIT , TRANSFER, BACK)");
		String input = com.nextLine().toUpperCase();
		while( !input.equals("WITHDRAW") && !input.equals("DEPOSIT")  && !input.equals("TRANSFER") && !input.equals("BACK")  ){
			System.out.println("Invaild Input");
			System.out.println("What would you like to do?(view WITHDRAW, DEPOSIT , TRANSFER, BACK)");
			input = com.nextLine().toUpperCase();
		}
		if (input.equals("WITHDRAW") ){
			System.out.println("What is the account name?");
			String account = com.nextLine().toUpperCase();
			System.out.println("How much would you like to withdraw?");
			int amount = com.nextInt();
			withdraw(account, amount);
			commands();
		}else if (input.equals("DEPOSIT") ){
			System.out.println("What is the account name?");
			String account = com.nextLine().toUpperCase();
			System.out.println("How much would you like to deposit?");
			int amount = com.nextInt();	
			deposit(account, amount);
			commands();
		}else if (input.equals("TRANSFER") ){
			System.out.println("What is the account name You would like to transfer from?");
			String from = com.nextLine().toUpperCase();
			System.out.println("What is the account name You would like to transfer to?");
			String to  = com.nextLine().toUpperCase();
			System.out.println("What is the amount You would like to transfer?");
			int  amount  = com.nextInt();
			transfer(from,to,amount);
			commands();

		}else {
			commands();
		}
		
		
	}

	////Determines whether a account exists and returns true if it does else false;
	public static boolean accountexists(List<Customer> customers, String accountname){
		boolean found = false;
		for(int i = 0; i < customers.size(); i++){
			for(int j = 0; j < customers.get(i).getAccounts().size();j++){
				if(customers.get(i).getAccounts().get(j).getName().equals(accountname)){
					found  = true;
				}
			}
		}
		return found;
		
	}
	/// gets customer from a list of customers that have the account name specified;
	public static Customer getcustomer(List<Customer> customers,String accountname){
		int customer = -1;
		for(int i = 0; i < customers.size();i++){
			for (int j = 0; j < customers.get(i).getAccounts().size();j++){
				if(customers.get(i).getAccounts().get(j).getName().equals(accountname)){
					customer = i;
				}
			}
		}
		if(customer == -1){
			LoggingUtil.logInfo("NULL value was returned");
			return null;
		}else{
			return customers.get(customer);

		}
	}
	
	/////Would request for two accounts one to transfer from and one to transfer to if either account does not exist then the user would be informed
	//// If both accounts exist then the user would be prompt an amount the the withdrawfrom method would be called.
	private void transfer(String from, String to, int amount) {
		List<Customer> customers  = Serial.customerdeserial();
		if(from.equals(to)){
			System.out.println("cannot transfer into the same account");
		}else if(accountexists(customers,from)== false ){ //////////////////////////////
			System.out.println("There is no account with that name to transfer from.");
			LoggingUtil.logWarn("Account was not found to Transfer From");
		}else if(accountexists(customers,to)== false ){ //////////////////////////////
			System.out.println("There is no account with that name to transfer to.");
			LoggingUtil.logWarn("Account was not found to Transfer To");
		}else{
			Customer customerfrom = getcustomer(customers,from); 
			Customer customerto = getcustomer(customers,to); 

			WithdrawFrom(customerfrom , from ,customerto,to,amount);

			
		}
	
	}
	//////////For transfering funds////////////////
	///// Called after the transfer method that would take the input and determine whether it is possible to make a transfer into an account
	///// works the same as the normal withdraw method. except it calls the depositto method it the withdraw is possible;
	public static  void WithdrawFrom(Customer customerfrom, String from, Customer customerto, String to,int amount) {
		if(customerfrom.getAccounts().get(Customer.existaccount(from, customerfrom.getAccounts())).getBalance() < amount || amount < 0){
			System.out.println("Amount is untransferable");
			LoggingUtil.logWarn("User had Invalid Input for Withdraw(Admin Transfer).");
		}else{	
			if(customerfrom.getAccounts().get(Customer.existaccount(from, customerfrom.getAccounts())).getJoint().equals("") ){
				customerfrom.getAccounts().get(Customer.existaccount(from, customerfrom.getAccounts())).setBalance(customerfrom.getAccounts().get(Customer.existaccount(from, customerfrom.getAccounts())).getBalance() - amount);
				Customer.update(customerfrom);
				DepositTo(customerto,to,amount);
	
			}else{
				Customer jointcustomer = Customer.findcustomer(customerfrom.getAccounts().get(Customer.existaccount(from, customerfrom.getAccounts())).getJoint());
				customerfrom.getAccounts().get(Customer.existaccount(from, customerfrom.getAccounts())).setBalance(customerfrom.getAccounts().get(Customer.existaccount(from, customerfrom.getAccounts())).getBalance() - amount);
				jointcustomer.getAccounts().get(Customer.existaccount(from, jointcustomer.getAccounts())).setBalance(jointcustomer.getAccounts().get(Customer.existaccount(from, jointcustomer.getAccounts())).getBalance() - amount);
				Customer.update(customerfrom);
				Customer.update(jointcustomer);
				DepositTo(customerto,to,amount);
			}
		}
		
	}
 	///// Transfer method for depositing in to an account which after calling the withdrawfrom method will be the end of the transfer transaction 
 	///// Works the same as the deposit method
	public static  void DepositTo(Customer customerto, String to, int amount) {
		if(customerto.getAccounts().get(Customer.existaccount(to, customerto.getAccounts())).getJoint().equals("") ){
			customerto.getAccounts().get(Customer.existaccount(to, customerto.getAccounts())).setBalance(customerto.getAccounts().get(Customer.existaccount(to, customerto.getAccounts())).getBalance() + amount);
			Customer.update(customerto);

		}else{
			Customer jointcustomer = Customer.findcustomer(customerto.getAccounts().get(Customer.existaccount(to, customerto.getAccounts())).getJoint());
			customerto.getAccounts().get(Customer.existaccount(to, customerto.getAccounts())).setBalance(customerto.getAccounts().get(Customer.existaccount(to, customerto.getAccounts())).getBalance() + amount);
			jointcustomer.getAccounts().get(Customer.existaccount(to, jointcustomer.getAccounts())).setBalance(jointcustomer.getAccounts().get(Customer.existaccount(to, jointcustomer.getAccounts())).getBalance() + amount);
			Customer.update(customerto);
			Customer.update(jointcustomer);
		}	
		System.out.println("Funds Transfered");
	}
	
	///// Deposit the requested amount into the user's account, the only way that the user can't deposit into a account is if the account does not exist
	public static void deposit(String input, int amount) {
		List<Customer > customers = Serial.customerdeserial();
		if(accountexists(customers, input) == false){
			System.out.println("account does not exist");
			LoggingUtil.logWarn("Account was Not Found.(Admin)");

		}else{
			Customer customer = getcustomer(customers,input);
		
			if(customer.getAccounts().get((Customer.existaccount(input,customer.getAccounts()))).getJoint().equals("")){
				customer.getAccounts().get((Customer.existaccount(input,customer.getAccounts()))).setBalance(customer.getAccounts().get((Customer.existaccount(input,customer.getAccounts()))).getBalance() + amount);
				System.out.println("Funds Deposited");
				Customer.update(customer);
			}else{
				Customer join = Customer.findcustomer(customer.getAccounts().get(Customer.existaccount(input, customer.getAccounts())).getJoint());
				customer.getAccounts().get((Customer.existaccount(input,customer.getAccounts()))).setBalance(customer.getAccounts().get((Customer.existaccount(input,customer.getAccounts()))).getBalance() + amount);
				join.getAccounts().get((Customer.existaccount(input,join.getAccounts()))).setBalance(join.getAccounts().get((Customer.existaccount(input,join.getAccounts()))).getBalance() + amount);
				System.out.println("Funds Deposited");
				Customer.update(customer);
				Customer.update(join);

			}
				
		}
		
	}
	/////// Withdraws a specific amount from the user's requested account if the amount does is under 0 or over the balance then the user would prompt that the transaction is impossible
	/////// if the amount is valid then the amount would be pulled from the account and reserialize into the Customer.dat
	public void withdraw(String input, int amount) {
		List<Customer > customers = Serial.customerdeserial();
		if(accountexists(customers, input) == false){
			System.out.println("account does not exist");
			LoggingUtil.logWarn("Account was Not Found.(Admin)");

		}else{
			Customer customer = getcustomer(customers,input);
			if(customer.getAccounts().get((Customer.existaccount(input,customer.getAccounts()))).getBalance() < amount  ){
				System.out.println("You do not have that much in this account!");
			}else if(amount < 0){
				System.out.println("Can't add negative amount!");
			}else{
			
				if(customer.getAccounts().get((Customer.existaccount(input,customer.getAccounts()))).getJoint().equals("")){
					customer.getAccounts().get((Customer.existaccount(input,customer.getAccounts()))).setBalance(customer.getAccounts().get((Customer.existaccount(input,customer.getAccounts()))).getBalance() - amount);
					System.out.println("Funds Withdrawn");
					Customer.update(customer);
				}else{
					Customer join = Customer.findcustomer(customer.getAccounts().get(Customer.existaccount(input, customer.getAccounts())).getJoint());
					customer.getAccounts().get((Customer.existaccount(input,customer.getAccounts()))).setBalance(customer.getAccounts().get((Customer.existaccount(input,customer.getAccounts()))).getBalance() - amount);
					join.getAccounts().get((Customer.existaccount(input,join.getAccounts()))).setBalance(join.getAccounts().get((Customer.existaccount(input,join.getAccounts()))).getBalance() - amount);
					System.out.println("Funds Withdrawn");
					Customer.update(customer);
					Customer.update(join);
					
				}

			}
				
		}
		
	}

	///Displays all the requests that were made by the customers and the admin can either approve or deny all request cannot chose 
	private void requests() {
		List<Accounts > requests = Serial.accountdeserial();	

		if(requests.isEmpty()){
			System.out.println("There are No requests to look at");
			commands();
		}else{
			Scanner com = new Scanner(System.in);
			System.out.println("Request: " + requests);
			System.out.println("Would you like to APPROVE or DENY?(or go BACK)");
			String input = com.nextLine().toUpperCase();
			while( !input.equals("APPROVE") && !input.equals("DENY")  && !input.equals("BACK")  ){
				System.out.println("Invaild Input");
				System.out.println("Would you like to APPROVE or DENY?(or go BACK)");
				input = com.nextLine().toUpperCase();
			}
			if (input.equals("APPROVE") ){
				Approve(requests);
				commands();
			}else if (input.equals("DENY") ){
				Deny(requests);
				commands();
			}else{
				commands();
			}
		}
	}

	/// Would deny all the request in the request.dat file by removing all of the accounts in the list
	public static void Deny(List<Accounts> requests) {
		while(!requests.isEmpty()){
			requests.remove(0);
		}
		Serial.accountserial(requests);
		System.out.println("Account denied");
	}
	//// would put all the requested accounts into the Customrs account list and then empty all accounts list and reserialize both the customers and accounts;
	public static void Approve(List<Accounts> requests) {
		List<Customer > customers = Serial.customerdeserial();
		
		for(int i = 0; i < requests.size();i++){
			for(int j = 0; j < customers.size();j++){	
				if(requests.get(i).getUsername().equals(customers.get(j).getUsername()) ){
					customers.get(j).getAccounts().add(requests.get(i));
				}
			}
		}
		
		while(!requests.isEmpty()){
			requests.remove(0);
		}
		Serial.accountserial(requests);
		Serial.customerserial(customers);
		System.out.println("Account approved");
		
		
	}




}
