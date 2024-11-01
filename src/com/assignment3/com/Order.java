package com.assignment3.com;

import java.util.ArrayList;

public class Order {
	private Integer OrderID;
	final private Customer RaisedBy;
	ArrayList<FoodItem> orderItems;
	public Order(Customer raisedBy){
		orderItems = new ArrayList<>();
		this.RaisedBy = raisedBy;
	}

}
