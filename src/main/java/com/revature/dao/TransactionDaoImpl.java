package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.pojo.Accounts;
import com.revature.pojo.Transaction;
import com.revature.util.ConnectionFactory;

public class TransactionDaoImpl implements TransactionDao {
	
	Connection connect  = ConnectionFactory.getInstance().getConnection();


	public List<Transaction> getAccountTransaction(Accounts account) {
		List<Transaction> transactions = new ArrayList<Transaction>();
		String sql = "SELECT * FROM TRANSACTION WHERE ACCOUNT_ID = ?";
		
		try {
			PreparedStatement select = ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			select.setInt(1,account.getAccount_id());
			ResultSet output = select.executeQuery();
			

			while(output.next()){
				Transaction transaction  =new Transaction();
				transaction.setTransaction_id(output.getInt(1));
				transaction.setAccount_id(output.getInt(2));
				transaction.setType(output.getString(3));
				transaction.setDate(output.getString(4));
				transactions.add(transaction);
			}
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return transactions;	
	}

	public void addTransaction(Accounts account,String message) {
		String sql = "{call ADD_TRANSACTION (?, ?)}";		
		try{
			connect.setAutoCommit(false);
			
			CallableStatement create = connect.prepareCall(sql);
			create.setInt(1,account.getAccount_id());
			create.setString(2,message);
			create.executeQuery();
			connect.commit();
			connect.close();

		} catch (SQLException e){
			e.printStackTrace();
		}
		
	}




}
