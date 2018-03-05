package com.revature.banker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.account.Accounts;
import com.revature.serial.Serial;
import com.revature.util.LoggingUtil;

import org.apache.log4j.*;


public class Customer implements Banker, Serializable {

	private static final long serialVersionUID = 3092539105119963047L;
	
	private  List<Accounts> accounts;
	private String customername;
	private String username;
	private String password;


	
	 
	 public Customer(String username , String password , String name , List<Accounts> accounts){
		 this.setUsername(username);
		 this.setPassword(password);
		 this.setName(name);
		 this.setAccounts(accounts);
	 }
	 
	 public String toString(){
		return "USERNAME: " + username +" NAME: " + customername + " ACCOUNTS: " + accounts.toString();
		 
	 }
	 
	 ///// Creates the customer user accounts by requesting input from the user (username,password,name on account)
	 ///// would then check whether or not the username is available for the user to register if not the create method would be called;
	 ///// once the user name is free an instance of the Customer class will be created and serialized into the Customer.dat file;
	public static void create() {
		List<Customer> customers  = Serial.customerdeserial();
		Scanner cre = new Scanner(System.in);
		System.out.println("Please enter your Username:");
		String username = cre.nextLine().toUpperCase();
		System.out.println("Please enter your Password:");
		String password = cre.nextLine().toUpperCase();
		System.out.println("Please enter the name under this Username:");
		String name = cre.nextLine().toUpperCase();
		if(findcustomer(username) == null){			
			System.out.println("Your account is created");
			List<Accounts> newaccounts = new ArrayList<Accounts>();
			Customer newcustomer = new Customer(username , password , name, newaccounts );	
			customers.add(newcustomer);
			Serial.customerserial(customers);
		}else{
			System.out.println("username already exist");
			create();
		}
		
	}
	
	//ensures that the customer object can be returned once found;
	// Takes in the customers username and looks through the list of customers and finds if the customer exist and returns them if not it will return null
	public static Customer findcustomer(String username ){
		int userfound = -1;
		List<Customer> customers  = Serial.customerdeserial();	
		for(int i = 0; i< customers.size(); i++){
			if(customers.get(i).getUsername().equals(username) ){
				userfound = i;								
			}
		}
		if(userfound == -1){
			LoggingUtil.logWarn("Customer was Not Found.");
			return null;
		}else{
			return customers.get(userfound);
		}
	}
	
	// ensures that the input is a Customer before creating the object 
	// Takes in the customer username and password and determines if the customer exist if not it will return false
	// If the customer does exist then it will determine if the password and user name matches the input if not then it will return false else true
	 public static boolean verify(String username , String password) {	
		if(findcustomer(username) == null){
			return false;
		}else{
			Customer temp = findcustomer(username);
			if(temp.getUsername().equals(username) && temp.getPassword().equals(password)){
				return true;
			}else{
				LoggingUtil.logWarn("User had Invalid Input.(Customer)");
				return false;
			}
		}
	}
	 //// Prints out all the accounts that are associated with that customer account
	private void checkbalances() {
		for(int i = 0; i < getAccounts().size();i++){
			System.out.println(getAccounts().get(i));
		}
		commands();
		
	}

	// Creates a request for a new account by the customer that the admin would look over for approval
	// The user would create a an account name and then determine whether or not that name exist if not then the user would be prompted for whether the account is joint or not
	// If the account is joint then the user would be prompt for another username and password that exists if they don't then it would prompt that they make a request again 
	// If the joint customer does exist then the request the account would be serialized into Request.dat file
	private void newaccount() {
		List<Accounts> requestaccount = Serial.accountdeserial();
		List<Accounts> allaccounts = new ArrayList<Accounts>();
		List<Customer> allcustomers = Serial.customerdeserial();
		for(int i = 0; i < allcustomers.size();i++){
			for(int j = 0; j < allcustomers.get(i).getAccounts().size(); j++){
				allaccounts.add(allcustomers.get(i).getAccounts().get(j));
			}
		}		
		Scanner acc = new Scanner(System.in);
		System.out.println("What is the account name?");
		String name = acc.nextLine().toUpperCase();
		if(existaccount(name,allaccounts) != -1){
			System.out.println("Name is already taken");
			LoggingUtil.logWarn("User had Invalid Input Name Taken.(Customer)");
		}else{
			System.out.println("Do you want to make this account joint?(Y/N)");
			String ans = acc.nextLine().toUpperCase();
			if(ans.equals("N")){	
				Accounts newaccount = new Accounts(name, this.getUsername(), 0, "");
				getAccounts().add(newaccount);				
				requestaccount.add(newaccount);		
				Serial.accountserial(requestaccount);				
				System.out.println("New account is under review");
				
			}else if(ans.equals("Y")){
				System.out.println("Please enter your Username:");
				String username = acc.nextLine().toUpperCase();
				System.out.println("Please enter your Password:");
				String password = acc.nextLine().toUpperCase();
				if (verify(username , password)){					
					Customer join = findcustomer(username);
					Accounts newaccount = new Accounts(name, this.getUsername(), 0, join.getUsername());
					Accounts joinnewaccount = new Accounts(name, join.getUsername(), 0, getUsername());

					requestaccount.add(newaccount);
					requestaccount.add(joinnewaccount);
										
					Serial.accountserial(requestaccount);
					System.out.println("New account is under review");

				}else{
					System.out.println("Customer does not exist!");
					System.out.println("Try the Request again");
					LoggingUtil.logWarn("User had Invalid Customer Input for New Account.");

				}
			}
		}
		commands();
		
	}

