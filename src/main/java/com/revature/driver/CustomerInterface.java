package com.revature.driver;

import java.util.List;
import java.util.Scanner;

import com.revature.dao.AccountsDao;
import com.revature.dao.AccountsDaoImpl;
import com.revature.dao.CustomerToAccountsDao;
import com.revature.dao.CustomerToAccountsDaoImpl;
import com.revature.dao.CustomersDao;
import com.revature.dao.CustomersDaoImpl;
import com.revature.dao.TransactionDao;
import com.revature.dao.TransactionDaoImpl;
import com.revature.pojo.Accounts;
import com.revature.pojo.Customer;



public class CustomerInterface {
	
	///////Option for new  Customers to create their accounts so that they can log-in
	public static void create() {
		Scanner cre = new Scanner(System.in);
		System.out.println("Please enter your Username:");
		String username = cre.nextLine();
		System.out.println("Please enter your Password:");
		String password = cre.nextLine();
		System.out.println("Please enter the Firstname under this Username:");
		String fname = cre.nextLine();
		System.out.println("Please enter the Lastname under this Username:");
		String lname = cre.nextLine();
		Customer newcustomer = new Customer(0, username , password , fname, lname, null, null );	
		CustomersDao create = new CustomersDaoImpl();
		create.createAccount(newcustomer);
		System.out.println("Creation of Account successful; ");
		System.out.println("Please Log-in");
	}
	
	/////// Takes in user input so that it can verify weather or not that the customer actually exists;
	public static void login() {
		Scanner cre = new Scanner(System.in);
		System.out.println("Please enter your Username:");
		String username = cre.nextLine();
		System.out.println("Please enter your Password:");
		String password = cre.nextLine();
		CustomersDao custom = new CustomersDaoImpl();
		Customer customer = custom.getCustomer(username, password);
		if (customer.getUsername() == null){
			System.out.println("!!!!!!!!!!!!!!!!!!!!Account does not exist.!!!!!!!!!!!!!!!!!!!!" );
		}else{
			commands(customer);
		}
	}

	///////  The main set of commands that the customer can go through and call
	private static void commands(Customer customer) {
		Scanner com = new Scanner(System.in);
		System.out.println("What would you like to do?");
		System.out.println("1> DEPOSIT");
		System.out.println("2> WITHDRAW");
		System.out.println("3> TRANSFER");
		System.out.println("4> NEW ACCOUNT");
		System.out.println("5> CHECK BALANCES");
		System.out.println("6> EDIT INFO");
		System.out.println("7> EXIT");
		int input = com.nextInt();
		while(input!= 1 && input!= 2 && input!= 3 && input!= 4 && input!= 5 && input!= 6 && input!= 7 ){
			System.out.println("Invaild Input");
			System.out.println("What would you like to do?");
			System.out.println("1> DEPOSIT");
			System.out.println("2> WITHDRAW");
			System.out.println("3> TRANSFER");
			System.out.println("4> NEW ACCOUNT");
			System.out.println("5> CHECK BALANCES");
			System.out.println("6> EDIT INFO");
			System.out.println("7> EXIT");
			input = com.nextInt();
		}
		if (input == 1 ){
			deposit(customer);
			commands(customer);
		}else if (input == 2 ){
			withdraw(customer);
			commands(customer);
		}else if (input == 3  ){
			transfer(customer);
			commands(customer);
		}else if (input == 4  ){
			newaccount(customer);
			commands(customer);
		}else if (input == 5  ){
			checkbalances(customer);
			commands(customer);
		}else if (input == 6  ){
			editinfo(customer);
			commands(customer);

		}else{
			System.out.println("Thank You! Come Again!");
		}
		
	}

	//////// Pulls up a list of Customer accounts that they can deposit into but if the account is not open then the request would be impossible to do///
	private static void deposit(Customer customer) {
		CustomerToAccountsDao acc = new CustomerToAccountsDaoImpl();
		List<Accounts> account = acc.getCustomerAccounts(customer);
		if(account.size() ==0){
			System.out.println("No accounts to deposit to.");
		}else{
			Scanner com = new Scanner(System.in);
			for(int i = 0; i < account.size();i++){
				System.out.println(">>>>>>>>>" +(i+1) + "> " + account.get(i).getName() + " ||| Balance: " + account.get(i).getBalance() + "<<<<<<<<<");
			}
			System.out.println("What account would you like to deposit to?");
			int input = com.nextInt();
			while(input > account.size()){
				System.out.println(">>>>>>>>>>>>>>>>>>>>>Invaild Input<<<<<<<<<<<<<<<<<<<<<<<<");
				System.out.println("What account would you like to deposit to?");
				input = com.nextInt();
			}
			if(account.get(input-1).getStatus_id() == 1){
				System.out.println("*************Account is Pending*************");
			}else if(account.get(input-1).getStatus_id() == 3){
				System.out.println("*************Account is Frozen*************");
			}else if(account.get(input-1).getStatus_id() == 4){
				System.out.println("*************Account is Closed*************");
			}else{
				System.out.println("How much would you like to deposit?");
				float amount = com.nextFloat();
				AccountsDao update = new AccountsDaoImpl();
				update.Deposit(account.get(input-1), amount);
				System.out.println("Deposit Complete!");
				TransactionDao trans =  new TransactionDaoImpl();
				trans.addTransaction(account.get(input-1), "DEPOSIT OF " + amount);
			}
		}
	}
	
