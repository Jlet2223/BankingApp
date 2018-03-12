package com.revature.dao;

import java.util.List;

import com.revature.pojo.Accounts;
import com.revature.pojo.Customer;
import com.revature.pojo.CustomerToAccounts;

public interface CustomerToAccountsDao {

	public List<Accounts> getCustomerAccounts(Customer customer);
	
	public void joinAccount(Accounts account, Customer customer);
	
}
