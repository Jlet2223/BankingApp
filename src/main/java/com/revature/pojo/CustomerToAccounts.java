package com.revature.pojo;

public class CustomerToAccounts {

	private int customer_id;
	private int account_id;
	
	public CustomerToAccounts(){
		super();
	}
	
	public CustomerToAccounts(int cust_id, int account_id){
		
		this.setCustomer_id(cust_id);
		this.setAccount_id(account_id);
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public int getAccount_id() {
		return account_id;
	}

	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
}