	/////Would request for two accounts one to transfer from and one to transfer to if either account does not exist then the user would be informed
	//// If both accounts exist then the user would be prompt an amount the the withdrawfrom method would be called.
	private void transfer() {
		List<Customer> customers  = Serial.customerdeserial();
		Scanner acc = new Scanner(System.in);
		System.out.println("What is the account name You would like to transfer from?");
		String from = acc.nextLine().toUpperCase();
		System.out.println("What is the account name You would like to transfer to?");
		String to  = acc.nextLine().toUpperCase();
		if(from.equals(to)){
			System.out.println("cannot transfer into the same account");
		}else if(existaccount(from , this.getAccounts()) == -1){
			System.out.println("You own no account with that name.");
			LoggingUtil.logWarn("Account was not found to Transfer From.(Customer)");
		}else{
			int account = -1;
			int customer = -1;
			for(int i = 0; i < customers.size();i++){
				for (int j = 0; j < customers.get(i).getAccounts().size();j++){
					if(customers.get(i).getAccounts().get(j).getName().equals(to)){
						account = j;
						customer = i;
					}
				}
			}
			if(account == -1){
				System.out.println("account does not exist");
			}else{
				System.out.println("How much would you like to transfer?");
				int amount = acc.nextInt();
				withdrawfrom(this,customers.get(customer),from ,to,amount);
			}
		}

		commands();
		
	}

	/////// Withdraws a specific amount from the user's requested account if the amount does is under 0 or over the balance then the user would prompt that the transaction is impossible
	/////// if the amount is valid then the amount would be pulled from the account and reserialize into the Customer.dat
	public void withdraw(String input, int amount) {
		if(existaccount(input,getAccounts()) == - 1){
			System.out.println("account does not exist");
			LoggingUtil.logWarn("Account was Not Found.");
		}else{
			if(getAccounts().get((existaccount(input,getAccounts()))).getBalance() < amount  ){
				System.out.println("You do not have that much in this account!");
				LoggingUtil.logWarn("User had Invalid Input for Withdraw.(Customer)");
			}else if(amount < -1){
				System.out.println("Can't add negative amount!");
				LoggingUtil.logWarn("User had Invalid Input for Withdraw.(Customer)");
			}else{
			
				if(getAccounts().get((existaccount(input,getAccounts()))).getJoint().equals("")){
					getAccounts().get((existaccount(input,getAccounts()))).setBalance(getAccounts().get((existaccount(input,getAccounts()))).getBalance() - amount);
					System.out.println("Funds Withdrawn");
					update(this);
				}else{
					Customer join = findcustomer(username);
					getAccounts().get((existaccount(input,getAccounts()))).setBalance(getAccounts().get((existaccount(input,getAccounts()))).getBalance() - amount);
					join.getAccounts().get((existaccount(input,join.getAccounts()))).setBalance(join.getAccounts().get((existaccount(input,join.getAccounts()))).getBalance() - amount);
					System.out.println("Funds Withdrawn");
					update(this);
					update(join);
					
				}

			}
				
		}
		
	}
	
	///// Deposit the requested amount into the user's account, the only way that the user can't deposit into a account is if the account does not exist
 	private void desposit(String input, int amount) {
		if(existaccount(input,getAccounts()) == - 1){
			System.out.println("account does not exist");
			LoggingUtil.logWarn("Account was Not Found.");
		}else{
			if(getAccounts().get((existaccount(input,getAccounts()))).getJoint().equals("")){
				getAccounts().get((existaccount(input,getAccounts()))).setBalance(getAccounts().get((existaccount(input,getAccounts()))).getBalance() + amount);
				System.out.println("Funds Deposited");
				update(this);
			}else{
				Customer join = findcustomer(username);
				getAccounts().get((existaccount(input,getAccounts()))).setBalance(getAccounts().get((existaccount(input,getAccounts()))).getBalance() + amount);
				join.getAccounts().get((existaccount(input,join.getAccounts()))).setBalance(join.getAccounts().get((existaccount(input,join.getAccounts()))).getBalance() + amount);
				System.out.println("Funds Deposited");
				update(this);
				update(join);
			}
		}

	}
	
