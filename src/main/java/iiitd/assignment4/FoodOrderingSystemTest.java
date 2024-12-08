package iiitd.assignment4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.*;
import static org.testng.AssertJUnit.assertNotNull;

public class FoodOrderingSystemTest {
	private FoodOrderingSystem system;
	private FoodOrderingSystem.Getter getter;
	private FoodOrderingSystem.Setter setter;
	Admin baseAdmin;
	FoodItem baseFoodItem;

	@Before
	public void setUp() {
		system = new FoodOrderingSystem("Test System");
		system.set.setSaveSerialization(false);
		getter = system.get;
		setter = system.set;
		Admin admin = new Admin("admin1", "password");
		setter.adminData.addAdmin(admin);
		FoodItem item = new FoodItem("Cheese Maggi", 40, true);
		item.setFoodLimit(100);
		admin.set.addNewItem(item);
		baseAdmin = admin;
		baseFoodItem = item;
	}
	@Test
	public void testSysName() {
		assertEquals("Test System", getter.getSysName());

		setter.setSysName("Updated System");
		assertEquals("Updated System", getter.getSysName());
	}

	@Test
	public void testAddAdmin() {
		Admin admin = new Admin("admin1", "password");
		setter.adminData.addAdmin(admin);

		assertNotNull(getter.adminData.getAdminById("admin1"));
		assertEquals("admin1", getter.adminData.getAdminById("admin1").get.username());
	}

	@Test
	public void testRemoveAdmin() {
		Admin admin = new Admin("admin2", "password");
		setter.adminData.addAdmin(admin);
		setter.adminData.removeAdmin(admin);

		assertNull(getter.adminData.getAdminById("admin2"));
	}

	@Test
	public void testAddCustomer() {
		Customer customer = new Customer("cust1", "password");
		setter.customerData.addCustomer(customer);

		assertNotNull(getter.customerData.getCustomerById("cust1"));
		assertEquals("cust1", getter.customerData.getCustomerById("cust1").get.username());
		assertEquals(2, getter.customerData.customerNumber()); // Should increment by 1
	}

	@Test
	public void testRemoveCustomer() {
		Customer customer = new Customer("cust2", "password");
		setter.customerData.addCustomer(customer);
		setter.customerData.removeCustomer(customer);

		assertNull(getter.customerData.getCustomerById("cust2"));
	}

	@Test
	public void testVerifyAdmin() {
		Admin admin = new Admin("admin3", "securepassword");
		setter.adminData.addAdmin(admin);

		assertTrue(system.verifyAdmin("admin3", "securepassword"));
		assertFalse(system.verifyAdmin("admin3", "wrongpassword"));
	}

	@Test
	public void testVerifyCustomer() {
		Customer customer = new Customer("cust3", "securepassword");
		setter.customerData.addCustomer(customer);

		assertTrue(system.verifyCustomer("cust3", "securepassword"));
		assertFalse(system.verifyCustomer("cust3", "wrongpassword"));
	}

	@Test
	public void testLoadSavedItems() {
		setter.loadSavedItems();
		assertNotNull(getter.getFoodMenuData());
	}

	@Test
	public void testLoadSavedOrderAndCarts() {
		setter.loadSavedOrderAndCarts();
		assertNotNull(getter.orderData.getOrderData());
	}

	@Test
	public void testOUTOFSTOCK() throws Exception {
		Customer newCustomer = new Customer();
		newCustomer.cart.addItemByCount(baseFoodItem, 80);
//		System.out.println(baseFoodItem.foodLimit);
		Customer newCustomer2 = new Customer();
		boolean nextCommandWillGiveError = false;
		try {
			newCustomer2.cart.addItemByCount(baseFoodItem, 40);
		}catch (OUTOFSTOCK e){
			nextCommandWillGiveError = true;
		}
		assertTrue(nextCommandWillGiveError);
		FoodItem otherItem = new FoodItem("WaterBottle", 10, true);
		nextCommandWillGiveError = false;
		try {
			newCustomer2.cart.addItemByCount(otherItem, 1001);
		}catch (
				OUTOFSTOCK e
		){
			nextCommandWillGiveError = true;
		}
		assertTrue(nextCommandWillGiveError);
	}


	@Test
	public void testAddOrder() {
		Customer customer = new Customer("cust3", "securepassword");
		Order order = new Order(customer);
		setter.orderData.addOrder(order);

		assertEquals(1, getter.orderData.getOrderData().size());
		assertNotNull(getter.orderData.getOrderByID(order.getOrderId()));
	}

	@Test
	public void testSaveOrdersAndCarts() {
		setter.saveOrdersAndCarts();
	}

	@Test
	public void testRunOtherTests() {
		// Running other test classes using JUnitCore
		Class[] classesToRun = {
				FoodItemTest.class,
				CustomerTest.class,
				OrderTest.class // Add as many classes as you want
		};

		for (Class testClass : classesToRun) {
			System.out.println("Running tests from: " + testClass.getName());
			Result result = JUnitCore.runClasses(testClass);

			// Check if there are any failures in the tests
			for (Failure failure : result.getFailures()) {
				System.out.println(failure.toString());
			}

			// Assert that no tests failed
			assertTrue("Some tests failed", result.wasSuccessful());
		}
	}
}


