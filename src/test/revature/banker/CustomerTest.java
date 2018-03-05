package test.revature.banker;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.revature.account.Accounts;
import com.revature.banker.Admin;
import com.revature.banker.Customer;
import com.revature.serial.Serial;

public class CustomerTest {

	
	///Testing findcustomer
	@Test
	public void testNoUserExist(){
		assertEquals(null, Customer.findcustomer("TEMP"));
	}
	
	@Test
	public void testUserNoInput(){
		assertEquals(null, Customer.findcustomer(""));
	}
	
	@Test
	public void testUserExist(){
		List<Customer > customers = Serial.customerdeserial();
		Customer customer = customers.get(0);
		assertEquals(customer.getUsername(), Customer.findcustomer("TEST").getUsername());
		assertEquals(customer.getName(), Customer.findcustomer("TEST").getName());
		assertEquals(customer.getAccounts(), Customer.findcustomer("TEST").getAccounts());
	}
	
	///Testing verify
	@Test
	public void testNoCustomerVerify(){
		assertFalse(Customer.verify("Tester", "123"));
	}
	@Test
	public void testNoCustomerVerify2(){
		assertFalse(Customer.verify("", ""));
	}
	
	@Test
	public void testCustomerVerify(){
		assertTrue(Customer.verify("TEST", "123"));	
	}
	
	
	////Testing Withdrawfrom + DepositTo
	@Test
	public void testSuccesfulTransfer(){
		
		List<Customer > customers = Serial.customerdeserial();
		Accounts testaccount = new Accounts("TESTER" , "TEST", 124 ,"");
		Accounts testaccount2 = new Accounts("MESTER" , "TEST", 0 ,"");
		customers.get(0).getAccounts().add(testaccount);
		customers.get(0).getAccounts().add(testaccount2);
		Serial.customerserial(customers);
		customers.get(0).withdrawfrom(customers.get(0),customers.get(0) ,"TESTER",  "MESTER", 50);
		customers = Serial.customerdeserial();
		assertEquals(74 ,customers.get(0).getAccounts().get(0).getBalance());

		assertEquals(50 ,customers.get(0).getAccounts().get(1).getBalance());
		Admin admin = new Admin();
		admin.Delete("TESTER");
		admin.Delete("MESTER");
	}
	
	@Test
	public void testUnsucessfulTransfer(){
		List<Customer > customers = Serial.customerdeserial();
		Accounts testaccount = new Accounts("TESTER" , "TEST", 0 ,"");
		Accounts testaccount2 = new Accounts("MESTER" , "TEST", 0 ,"");
		customers.get(0).getAccounts().add(testaccount);
		customers.get(0).getAccounts().add(testaccount2);
		Serial.customerserial(customers);
		customers.get(0).withdrawfrom(customers.get(0),customers.get(0) ,"TESTER",  "MESTER", 50);
		customers = Serial.customerdeserial();
		assertEquals(0 ,customers.get(0).getAccounts().get(0).getBalance());

		assertEquals(0 ,customers.get(0).getAccounts().get(1).getBalance());
		Admin admin = new Admin();

		admin.Delete("TESTER");
		admin.Delete("MESTER");	}
	////Testing Withdraw
	@Test
	public void testSuccesfulWithdraw(){
		List<Customer > customers = Serial.customerdeserial();
		Accounts testaccount = new Accounts("TESTER" , "TEST", 100 ,"");
		customers.get(0).getAccounts().add(testaccount);
		Serial.customerserial(customers);
		customers.get(0).withdraw("TESTER", 50);
		customers = Serial.customerdeserial();
		assertEquals(50 ,customers.get(0).getAccounts().get(0).getBalance());
		Admin admin = new Admin();
		admin.Delete("TESTER");

	}
	
	@Test
	public void testUnsuccesfulWithdraw(){
		List<Customer > customers = Serial.customerdeserial();
		Accounts testaccount = new Accounts("TESTER" , "ESTER", 100 ,"");
		customers.get(0).getAccounts().add(testaccount);
		Serial.customerserial(customers);
		customers.get(0).withdraw("TESTER", 150);
		customers = Serial.customerdeserial();
		assertEquals(100 ,customers.get(0).getAccounts().get(0).getBalance());
		Admin admin = new Admin();
		admin.Delete("TESTER");


	}
	////Testing Deposit
	@Test
	public void testSuccessfulDeposit(){
		List<Customer > customers = Serial.customerdeserial();
		Accounts testaccount = new Accounts("TESTER" , "ESTER", 100 ,"");
		customers.get(0).getAccounts().add(testaccount);
		Serial.customerserial(customers);
		Admin.deposit("TESTER", 150);
		customers = Serial.customerdeserial();
		assertEquals(250 ,customers.get(0).getAccounts().get(0).getBalance());
		Admin admin = new Admin();
		admin.Delete("TESTER");
	}



}
