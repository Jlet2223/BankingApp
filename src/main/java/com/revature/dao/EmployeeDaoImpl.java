package com.revature.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.revature.pojo.Employee;
import com.revature.util.ConnectionFactory;

public class EmployeeDaoImpl implements EmployeeDao{

	public Employee getEmployee(String username, String password) {
		Employee employee = new Employee();
		
		String sql = "SELECT * FROM EMPLOYEE WHERE USERNAME = ? AND PSSWRD = ?";
		
		try {
			PreparedStatement select = ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			select.setString(1,username);
			select.setString(2, password);
			ResultSet output = select.executeQuery();
			
			while(output.next()){
				employee.setEmployee_id(output.getInt(1));
				employee.setUsername(output.getString(2));
				employee.setPsswrd(output.getString(3));
				employee.setFirstname(output.getString(4));
				employee.setLastname(output.getString(5));
				employee.setAdmin_id(output.getInt(6));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employee;
	}

}
