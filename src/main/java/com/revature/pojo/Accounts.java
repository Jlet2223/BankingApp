package com.revature.pojo;

public class Accounts {

	private int Account_id;
	private String name;
	private float balance;
	private int status_id;
	
	public Accounts(){
		super();
	}
	
	public Accounts(int id , String name , float balance , int status){
		super();
		this.Account_id  = id;
		this.balance = balance;
		this.name = name;
		this.status_id = status;
	}
	
	public int getStatus_id() {
		return status_id;
	}
	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAccount_id() {
		return Account_id;
	}
	public void setAccount_id(int account_id) {
		Account_id = account_id;
	}
}