	//////// Pulls up a list of Customer accounts that they can withdraw from but if the account is not open then the request would be impossible to do///
	private static void withdraw(Customer customer) {
		CustomerToAccountsDao acc = new CustomerToAccountsDaoImpl();
		List<Accounts> account = acc.getCustomerAccounts(customer);
		if(account.size() ==0){
			System.out.println("No accounts to withdraw from.");
		}else{
			Scanner com = new Scanner(System.in);
			for(int i = 0; i < account.size();i++){
				System.out.println(">>>>>>>>>" +(i+1) + "> " + account.get(i).getName() + " ||| Balance: " + account.get(i).getBalance() + "<<<<<<<<<");
			}
			System.out.println("What account would you like to withdraw from?");
			int input = com.nextInt();
			while(input > account.size()){
				System.out.println(">>>>>>>>>>>>>>>>>>>>>Invaild Input<<<<<<<<<<<<<<<<<<<<<<<<");
				System.out.println("What account would you like to withdraw from?");
				input = com.nextInt();
			}
			if(account.get(input-1).getStatus_id() == 1){
				System.out.println("*************Account is Pending*************");
			}else if(account.get(input-1).getStatus_id() == 3){
				System.out.println("*************Account is Frozen*************");
			}else if(account.get(input-1).getStatus_id() == 4){
				System.out.println("*************Account is Closed*************");
			}else{
				System.out.println("How much would you like to withdraw?");
				float amount = com.nextFloat();
				while(amount > account.get(input-1).getBalance() || amount < 0){
					System.out.println("Account Does not have that much.");
					System.out.println("How much would you like to withdraw?");
					amount =  com.nextFloat();
				}
				AccountsDao update = new AccountsDaoImpl();
				update.Withdraw(account.get(input-1), amount);
				System.out.println("Withdraw Complete!");
				TransactionDao trans =  new TransactionDaoImpl();
				trans.addTransaction(account.get(input-1), "WITHDRAW OF " + amount);
			}
		}
	}
	
	//////// Pulls up a list of Customer accounts that they can transfer from and into but if the accounts are not open then the request would be impossible to do///
	private static void transfer(Customer customer) {
		CustomerToAccountsDao accc = new CustomerToAccountsDaoImpl();
		AccountsDao act = new AccountsDaoImpl();

		List<Accounts> account = accc.getCustomerAccounts(customer);
		if(account.size() ==0){
			System.out.println("No accounts to withdraw from.");
		}else{
			Scanner com = new Scanner(System.in);
			for(int i = 0; i < account.size();i++){
				System.out.println(">>>>>>>>>" +(i+1) + "> " + account.get(i).getName() + " ||| Balance: " + account.get(i).getBalance() + "<<<<<<<<<");
			}
			System.out.println("What account would you like to transfer from?");
			int input = com.nextInt();
			while(input > account.size()){
				System.out.println(">>>>>>>>>>>>>>>>>>>>>Invaild Input<<<<<<<<<<<<<<<<<<<<<<<<");
				System.out.println("What account would you like to transfer from?");
				input = com.nextInt();
			}
			if(account.get(input-1).getStatus_id() == 1){
				System.out.println("*************Account is Pending*************");
			}else if(account.get(input-1).getStatus_id() == 3){
				System.out.println("*************Account is Frozen*************");
			}else if(account.get(input-1).getStatus_id() == 4){
				System.out.println("*************Account is Closed*************");
			}else{
				List<Accounts> allaccount = act.getOtherAccounts(account.get(input-1));
				if(allaccount.size() ==0){
					System.out.println("No accounts to deposit to.");
				}else{
					for(int j = 0; j < allaccount.size();j++){
						System.out.println(">>>>>>>>>" +(j+1) + "> " + allaccount.get(j).getName()  + "<<<<<<<<<");
					}
					System.out.println("What account would you like to transfer from?");
					int input2 = com.nextInt();
					while(input2 > allaccount.size()){
						System.out.println(">>>>>>>>>>>>>>>>>>>>>Invaild Input<<<<<<<<<<<<<<<<<<<<<<<<");
						System.out.println("What account would you like to transfer from?");
						input2 = com.nextInt();
					}
					if(allaccount.get(input2-1).getStatus_id() == 1){
						System.out.println("*************Account is Pending*************");
					}else if(allaccount.get(input2-1).getStatus_id() == 3){
						System.out.println("*************Account is Frozen*************");
					}else if(allaccount.get(input2-1).getStatus_id() == 4){
						System.out.println("*************Account is Closed*************");
					}else{
				
						System.out.println("How much would you like to transfer?");
						float amount = com.nextFloat();
						while(amount > account.get(input-1).getBalance() || amount < 0){
							System.out.println("The From account does not have this much.");
							System.out.println("How much would you like to transfer?");
							amount = com.nextFloat();
						}
						AccountsDao update = new AccountsDaoImpl();
						update.Withdraw(account.get(input-1), amount);
						update = new AccountsDaoImpl();
						update.Deposit(allaccount.get(input2-1), amount);
						System.out.println("Transfer Complete!");
						TransactionDao trans =  new TransactionDaoImpl();
						trans.addTransaction(account.get(input-1), "TRANSFER OF " + amount);

					}
				}
			}
		}		
	}

