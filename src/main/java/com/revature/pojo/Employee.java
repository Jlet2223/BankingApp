package com.revature.pojo;

public class Employee {

	private int Employee_id;
	private String username;
	private String psswrd;
	private String firstname;
	private String lastname;
	private int admin_id;
	
	public Employee(){
		super();
	}

	public Employee( int Employee_id ,String username,String password,String firstname,String lastname,int admin){
		super();
		this.setEmployee_id(Employee_id);
		this.setUsername(username);
		this.setPsswrd(password);
		this.setFirstname(firstname);
		this.setLastname(lastname);
		this.setAdmin_id(admin);
	}
	
	public int getEmployee_id() {
		return Employee_id;
	}
	public void setEmployee_id(int Employee_id) {
		this.Employee_id = Employee_id;
	}
	public String getUsername() {
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
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public int getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(int admin_id) {
		this.admin_id = admin_id;
	}


	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
}
