package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.pojo.Accounts;
import com.revature.pojo.Customer;
import com.revature.util.ConnectionFactory;

public class CustomerToAccountsDaoImpl implements CustomerToAccountsDao {
	
	Connection connect  = ConnectionFactory.getInstance().getConnection();


	public List<Accounts> getCustomerAccounts(Customer customer) {
		List<Accounts> accounts = new ArrayList<Accounts>();

		String sql = "SELECT ACCOUNT.ACCOUNT_ID ,ACCOUNT.ACCOUNT_NAME , ACCOUNT.BALANCE , ACCOUNT.STATUS_ID FROM ACCOUNT JOIN CUSTOMER_TO_ACCOUNT ON ACCOUNT.ACCOUNT_ID = CUSTOMER_TO_ACCOUNT.ACCOUNT_ID WHERE CUSTOMER_ID = ?";
		
		try {
			PreparedStatement select = ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			select.setInt(1, customer.getCustomer_id());			
			ResultSet output = select.executeQuery();
			
			while(output.next()){
				Accounts account = new Accounts();
				account.setAccount_id(output.getInt(1));
				account.setName(output.getString(2));
				account.setBalance(output.getFloat(3));
				account.setStatus_id(output.getInt(4));
				accounts.add(account);
			}
			connect.close();

		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		return accounts;
	}
	

	public void joinAccount(Accounts account, Customer customer) {
		String sql = "INSERT INTO CUSTOMER_TO_ACCOUNT VALUES (? , ? )";
		try{
			connect.setAutoCommit(false);
			PreparedStatement create = connect.prepareStatement(sql);
			create.setInt(1,customer.getCustomer_id());
			create.setInt(2,account.getAccount_id());
			create.executeQuery();
			connect.commit();
			connect.close();

		} catch (SQLException e){
			e.printStackTrace();
		}
	}



}
