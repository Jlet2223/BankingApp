package com.revature.driver;
import java.util.List;
import java.util.Scanner;

import com.revature.dao.AccountsDao;
import com.revature.dao.AccountsDaoImpl;
import com.revature.dao.CustomerToAccountsDao;
import com.revature.dao.CustomerToAccountsDaoImpl;
import com.revature.dao.CustomersDao;
import com.revature.dao.CustomersDaoImpl;
import com.revature.dao.EmployeeDao;
import com.revature.dao.EmployeeDaoImpl;
import com.revature.dao.TransactionDao;
import com.revature.dao.TransactionDaoImpl;
import com.revature.pojo.Accounts;
import com.revature.pojo.Customer;
import com.revature.pojo.Employee;

public class EmployeeInterface {
	
	
	/////// This is the login form for employees takes in a username and password to be verified
	public static void login() {
		Scanner cre = new Scanner(System.in);
		System.out.println("Please enter your Username:");
		String username = cre.nextLine();
		System.out.println("Please enter your Password:");
		String psswrd = cre.nextLine();
		EmployeeDao custom = new EmployeeDaoImpl();
		Employee employee = custom.getEmployee(username, psswrd);
		if (employee.getUsername() == null){
			System.out.println("Account does not exist." );
		}else{
			commands(employee);
		}
	}

	///////// The mainlist of commands that the employee can run this includes to set of commands based on if the employee is Admin or not;
	private static void commands(Employee employee) {
		if(employee.getAdmin_id() == 0){
			Scanner com = new Scanner(System.in);
			System.out.println("What would you like to do?");
			System.out.println("1> ACCOUNTS");
			System.out.println("2> USERS");
			System.out.println("3> EXIT");
			int input = com.nextInt();
			while(input!= 1 && input!= 2 && input!= 3 ){
				System.out.println(">>>>>>>>>>>>>>>>>>>>>Invaild Input<<<<<<<<<<<<<<<<<<<<<<<<");
				System.out.println("What would you like to do?");
				System.out.println("1> ACCOUNTS");
				System.out.println("2> USERS");
				System.out.println("3> EXIT");
				input = com.nextInt();
			}
			if (input == 1 ){
				getAccounts();
				commands(employee);
			}else if (input == 2 ){
				getUsers();
				commands(employee);
			}else {
				System.out.println("Thank You! Come Again!");

			}
		}else{
			Scanner com = new Scanner(System.in);
			System.out.println("What would you like to do?");
			System.out.println("1> ACCOUNTS");
			System.out.println("2> USERS");
			System.out.println("3> ALTER ACCOUNTS");
			System.out.println("4> TRANSACTIONS");
			System.out.println("5> EXIT");
			int input = com.nextInt();
			while(input!= 1 && input!= 2 && input!= 3  && input != 4 && input !=5){
				System.out.println(">>>>>>>>>>>>>>>>>>>>>Invaild Input<<<<<<<<<<<<<<<<<<<<<<<<");
				System.out.println("What would you like to do?");
				System.out.println("1> ACCOUNTS");
				System.out.println("2> USERS");
				System.out.println("3> ALTER ACCOUNTS");
				System.out.println("4> TRANSACTIONS");
				System.out.println("5> EXIT");
				input = com.nextInt();
			}
			if (input == 1 ){
				getAccounts();
				commands(employee);
			}else if (input == 2 ){
				getUsers();
				commands(employee);
			}else if (input == 3 ){
				getAccountModifications();
				commands(employee);
			}else if (input == 4 ){
				getTransactions(employee);
				commands(employee);
			}else {
				System.out.println("Thank You! Come Again!");

			}
		}
	}

