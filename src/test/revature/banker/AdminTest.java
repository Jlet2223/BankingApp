package test.revature.banker;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.revature.account.Accounts;
import com.revature.banker.Admin;
import com.revature.banker.Customer;
import com.revature.serial.Serial;

public class AdminTest {

	public static Admin admin = new Admin();
	
	///Testing Users
	@Test
	public void testNoUserExist(){
		assertEquals("", admin.Users("TEMP"));
	}
	
	@Test
	public void testUserNoInput(){
		assertEquals("", admin.Users(""));
	}
	
	@Test
	public void testUserExist(){
		assertEquals("Customer Found", admin.Users("TEST"));
	}
	
	///Testing Accounts
	@Test
	public void testNoAccountExist(){
		assertEquals("", admin.Accounts("TEMP"));
	}
	@Test
	public void testAccountNoInput(){
		assertEquals("", admin.Users(""));
	}
	
	@Test
	public void testAccountExist(){
		assertEquals("Account Found", admin.Accounts("LIFE"));
	}
	
	////Testing Delete
	@Test
	public void testAccountDeleted(){
		List<Customer > customers = Serial.customerdeserial();
		Accounts testaccount = new Accounts("TESTER" , "CODED", 124 ,"NOLI");
		customers.get(0).getAccounts().add(testaccount);
		Serial.customerserial(customers);
		admin.Delete("TESTER");
		customers = Serial.customerdeserial();
		assertFalse(Admin.accountexists(customers, "TESTER"));
	}
	
	@Test
	public void testAccountNotDeleted(){
		assertFalse(admin.Delete("NOONE"));
	}
	
	////Testing GetCustomer
	@Test
	public void testGotCustomer(){
		List<Customer > customers = Serial.customerdeserial();
		Accounts testaccount = new Accounts("TESTER" , "STEVE", 124 ,"");
		List<Accounts> listaccount = new ArrayList<Accounts>();
		listaccount.add(testaccount);
		Customer customer = new Customer("ESTER" , "123", "STEVE", listaccount);
		customers.add(customer);
		assertEquals(customer,Admin.getcustomer(customers,"TESTER"));
		admin.Delete("ESTER");
	}
	
	@Test
	public void testNoCustomer(){
		List<Customer > customers = Serial.customerdeserial();
		assertEquals(null,Admin.getcustomer(customers,"TESTER"));
	}
	
	////Testing Withdrawfrom + DepositTo
	@Test
	public void testSuccesfulTransfer(){
		
		List<Customer > customers = Serial.customerdeserial();
		Accounts testaccount = new Accounts("TESTER" , "ESTER", 124 ,"");
		Accounts testaccount2 = new Accounts("MESTER" , "ESTER", 0 ,"");
		customers.get(0).getAccounts().add(testaccount);
		customers.get(0).getAccounts().add(testaccount2);
		Serial.customerserial(customers);
		Admin.WithdrawFrom(customers.get(0), "TESTER", customers.get(0), "MESTER", 50);
		customers = Serial.customerdeserial();
		int testindex = Customer.existaccount("TESTER", Admin.getcustomer(customers, "MESTER").getAccounts());
		int testindex2 = Customer.existaccount("MESTER", Admin.getcustomer(customers, "MESTER").getAccounts());
		assertEquals(50 ,Admin.getcustomer(customers, "MESTER").getAccounts().get(testindex2).getBalance());

		assertEquals(74 ,Admin.getcustomer(customers, "TESTER").getAccounts().get(testindex).getBalance());
		
		admin.Delete("TESTER");
		admin.Delete("MESTER");
		
	}
	
