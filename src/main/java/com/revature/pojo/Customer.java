package com.revature.pojo;

public class Customer {

	private int Customer_id;
	private  String username;
	private String psswrd;
	private String firstname;
	private String lastname;
	private String email;
	private String address;
	
	public Customer(){
		super();
	}
	
	public Customer( int Customer_id ,String username,String password,String firstname,String lastname,String email,String address){
		super();
		this.setCustomer_id(Customer_id);
		this.setUsername(username);
		this.setPsswrd(password);
		this.setFirstname(firstname);
		this.setLastname(lastname);
		this.setEmail(email);
		this.setAddress(address);
	}

	public int getCustomer_id() {
		return Customer_id;
	}

	public void setCustomer_id(int customer_id) {
		Customer_id = customer_id;
	}

	public  String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPsswrd() {
		return psswrd;
	}

	public void setPsswrd(String psswrd) {
		this.psswrd = psswrd;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
