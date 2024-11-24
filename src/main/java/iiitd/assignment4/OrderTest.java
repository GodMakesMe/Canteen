package iiitd.assignment4;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class OrderTest {
	private Customer mockCustomer;
	private FoodItem mockFoodItem;
	private Order order;

	class MockCustomer extends Customer {
		String username;
		boolean VIPStatus;

		MockCustomer(String username, boolean VIPStatus) {
			this.username = username;
			this.VIPStatus = VIPStatus;
		}
	}

	class MockFoodItem extends FoodItem {
		int price;

		MockFoodItem(int price) {
			this.price = price;
		}

		@Override
		public int getPrice() {
			return price;
		}
	}

	@Before
	public void setUp() {
		mockCustomer = new MockCustomer("JohnDoe", true);
		mockFoodItem = new MockFoodItem(100);

		// Creating an Order with the mocked Customer
		order = new Order(mockCustomer);
	}

	@Test
	public void testAddItemByCount_ItemNotInOrder() throws OUTOFSTOCK {
		order.addItemByCount(mockFoodItem, 2);

		assertEquals(1, order.orderItems.size());
		assertEquals(Integer.valueOf(2), order.orderItems.get(0).y);
	}

	@Test
	public void testAddItemByCount_ItemAlreadyInOrder() throws OUTOFSTOCK {
		order.addItemByCount(mockFoodItem, 2);
		order.addItemByCount(mockFoodItem, 3);

		assertEquals(1, order.orderItems.size());
		assertEquals(Integer.valueOf(5), order.orderItems.get(0).y);
	}

	@Test
	public void testRemoveItem() throws OUTOFSTOCK {
		order.addItemByCount(mockFoodItem, 2);

		order.removeItem(mockFoodItem);

		assertTrue(order.orderItems.isEmpty());
	}

	@Test
	public void testGetStatus_Preparing() {
		assertEquals("Preparing", order.getStatus());
	}

	@Test
	public void testGetStatus_Completed() {
		order.delivered = true;

		assertEquals("Completed", order.getStatus());
	}

	@Test
	public void testSetFeedback() {
		order.setFeedback("Good service");

		assertEquals("Good service", order.getFeedback());
	}

	@Test
	public void testSetSpecialRequest() {
		order.setSpecialRequest("Extra cheese");

		assertEquals("Extra cheese", order.getSpecialRequest());
	}

	@Test
	public void testInitiateRefund() {
		order.initiateRefund();

		assertTrue(order.initiateRefund);
	}
}
