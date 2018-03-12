package com.revature.pojo;

public class Transaction {
	
	private int transaction_id;
	private int account_id;
	private String type;
	private String date;
	
	public Transaction(){
		super();
	}
	public Transaction(int transaction_id,int account_id, String type, String date ){
		super();
		this.transaction_id = transaction_id;
		this.account_id = account_id;
		this.type = type;
		this.date = date;
	}
	
	public int getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(int transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getAccount_id() {
		return account_id;
	}
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}

}
