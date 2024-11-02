package com.assignment3.com;
import java.util.Comparator;

public class FoodItem {
	protected String nameOfFood;
	protected int price;
	protected boolean vegetarian;
	protected String typeDet = "General";
	protected String foodDescription;
	protected Integer FoodID;
	protected Integer itemOrdered = 0;


	private int getPrice() {
		return price;
	}
	private int getFoodID() {
		return FoodID;
	}
	private String getFoodName(){
		return nameOfFood;
	}
	private int getItemOrdered() {
		return itemOrdered;
	}

	public static final Comparator<FoodItem> BY_PRICE = Comparator.comparingInt(FoodItem::getPrice);
	public static final Comparator<FoodItem> BY_NAME = Comparator.comparing(FoodItem::getFoodName);
	public static final Comparator<FoodItem> BY_ITEM_ORDERED = Comparator.comparingInt(FoodItem::getItemOrdered);
	public static final Comparator<FoodItem> BY_FOOD_ID = Comparator.comparingInt(FoodItem::getFoodID);


	public FoodItem(String nameOfFood, int price, boolean vegetarian) {
		this.nameOfFood = nameOfFood;
		this.price = price;
		this.vegetarian = vegetarian;
	}
	void changeTypeDet(String category){
		typeDet = category;
	}

}
