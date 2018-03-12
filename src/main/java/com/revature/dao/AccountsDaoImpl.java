package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.pojo.Accounts;
import com.revature.util.ConnectionFactory;

public class AccountsDaoImpl implements AccountsDao {
	
	Connection connect  = ConnectionFactory.getInstance().getConnection();
	


	public void createAccount(Accounts account) {

		String sql = "INSERT INTO ACCOUNT (ACCOUNT_NAME , BALANCE , STATUS_ID) VALUES (? , DEFAULT , 1)";
		
		try{
			connect.setAutoCommit(false);
			
			PreparedStatement create = connect.prepareStatement(sql);
			create.setString(1,account.getName());
			create.executeQuery();
			connect.commit();
			connect.close();

		} catch (SQLException e){
			e.printStackTrace();
		}
	}

	public void closeAccount(Accounts account) {
		String sql = "UPDATE ACCOUNT SET STATUS_ID = ? WHERE ACCOUNT_ID = ?";
		try {
			connect.setAutoCommit(false);
			PreparedStatement update = ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			update.setFloat(1, 4);	
			update.setInt(2, account.getAccount_id());
			update.executeQuery();
			connect.commit();
			connect.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Withdraw(Accounts account, float amount) {
		String sql = "UPDATE ACCOUNT SET BALANCE = ? WHERE ACCOUNT_ID = ?";
		try {
			connect.setAutoCommit(false);
			PreparedStatement update = ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			update.setFloat(1, account.getBalance()-amount);	
			update.setInt(2, account.getAccount_id());
			update.executeQuery();
			connect.commit();
			connect.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void Deposit(Accounts account, float amount) {
		String sql = "UPDATE ACCOUNT SET BALANCE = ? WHERE ACCOUNT_ID = ?";
		try {
			connect.setAutoCommit(false);
			PreparedStatement update = ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			update.setFloat(1, account.getBalance()+amount);	
			update.setInt(2, account.getAccount_id());
			update.executeQuery();
			connect.commit();
			connect.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void Transfer(Accounts accountfrom, Accounts accountto, float amount) {
		String sql = "UPDATE ACCOUNT SET BALANCE = ? WHERE ACCOUNT_ID = ?";
		try {
			connect.setAutoCommit(false);
			PreparedStatement update = ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			update.setFloat(1, accountfrom.getBalance()-amount);	
			update.setInt(2, accountfrom.getAccount_id());
			update.executeQuery();
			
			update = ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			update.setFloat(1, accountfrom.getBalance()+amount);	
			update.setInt(2, accountfrom.getAccount_id());
			update.executeQuery();
			connect.commit();
			connect.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public Accounts getAccount(int id) {
		Accounts account = new Accounts();
		
		String sql = "SELECT * FROM ACCOUNT WHERE ACCOUNT_ID = ?";
		
		try {
			connect.setAutoCommit(false);
			PreparedStatement select = ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			select.setInt(1, id);			
			ResultSet output = select.executeQuery();
			
			while(output.next()){
				account.setAccount_id(output.getInt(1));
				account.setName(output.getString(2));
				account.setBalance(output.getFloat(3));
				account.setStatus_id(output.getInt(4));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return account;
	}

	public List<Accounts> getOtherAccounts(Accounts accounts) {
		List<Accounts>  otheraccount = new ArrayList<Accounts>();
		String sql = "SELECT * FROM ACCOUNT WHERE NOT ACCOUNT_ID = ?";
		
		try {
			connect.setAutoCommit(false);
			PreparedStatement select = ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			select.setInt(1, accounts.getAccount_id());			
			ResultSet output = select.executeQuery();
			
			while(output.next()){
				Accounts account = new Accounts();
				account.setAccount_id(output.getInt(1));
				account.setName(output.getString(2));
				account.setBalance(output.getFloat(3));
				account.setStatus_id(output.getInt(4));
				otheraccount.add(account);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return otheraccount;
	}

	public List<Accounts> getAllAccounts() {
		List<Accounts>  accounts = new ArrayList<Accounts>();
		String sql = "SELECT * FROM ACCOUNT ";	
		try {
			connect.setAutoCommit(false);
			PreparedStatement select = ConnectionFactory.getInstance().getConnection().prepareStatement(sql);			
			ResultSet output = select.executeQuery();
			
			while(output.next()){
				Accounts account = new Accounts();
				account.setAccount_id(output.getInt(1));
				account.setName(output.getString(2));
				account.setBalance(output.getFloat(3));
				account.setStatus_id(output.getInt(4));
				accounts.add(account);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accounts;
	}

	public void approveAccount(Accounts account) {
		String sql = "UPDATE ACCOUNT SET STATUS_ID = ? WHERE ACCOUNT_ID = ?";
		try {
			connect.setAutoCommit(false);
			PreparedStatement update = ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			update.setFloat(1, 2);	
			update.setInt(2, account.getAccount_id());
			update.executeQuery();
			connect.commit();
			connect.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void freezeAccount(Accounts account) {
		String sql = "UPDATE ACCOUNT SET STATUS_ID = ? WHERE ACCOUNT_ID = ?";
		try {
			connect.setAutoCommit(false);
			PreparedStatement update = ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			update.setFloat(1, 3);	
			update.setInt(2, account.getAccount_id());
			update.executeQuery();
			connect.commit();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void unfreezeAccount(Accounts account) {
		String sql = "UPDATE ACCOUNT SET STATUS_ID = ? WHERE ACCOUNT_ID = ?";
		try {
			connect.setAutoCommit(false);
			PreparedStatement update = ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			update.setFloat(1, 2);	
			update.setInt(2, account.getAccount_id());
			update.executeQuery();
			connect.commit();
			
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public List<Accounts> getAllPendingAccounts() {
		List<Accounts>  accounts = new ArrayList<Accounts>();
		String sql = "SELECT * FROM ACCOUNT WHERE STATUS_ID = 1";
		
		try {
			connect.setAutoCommit(false);
			PreparedStatement select = ConnectionFactory.getInstance().getConnection().prepareStatement(sql);			
			ResultSet output = select.executeQuery();
			
			while(output.next()){
				Accounts account  = new Accounts();
				account.setAccount_id(output.getInt(1));
				account.setName(output.getString(2));
				account.setBalance(output.getFloat(3));
				account.setStatus_id(output.getInt(4));
				accounts.add(account);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accounts;
	}

	public List<Accounts> getAllFrozenAccounts() {
		List<Accounts>  accounts = new ArrayList<Accounts>();
		String sql = "SELECT * FROM ACCOUNT WHERE STATUS_ID = 3";
		
		try {
			connect.setAutoCommit(false);
			PreparedStatement select = ConnectionFactory.getInstance().getConnection().prepareStatement(sql);			
			ResultSet output = select.executeQuery();
			
			while(output.next()){
				Accounts account  = new Accounts();	
				account.setAccount_id(output.getInt(1));
				account.setName(output.getString(2));
				account.setBalance(output.getFloat(3));
				account.setStatus_id(output.getInt(4));
				accounts.add(account);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accounts;
	}

	public List<Accounts> getAllOpenAccounts() {
		List<Accounts>  accounts = new ArrayList<Accounts>();
		String sql = "SELECT * FROM ACCOUNT WHERE STATUS_ID = 2";
		
		try {
			connect.setAutoCommit(false);
			PreparedStatement select = ConnectionFactory.getInstance().getConnection().prepareStatement(sql);			
			ResultSet output = select.executeQuery();
			
			while(output.next()){
				Accounts account  = new Accounts();		
				account.setAccount_id(output.getInt(1));
				account.setName(output.getString(2));
				account.setBalance(output.getFloat(3));
				account.setStatus_id(output.getInt(4));
				accounts.add(account);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accounts;
	}

	public int getAccountid(Accounts account) {
	String sql = "SELECT ACCOUNT_ID FROM ACCOUNT WHERE ACCOUNT_NAME = ?";
	int returnval = 0;	
	try {
		connect.setAutoCommit(false);
		PreparedStatement select = ConnectionFactory.getInstance().getConnection().prepareStatement(sql);	
		select.setString(1, account.getName());	

		ResultSet output = select.executeQuery();
		
		while(output.next()){
			returnval = output.getInt(1);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return returnval;
	}

}
