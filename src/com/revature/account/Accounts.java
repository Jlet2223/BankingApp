package com.revature.account;

import java.io.Serializable;

public class Accounts implements Serializable{

	private static final long serialVersionUID = 3092539105119963047L;
	
	private String name;
	private int balance;
	private String username;
	private String joint;
	
	public Accounts(String name ,String username, int balance , String joint){
		 this.setName(name);
		 this.setBalance(balance);
		 this.setUsername(username);
		 this.setJoint(joint);
	}
	public String toString(){
		return "Name: " + name + " Balance: " + balance + " Owners: " + username + "," + joint;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJoint() {
		return joint;
	}
	public void setJoint(String joint) {
		this.joint = joint;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