	//////////For transfering funds////////////////
 	///// Transfer method for depositing in to an account which after calling the withdrawfrom method will be the end of the transfer transaction 
 	///// Works the same as the deposit method
	public  void depositto(Customer customer,int amount,String  accountname) {
		if(getAccounts().get((existaccount(accountname,getAccounts()))).getJoint().equals("")){
			customer.getAccounts().get((existaccount(accountname,customer.getAccounts()))).setBalance(customer.getAccounts().get((existaccount(accountname,customer.getAccounts()))).getBalance() +amount);
			System.out.println("Funds transfered");
			update(customer);
		}else{
			Customer join = findcustomer(this.username);
			customer.getAccounts().get((existaccount(accountname,customer.getAccounts()))).setBalance(customer.getAccounts().get((existaccount(accountname,customer.getAccounts()))).getBalance() +amount);
			join.getAccounts().get((existaccount(accountname,join.getAccounts()))).setBalance(join.getAccounts().get((existaccount(accountname,join.getAccounts()))).getBalance() + amount);
			System.out.println("Funds transfered");
			update(customer);
			update(join);
		}
		
	}
	///// Called after the transfer method that would take the input and determine whether it is possible to make a transfer into an account
	///// works the same as the normal withdraw method. except it calls the depositto method it the withdraw is possible;
	public  void withdrawfrom(Customer customer, Customer tocustomer, String account, String toaccount, int amount) {
		if(getAccounts().get((existaccount(account,getAccounts()))).getBalance() < amount){
			System.out.println("You do not have that much in this account!");
			LoggingUtil.logWarn("User had Invalid Input for Withdraw(Customer Transfer).");
		}else{
			if(customer.getAccounts().get((existaccount(account,customer.getAccounts()))).getBalance() < amount){
				System.out.println("You do not have that much in this account!");
				LoggingUtil.logWarn("Amount was too much to Withdraw(Customer Transfer).");
			}else{
				if(customer.getAccounts().get((existaccount(account,customer.getAccounts()))).getJoint().equals("")){
					customer.getAccounts().get((existaccount(account,customer.getAccounts()))).setBalance(customer.getAccounts().get((existaccount(account,customer.getAccounts()))).getBalance() -amount);
					update(customer);
					depositto(tocustomer, amount, toaccount);
				}else{
					Customer join = findcustomer(this.username);
					customer.getAccounts().get((existaccount(account,customer.getAccounts()))).setBalance(customer.getAccounts().get((existaccount(account,customer.getAccounts()))).getBalance() -amount);
					join.getAccounts().get((existaccount(account,join.getAccounts()))).setBalance(join.getAccounts().get((existaccount(account,join.getAccounts()))).getBalance() - amount);
					update(customer);
					update(join);
					depositto(tocustomer, amount, toaccount);					
				}
		
			}
				
		}
		
	}
	////Determines whether a account exists and returns the index in which they are located within the list of accounts;
	public static int existaccount(String accountname, List<Accounts> accounts){
		int accountindex = -1;
		for(int i = 0; i < accounts.size();i++){
			if(accounts.get(i).getName().equals(accountname)){
				accountindex = i;
			}
		}
		return accountindex;
		
	}
	
	////// updates the list of Customer , with added or updated accounts so they are up to date 
	public static void update(Customer customer) {
		List<Customer> customers  = Serial.customerdeserial();
		for(int i = 0; i < customers.size();i++){
			if(customer.getUsername().equals(customers.get(i).getUsername())){
				customers.remove(i);
			}
			customers.add(customer);
		}
		
		Serial.customerserial(customers);
		
	}
	 
	// gets a list of options for the customer to access through there account;
	@Override
	public void commands() {
		Scanner com = new Scanner(System.in);
		System.out.println("What would you like to do?(DEPOSIT,WITHDRAW,TRANSFER,NEW ACCOUNT,CHECK BALANCES,EXIT)");
		String input = com.nextLine().toUpperCase();
		while( !input.equals("DEPOSIT") && !input.equals("WITHDRAW") && !input.equals("TRANSFER") && !input.equals("NEW ACCOUNT") && !input.equals("CHECK BALANCES") && !input.equals("EXIT")){
			System.out.println("Invaild Input");
			System.out.println("What would you like to do?(DEPOSIT,WITHDRAW,TRANSFER,NEW ACCOUNT,CHECK BALANCES,EXIT)");
			input = com.nextLine().toUpperCase();
		}
		if (input.equals("DEPOSIT") ){
			System.out.println("What is the account name?");
			String account = com.nextLine().toUpperCase();
			System.out.println("How much would you like to deposit?");
			int amount = com.nextInt();
			desposit(account,amount);
			commands();

		}else if (input.equals("WITHDRAW") ){
			System.out.println("What is the account name?");
			String account = com.nextLine().toUpperCase();
			System.out.println("How much would you like to withdraw?");
			int amount = com.nextInt();
			withdraw(account,amount);
			commands();

		}else if (input.equals("TRANSFER") ){
			transfer();
		}else if (input.equals("NEW ACCOUNT") ){
			newaccount();
		}else if (input.equals("CHECK BALANCES") ){
			checkbalances();
		}else{
			System.out.println("Thank You! Come Again!");
		}
	}
////////////////Setters and Getter Functions/////////////////
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return customername;
	}

	public void setName(String name) {
		this.customername = name;
	}

	public  List<Accounts> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Accounts> accounts) {
		this.accounts = accounts;
	}



	


		
		
}
