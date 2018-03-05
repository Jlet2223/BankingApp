package test.revature.banker;

import static org.junit.Assert.*;

import org.junit.Test;

import com.revature.banker.Employee;

public class EmployeeTest {

	public static Employee employee = new Employee();
	///Testing Users
	@Test
	public void testNoUserExist(){
		assertEquals("", employee.Users("TEMP"));
	}
	
	@Test
	public void testUserNoInput(){
		assertEquals("", employee.Users(""));
	}
	
	@Test
	public void testUserExist(){
		assertEquals("Customer Found", employee.Users("TEST"));
	}
	
	///Testing Accounts
	@Test
	public void testNoAccountExist(){
		assertEquals("", employee.Accounts("TEMP"));
	}
	@Test
	public void testAccountNoInput(){
		assertEquals("", employee.Users(""));
	}
	
	@Test
	public void testAccountExist(){
		assertEquals("Account Found", employee.Accounts("LIFE"));
	}



}