	/////////Pulls up a set of commands that the user can do when wanting to make a micro-transaction
	private static void getTransactions(Employee employee) {
		Scanner com = new Scanner(System.in);
		System.out.println("What would you like to do?");
		System.out.println("1> DEPOSIT");
		System.out.println("2> WITHDRAW");
		System.out.println("3> TRANSFER");
		System.out.println("4> EXIT");
		int input = com.nextInt();
		while(input!= 1 && input!= 2 && input!= 3 && input!= 4 ){
			System.out.println(">>>>>>>>>>>>>>>>>>>>>Invaild Input<<<<<<<<<<<<<<<<<<<<<<<<");
			System.out.println("What would you like to do?");
			System.out.println("1> DEPOSIT");
			System.out.println("2> WITHDRAW");
			System.out.println("3> TRANSFER");
			System.out.println("4> EXIT");
			input = com.nextInt();
		}
		if (input == 1 ){
			deposit();
		}else if (input == 2 ){
			withdraw();
		}else if (input == 3  ){
			transfer();
		}
		
	}

	//////// Pulls up a list of all of the accounts that they can transfer from and into but if the accounts are not open then the request would be impossible to do///
	private static void transfer() {
		AccountsDao act = new AccountsDaoImpl();

		List<Accounts> account = act.getAllAccounts();
		if(account.size() ==0){
			System.out.println("No accounts to withdraw from.");
		}else{
			Scanner com = new Scanner(System.in);
			for(int i = 0; i < account.size();i++){
				System.out.print(">>>>>>>>>" +(i+1) + "> " + account.get(i).getName() + " ||| Balance: " + account.get(i).getBalance() + "<<<<<<<<<");
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
						System.out.print(">>>>>>>>>" +(j+1) + "> " + allaccount.get(j).getName() + " ||| Balance: " + allaccount.get(j).getBalance() + "<<<<<<<<<");
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
							System.out.println(" The From account does not have this much.");
							System.out.println("How much would you like to transfer?");
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

	//////// Pulls up a list of all of the accounts that they can withdraw from but if the account is not open then the request would be impossible to do///
	private static void withdraw() {
		AccountsDao act = new AccountsDaoImpl();
		List<Accounts> account = act.getAllAccounts();
		if(account.size() ==0){
			System.out.println("No accounts to withdraw from.");
		}else{
			Scanner com = new Scanner(System.in);
			for(int i = 0; i < account.size();i++){
				System.out.print(">>>>>>>>>" +(i+1) + "> " + account.get(i).getName() + " ||| Balance: " + account.get(i).getBalance() + "<<<<<<<<<");
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
					amount = com.nextFloat();
				}
				AccountsDao update = new AccountsDaoImpl();
				update.Withdraw(account.get(input-1), amount);
				System.out.println("Withdraw Complete!");
				TransactionDao trans =  new TransactionDaoImpl();
				trans.addTransaction(account.get(input-1), "WITHDRAW OF " + amount);
			}
		}
		
	}

	//////// Pulls up a list of all of the accounts that they can deposit into but if the account is not open then the request would be impossible to do///
	private static void deposit() {
		AccountsDao act = new AccountsDaoImpl();
		List<Accounts> account = act.getAllAccounts();
		if(account.size() ==0){
			System.out.println("No accounts to deposit to.");
		}else{
			Scanner com = new Scanner(System.in);
			for(int i = 0; i < account.size();i++){
				System.out.print(">>>>>>>>>" +(i+1) + "> " + account.get(i).getName() + " ||| Balance: " + account.get(i).getBalance() + "<<<<<<<<<");
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

	//////Set of list of commands that the Admin can use to affect any account that they seem fit.
	private static void getAccountModifications() {
		Scanner com = new Scanner(System.in);
		System.out.println("What would you like to do?");
		System.out.println("1> APPROVE ACCOUNTS");
		System.out.println("2> CANCEL ACCOUNTS");
		System.out.println("3> FREEZE ACOUNTS");
		System.out.println("4> UNFREEZE ACCOUNTS");
		System.out.println("5> JOIN ACCOUNTS");		
		System.out.println("6> Exit");
		int input = com.nextInt();
		while(input!= 1 && input!= 2 && input!= 3 && input!= 4 && input !=5 && input !=6 ){
			System.out.println("Invaild Input");
			System.out.println("1> APPROVE ACCOUNTS");
			System.out.println("2> CANCEL ACCOUNTS");
			System.out.println("3> FREEZE ACOUNTS");
			System.out.println("4> UNFREEZE ACCOUNTS");
			System.out.println("5> JOIN ACCOUNTS");		
			System.out.println("6> Exit");
			input = com.nextInt();
		}
		if (input == 1 ){
			approve();
		}else if (input == 2 ){
			cancel();
		}else if (input == 3  ){
			freeze();
		}else if (input ==4){
			unfreeze();
		}else if (input ==5){
			joinAccount();
		}
		
		
	}

	/////Option for the admin to join account with Different Customers
	private static void joinAccount() {
		AccountsDao custom = new AccountsDaoImpl();
		List<Accounts> allAccounts = custom.getAllOpenAccounts();
		if(allAccounts.size() ==0){
			System.out.println("No accounts to join.");
		}else{
			Scanner com = new Scanner(System.in);
			for(int i = 0; i < allAccounts.size();i++){
				System.out.print(">>>>>>>>>" +(i+1) + "> " + allAccounts.get(i).getName() );
			}
			System.out.println("What account would you like to join?");
			int input = com.nextInt();
			while(input > allAccounts.size()){
				System.out.println(">>>>>>>>>>>>>>>>>>>>>Invaild Input<<<<<<<<<<<<<<<<<<<<<<<<");
				System.out.println("What account would you like to join?");
				input = com.nextInt();
			}
			
			CustomersDao customer = new CustomersDaoImpl();
			List<Customer> allCustomer = customer.getAllCustomers();
			for(int i = 0; i < allCustomer.size(); i++){
				System.out.println((i+1) + "> " + allCustomer.get(i).getUsername());
			}
			System.out.println("What customer would you like to join to the account?");
			int input2 = com.nextInt();
			while(input2 > allCustomer.size()){
				System.out.println(">>>>>>>>>>>>>>>>>>>>>Invaild Input<<<<<<<<<<<<<<<<<<<<<<<<");
				System.out.println("What customer would you like to join to the account?");
				input2 = com.nextInt();
			}
			CustomerToAccountsDao reference = new CustomerToAccountsDaoImpl();
			reference.joinAccount(allAccounts.get(input-1), allCustomer.get(input2-1));
		}
		
		
	}

	//////Option for the user to approve any account that is in pending and make them Open;
	private static void approve() {
		AccountsDao custom = new AccountsDaoImpl();
		List<Accounts> allAccounts = custom.getAllPendingAccounts();
		if(allAccounts.size() ==0){
			System.out.println("No accounts to approve.");
		}else{
			Scanner com = new Scanner(System.in);
			for(int i = 0; i < allAccounts.size();i++){
				System.out.print(">>>>>>>>>" +(i+1) + "> " + allAccounts.get(i).getName() );
			}
			System.out.println("What account would you like to approve?");
			int input = com.nextInt();
			while(input > allAccounts.size()){
				System.out.println(">>>>>>>>>>>>>>>>>>>>>Invaild Input<<<<<<<<<<<<<<<<<<<<<<<<");
				System.out.println("What account would you like to approve?");
				input = com.nextInt();
			}
			custom.approveAccount(allAccounts.get(input-1));
		}
		
	}

	//////Option for the user to approve any account that is open and make them Closed;
	private static void cancel() {
		AccountsDao custom = new AccountsDaoImpl();
		List<Accounts> allAccounts = custom.getAllOpenAccounts();	
		if(allAccounts.size() ==0){
			System.out.println("No accounts to cancel.");
		}else{
			Scanner com = new Scanner(System.in);
			for(int i = 0; i < allAccounts.size();i++){
				System.out.print(">>>>>>>>>" +(i+1) + "> " + allAccounts.get(i).getName() );
			}
			System.out.println("What account would you like to cancel?");
			int input = com.nextInt();
			while(input > allAccounts.size()){
				System.out.println(">>>>>>>>>>>>>>>>>>>>>Invaild Input<<<<<<<<<<<<<<<<<<<<<<<<");
				System.out.println("What account would you like to cancel?");
				input = com.nextInt();
			}
			custom.closeAccount(allAccounts.get(input-1));
		}
	}

	//////Option for the user to approve any account that is open and make them Frozen;
	private static void freeze() {
		AccountsDao custom = new AccountsDaoImpl();
		List<Accounts> allAccounts = custom.getAllOpenAccounts();
		if(allAccounts.size() ==0){
			System.out.println("No accounts to freeze.");
		}else{
			Scanner com = new Scanner(System.in);
			for(int i = 0; i < allAccounts.size();i++){
				System.out.print(">>>>>>>>>" +(i+1) + "> " + allAccounts.get(i).getName() );
			}
			System.out.println("What account would you like to freeze?");
			int input = com.nextInt();
			while(input > allAccounts.size()){
				System.out.println(">>>>>>>>>>>>>>>>>>>>>Invaild Input<<<<<<<<<<<<<<<<<<<<<<<<");
				System.out.println("What account would you like to freeze?");
				input = com.nextInt();
			}
			custom.freezeAccount(allAccounts.get(input-1));
		}
	}

	//////Option for the user to approve any account that is Frozen and make them Open;
	private static void unfreeze() {
		AccountsDao custom = new AccountsDaoImpl();
		List<Accounts> allAccounts = custom.getAllFrozenAccounts();	
		if(allAccounts.size() ==0){
			System.out.println("No accounts to unfreeze.");
		}else{
			Scanner com = new Scanner(System.in);
			for(int i = 0; i < allAccounts.size();i++){
				System.out.print(">>>>>>>>>" +(i+1) + "> " + allAccounts.get(i).getName() );
			}
			System.out.println("What account would you like to unfreeze?");
			int input = com.nextInt();
			while(input > allAccounts.size()){
				System.out.println(">>>>>>>>>>>>>>>>>>>>>Invaild Input<<<<<<<<<<<<<<<<<<<<<<<<");
				System.out.println("What account would you like to unfreeze?");
				input = com.nextInt();
			}
			custom.unfreezeAccount(allAccounts.get(input-1));
		}
	}

	//////////Displays all accounts with the balances and the status of each of them
	private static void getAccounts() {
		AccountsDao custom = new AccountsDaoImpl();
		List<Accounts> allAccounts = custom.getAllAccounts();
		for(int i = 0; i < allAccounts.size(); i++){
			System.out.print(">>>>>>>>>" +(i+1) + "> " + allAccounts.get(i).getName() + " ||| Balance: " + allAccounts.get(i).getBalance() );
			if(allAccounts.get(i).getStatus_id() ==1){
				System.out.println(" Status: Pending  <<<<<<<<<");
			}else if(allAccounts.get(i).getStatus_id() ==2){
				System.out.println(" Status: Open <<<<<<<<<");			
			}else if(allAccounts.get(i).getStatus_id() ==3){
				System.out.println(" Status: Frozen <<<<<<<<<");			
			}else{
				System.out.println(" Status: Closed <<<<<<<<<");				
			}
		}
	}

	/////////Displays all the Customers that are registered.
	private static void getUsers() {
		CustomersDao custom = new CustomersDaoImpl();
		List<Customer> allCustomer = custom.getAllCustomers();
		for(int i = 0; i < allCustomer.size(); i++){
			System.out.print(">>>>>>>>>" +(i+1) + "> " + allCustomer.get(i).getUsername() + " ||| Name: " + allCustomer.get(i).getFirstname() + " " + allCustomer.get(i).getLastname()  );
		}
	}
}
