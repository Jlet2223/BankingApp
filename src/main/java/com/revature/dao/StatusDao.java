package com.revature.dao;


import java.util.List;

import com.revature.pojo.Accounts;
import com.revature.pojo.Status;

public interface StatusDao {

	public String checkStatus(Accounts account);
	
	public List<Status> getStatus();

}
