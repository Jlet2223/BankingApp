package com.revature.pojo;

public class Status {

	private int status_id;
	private String type;
	
	public Status(){
		super();
	}
	
	public Status(int status_id , String type){
		super();
		this.status_id = status_id;
		this.type = type;
	}

	public int getStatus_id() {
		return status_id;
	}

	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
