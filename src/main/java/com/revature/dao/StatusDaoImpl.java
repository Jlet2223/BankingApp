package com.revature.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.pojo.Accounts;
import com.revature.pojo.Status;
import com.revature.util.ConnectionFactory;

public class StatusDaoImpl implements StatusDao{

	public String checkStatus(Accounts account) {
		
		String status = new String();
		
		String sql = "SELECT * FROM STATUS WHERE STATUS_ID = ?";
		
		try {
			PreparedStatement select = ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			select.setInt(1,account.getStatus_id());
			ResultSet output = select.executeQuery();
			
			while(output.next()){
				status = output.getString(2);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	public List<Status> getStatus() {
		List<Status> stat = new ArrayList<Status>();
		String sql = "SELECT * FROM STATUS";
		
		try {
			PreparedStatement select = ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			ResultSet output = select.executeQuery();
			
			while(output.next()){
				Status statuses = new Status();
				statuses.setStatus_id(output.getInt(1));
				statuses.setType(output.getString(2));
				stat.add(statuses);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stat;
	}

	
}
