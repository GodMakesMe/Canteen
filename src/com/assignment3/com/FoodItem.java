package com.assignment3.com;

public class FoodItem {
	protected String nameOfFood;
	protected int price;
	protected boolean vegetarian;
	protected String typeDet = "General";
	protected String foodDescription;
	public FoodItem(String nameOfFood, int price, boolean vegetarian) {
		this.nameOfFood = nameOfFood;
		this.price = price;
		this.vegetarian = vegetarian;
	}
	void changeTypeDet(String category){
		typeDet = category;
	}
}
