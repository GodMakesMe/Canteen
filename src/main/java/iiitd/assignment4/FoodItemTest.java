package iiitd.assignment4;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FoodItemTest {
	private FoodItem foodItem;

	@Before
	public void setUp() {
		foodItem = new FoodItem("Pasta", 200, true);
		foodItem.changeTypeDet("Main Course");
	}

	@Test
	public void testConstructor() {
		assertEquals("Pasta", foodItem.getNameOfFood());
		assertEquals(200, foodItem.getPrice());
		assertTrue(foodItem.vegetarian);
		assertEquals("Main Course", foodItem.getTypeDet());
	}

	@Test
	public void testLoaderDataFromStringArray() {
		String[] data = {"Burger", "150", "false", "Snack", "Delicious burger", "101", "5"};
		foodItem.loaderDataFromString(data);

		assertEquals("Burger", foodItem.getNameOfFood());
		assertEquals(150, foodItem.getPrice());
		assertFalse(foodItem.vegetarian);
		assertEquals("Snack", foodItem.getTypeDet());
		assertEquals("Delicious burger", foodItem.getFoodDescription());
		assertEquals(101, foodItem.getFoodID());
		assertEquals(5, foodItem.getItemOrdered());
	}

	@Test
	public void testChangeTypeDet() {
		foodItem.changeTypeDet("Dessert");
		assertEquals("Dessert", foodItem.getTypeDet());
	}

	@Test
	public void testGetters() {
		assertEquals("Veg", foodItem.getVegetarian());
		foodItem.loaderDataFromString("Pizza", "250", "false", "Snack", "Cheese pizza", "102", "3");
		assertEquals("Non-Veg", foodItem.getVegetarian());
	}

	@Test
	public void testToString() {
		foodItem.loaderDataFromString("Salad", "100", "true", "Appetizer", "Fresh salad", "103", "2");
		String expectedString = "Salad,100,true,Appetizer,Fresh salad,103,2";
		assertEquals(expectedString, foodItem.toString());
	}

	@Test
	public void testComparators() {
		FoodItem item1 = new FoodItem("Burger", 150, false);
		FoodItem item2 = new FoodItem("Pizza", 200, true);

		assertTrue(FoodItem.BY_PRICE.compare(item1, item2) < 0);
		assertTrue(FoodItem.BY_NAME.compare(item1, item2) < 0);

		item1.itemOrdered = 10;
		item2.itemOrdered = 5;
		assertTrue(FoodItem.BY_ITEM_ORDERED.compare(item1, item2) > 0);

		item1.FoodID = 101;
		item2.FoodID = 102;
		assertTrue(FoodItem.BY_FOOD_ID.compare(item1, item2) < 0);
	}
}
