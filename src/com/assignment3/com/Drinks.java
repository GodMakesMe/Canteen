package com.assignment3.com;

public class Drinks extends VegItem{
	String DrinkDescription;
	public Drinks(String DrinkName, String DrinkDescription, int DrinkPrice) {
		super(DrinkName, DrinkPrice);
		typeDet = "Drink";
	}
}