	///////Function in which the customer can create there own accounts when initialized the account is pending 
	private static void newaccount(Customer customer) {
		Scanner acc = new Scanner(System.in);
		System.out.println("What is the account name?");
		String name = acc.nextLine();	
		Accounts newaccount = new Accounts(0, name ,0 , 1);
		AccountsDao account = new AccountsDaoImpl();
		account.createAccount(newaccount);
		CustomerToAccountsDao reference = new CustomerToAccountsDaoImpl();
		account = new AccountsDaoImpl();
		newaccount.setAccount_id(account.getAccountid(newaccount));
		reference.joinAccount(newaccount, customer);
		System.out.println("New account is under review");
	}
	
	/// Pulls a list of all the Customer's accounts and displays all the information for the Customer;
	private static void checkbalances(Customer customer) {
		CustomerToAccountsDao accc = new CustomerToAccountsDaoImpl();
		List<Accounts> accounts =  accc.getCustomerAccounts(customer);

		if(accounts.size() ==0){
			System.out.println("No accounts view.");
		}else{
			for(int i = 0; i < accounts.size();i++){
				System.out.print(">>>>>>>>>" +(i+1) + "> " + accounts.get(i).getName() + " ||| Balance: " + accounts.get(i).getBalance() );
				if(accounts.get(i).getStatus_id() ==1){
					System.out.println("  ||| Status: Pending <<<<<<<<<");
				}else if(accounts.get(i).getStatus_id() ==2){
					System.out.println("  ||| Status: Open    <<<<<<<<<");			
				}else if(accounts.get(i).getStatus_id() ==3){
					System.out.println("  ||| Status: Frozen  <<<<<<<<<");			
				}else{
					System.out.println("  ||| Status: Closed  <<<<<<<<<");				
				}
			}
		}
	}

	////// This allows the user to edit there profiles but does not work properly
	private static void editinfo(Customer customer) {
		CustomersDao cust = new CustomersDaoImpl();
		System.out.println("> Username: " + customer.getUsername()); 
		System.out.println("> Name: " + customer.getFirstname() + " " + customer.getLastname());
		System.out.println("> Email: " + customer.getEmail());
		System.out.println("> Address: " + customer.getAddress());
		System.out.println("Is there anything you would like to change?");
		System.out.println("1> EMAIL");
		System.out.println("2> ADDRESS");
		System.out.println("3> GO BACK");
		Scanner acc = new Scanner(System.in);
		int input = acc.nextInt();
		while(input!= 1 && input!= 2 && input!= 3){
			System.out.println(">>>>>>>>>>>>>>>>>>>>>Invaild Input<<<<<<<<<<<<<<<<<<<<<<<<");
			System.out.println("Is there anything you would like to change?");
			System.out.println("1> EMAIL");
			System.out.println("2> ADDRESS");
			System.out.println("3> GO BACK");			
			input = acc.nextInt();
		}
		if (input == 1 ){
			System.out.println("What would like to change your email to?");
			String email = acc.nextLine();
			cust.updateEmail(customer, email);
		}else if (input == 2 ){
			System.out.println("What would like to change your address to?");
			String address = acc.nextLine();
			cust.updateAddress(customer, address);	
		}else{
			//commands(customer);
		}
			
	}
	
}