	@Test
	public void testUnsucessfulTransfer(){

		List<Customer > customers = Serial.customerdeserial();
		Accounts testaccount = new Accounts("TESTER" , "ESTER", 0 ,"");
		Accounts testaccount2 = new Accounts("MESTER" , "ESTER", 0 ,"");
		customers.get(0).getAccounts().add(testaccount);
		customers.get(0).getAccounts().add(testaccount2);
		Serial.customerserial(customers);
		Admin.WithdrawFrom(customers.get(0), "TESTER", customers.get(0), "MESTER", 50);
		customers = Serial.customerdeserial();
		int testindex = Customer.existaccount("TESTER", Admin.getcustomer(customers, "TESTER").getAccounts());
		int testindex2 = Customer.existaccount("MESTER", Admin.getcustomer(customers, "MESTER").getAccounts());
		assertEquals(0 ,Admin.getcustomer(customers, "MESTER").getAccounts().get(testindex2).getBalance());

		assertEquals(0 ,Admin.getcustomer(customers, "TESTER").getAccounts().get(testindex).getBalance());

		admin.Delete("TESTER");
		admin.Delete("MESTER");
	}
	////Testing Withdraw
	@Test
	public void testSuccesfulWithdraw(){
		List<Customer > customers = Serial.customerdeserial();
		Accounts testaccount = new Accounts("TESTER" , "ESTER", 100 ,"");
		customers.get(0).getAccounts().add(testaccount);
		Serial.customerserial(customers);
		admin.withdraw("TESTER", 50);
		customers = Serial.customerdeserial();
		int testindex = Customer.existaccount("TESTER", Admin.getcustomer(customers, "TESTER").getAccounts());
		assertEquals(50 ,Admin.getcustomer(customers, "TESTER").getAccounts().get(testindex).getBalance());
		
		admin.Delete("TESTER");

	}
	
	@Test
	public void testUnsuccesfulWithdraw(){
		List<Customer > customers = Serial.customerdeserial();
		Accounts testaccount = new Accounts("TESTER" , "ESTER", 100 ,"");
		customers.get(0).getAccounts().add(testaccount);
		Serial.customerserial(customers);
		admin.withdraw("TESTER", 150);
		customers = Serial.customerdeserial();
		int testindex = Customer.existaccount("TESTER", Admin.getcustomer(customers, "TESTER").getAccounts());
		assertEquals(100 ,Admin.getcustomer(customers, "TESTER").getAccounts().get(testindex).getBalance());
		
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
		int testindex = Customer.existaccount("TESTER", Admin.getcustomer(customers, "TESTER").getAccounts());
		assertEquals(250 ,Admin.getcustomer(customers, "TESTER").getAccounts().get(testindex).getBalance());
		
		admin.Delete("TESTER");
	}
	
	////Testing Deny
	@Test
	public void testRequestEmpty(){
		List<Accounts > request = Serial.accountdeserial();
		List<Accounts > emptyrequest = new ArrayList<Accounts>();
		Accounts testaccount = new Accounts("TESTER" , "ESTER", 0 ,"");
		request.add(testaccount);
		Serial.accountserial(request);
		Admin.Deny(request);
		request = Serial.accountdeserial();
		assertEquals(emptyrequest,request);

	}
	
	
	////Testing Approve
	@Test
	public void testAccountApproved(){
		List<Accounts > request = Serial.accountdeserial();
		Accounts testaccount = new Accounts("TESTER" , "TEST", 0 ,"");
		request.add(testaccount);
		Serial.accountserial(request);
		Admin.Approve(request);
		List<Customer > customers = Serial.customerdeserial();
		assertTrue(Admin.accountexists(customers, "TESTER"));
		admin.Delete("TESTER");
	}
	
	@Test
	public void testRequestAfter(){
		List<Accounts > request = Serial.accountdeserial();
		Accounts testaccount = new Accounts("TESTER" , "TEST", 0 ,"");
		request.add(testaccount);
		Serial.accountserial(request);
		Admin.Approve(request);
		List<Customer > customers = Serial.customerdeserial();
		admin.Delete("TESTER");
		
		List<Accounts > emptyrequest = new ArrayList<Accounts>();
		request = Serial.accountdeserial();
		assertEquals(emptyrequest,request);
		
	}

}
