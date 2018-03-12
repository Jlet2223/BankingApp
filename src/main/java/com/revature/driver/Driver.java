package com.revature.driver;

import java.util.Scanner;

public class Driver {

	public static void main(String[] args){
		mainconsole();
	}
	
	/////////////////////Main interface for the commands for the user Either the user is a Customer , Employee , or is Leaving
	private static void mainconsole(){
		Scanner input = new Scanner(System.in);
		System.out.println("*********Welcome To The Bank.*********");
		System.out.println("Please enter Banker Type:");
		System.out.println("1> CUSTOMER");
		System.out.println("2> EMPLOYEE");
		System.out.println("3> EXIT");
		int type = input.nextInt();
		while( type != 1 && type != 2 && type != 3 ){
			System.out.println(">>>>>>>>>>>>>>>>>>>>>Invaild Input<<<<<<<<<<<<<<<<<<<<<<<<");
			System.out.println("Please enter Banker Type:");
			System.out.println("1> CUSTOMER");
			System.out.println("2> EMPLOYEE");
			System.out.println("3> EXIT");
			type = input.nextInt();
		}
		if (type == 1 ){
			customer();
			mainconsole();
		}
		else if (type == 2 ){
			employee();
			mainconsole();
		}else{
			System.out.println("Goodbye!");
		}
	}

	//// Calls the login functions from the EmployeeInterface Function
	private static void employee() {
		EmployeeInterface.login();
	}

	///// The customer determines if you are new or a returning customer and based on their options they will be directed to the Customer Interface page;
	private static void customer() {
		boolean customer = true;
		Scanner input = new Scanner(System.in);
		while(customer == true){
			System.out.println("Are you a returning or new Customer?");
			System.out.println("1> NEW");
			System.out.println("2> RETURN");
			System.out.println("3> BACK");
			int type = input.nextInt();
			while( type != 1 && type != 2 && type != 3 ){
				System.out.println(">>>>>>>>>>>>>>>>>>>>>Invaild Input<<<<<<<<<<<<<<<<<<<<<<<<");
				System.out.println("Are you a returning or new Customer?");
				System.out.println("1> NEW");
				System.out.println("2> RETURN");
				System.out.println("3> BACK");
				type = input.nextInt();
			}
			if (type == 1 ){
				CustomerInterface.create();
			}
			else if (type == 2 ){
				CustomerInterface.login();
			}else{
				customer = false;
			}
		}
		mainconsole();
	}
}
