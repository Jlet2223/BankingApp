package com.revature.dao;

import java.util.List;

import com.revature.pojo.Accounts;
import com.revature.pojo.Transaction;

public interface TransactionDao {

	public List<Transaction> getAccountTransaction(Accounts account);
	
	public void addTransaction(Accounts account , String message);
		

}
