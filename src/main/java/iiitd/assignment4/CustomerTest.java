package iiitd.assignment4;

import org.junit.Before;
import org.junit.Test;

import static org.testng.AssertJUnit.*;

public class CustomerTest {
	Customer cus1;
	Customer cus2;
	@Before
	public void setUp() throws Exception {
		cus1 = new Customer();
		cus2 = new Customer("Krishna", "godmakesme");
		cus2.set.initiateLogin("Krishna", "godmakesme");
		cus2.set.customerName("Krishna Shukla");
		cus2.set.makeVIP();
	}

	@Test
	public void checkLoginStatus(){
		assertFalse(cus1.customerLogin);
		assertTrue(cus2.customerLogin);
		cus2.logOut();
		cus2.set.initiateLogin("Krishna", "wrongpassword");
		assertFalse(cus1.customerLogin);

	}

	@Test
	public void checkCustomerName(){
		assertNull(cus1.CustomerName);
		assertTrue(cus2.CustomerName.equals("Krishna Shukla"));
	}
	@Test
	public void checkVIPStatus(){
		assertFalse(cus1.VIPStatus);
		assertTrue(cus2.VIPStatus);
	}
	@Test
	public void checkLogOut(){
		cus1.logOut();
		cus2.logOut();
		assertFalse(cus1.customerLogin);
		assertFalse(cus2.customerLogin);
	}

}
