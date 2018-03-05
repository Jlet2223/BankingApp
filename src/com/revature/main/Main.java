package com.revature.main;



import java.util.Scanner;

import com.revature.banker.Admin;
import com.revature.banker.Customer;
import com.revature.banker.Employee;
import com.revature.util.LoggingUtil;

public class Main {
	
	public static void main(String[] args){
		mainconsole();
	}
	
	private static void mainconsole(){
		Scanner input = new Scanner(System.in);
		System.out.println("Welcome To The Bank.");
		System.out.println("Please enter Banker Type:(ADMIN, EMPLOYEE, CUSTOMER,EXIT)");
		String type = input.nextLine().toUpperCase();
		while( !type.equals("ADMIN") && !type.equals("EMPLOYEE") && !type.equals("CUSTOMER") && !type.equals("EXIT")){
			System.out.println("Invaild Input");
			System.out.println("Please enter Banker Type:(ADMIN, EMPLOYEE, CUSTOMER,EXIT)");
			type = input.nextLine().toUpperCase();
		}
		if (type.equals("ADMIN") ){
			admin();
		}
		else if (type.equals("EMPLOYEE") ){
			employee();
		}
		else if (type.equals("CUSTOMER") ){
			customer();
		}else{
			System.out.println("Goodbye!");
			LoggingUtil.logInfo("Application Exited");
		}
	}

	//// No option for employee log-in as the employee
	private static void employee() {
		Employee worker = new Employee();
		worker.commands();
		mainconsole();
	}

	//// No option for  admin log-in 
	private static void admin() {
		Admin admin = new Admin();
		admin.commands();
		mainconsole();
	}
	///// Needed more for Customer because their usersnames needed to be verified first before any progress can be made
	private static void customer() {
		boolean customer = true;
		Scanner input = new Scanner(System.in);
		while(customer == true){
			System.out.println("Are you a returning or new Customer?(NEW, RETURN,BACK)");
			String type = input.nextLine().toUpperCase();
			while( !type.equals("NEW") && !type.equals("RETURN")  && !type.equals("BACK")){
				System.out.println("Invaild Input");
				System.out.println("Please enter if you are new or returning:(NEW, RETURN, BACK)");
				type = input.nextLine().toUpperCase();
			}
			if (type.equals("BACK")){
				customer = false;
			} else if (type.equals("NEW")){
				Customer.create();
				System.out.println("Now Please Log-in under Return.");
				LoggingUtil.logInfo("Customer Created");
			} else if (type.equals("RETURN")){
				System.out.println("Please enter your Username:");
				String username = input.nextLine().toUpperCase();
				System.out.println("Please enter your Password:");
				String password = input.nextLine().toUpperCase();
				if (Customer.verify(username, password) == false){
					System.out.println("Account does not exist.");
				}
				else{
					Customer custom = Customer.findcustomer(username);
					custom.commands();
					customer = false;
					LoggingUtil.logInfo("Customer Found");

				}
			}

		}
		mainconsole();
	}

}
