package com.revature.dao;

import java.util.List;

import com.revature.pojo.Accounts;

public interface AccountsDao {
	
	public void createAccount(Accounts account);
	
	public void closeAccount(Accounts account);
	
	public void approveAccount(Accounts account);
	
	public void freezeAccount(Accounts account);
	
	public void unfreezeAccount(Accounts account);
	
	public void Withdraw(Accounts account, float amount);
	
	public void Deposit(Accounts account, float amount);
	
	public void Transfer(Accounts accountfrom, Accounts accountto , float amount);
	
	public Accounts getAccount(int id);
	
	public int getAccountid(Accounts account);
	
	public List<Accounts> getOtherAccounts(Accounts account);
	
	public List<Accounts> getAllAccounts();
	
	public List<Accounts> getAllPendingAccounts();
	
	public List<Accounts> getAllFrozenAccounts();
	
	public List<Accounts> getAllOpenAccounts();

}
