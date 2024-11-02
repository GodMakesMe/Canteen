package com.assignment3.com;
import java.util.ArrayList;

class pair<Type0, Type1>{
	Type0 x;
	Type1 y;
	public pair(Type0 a, Type1 b){
		x = a;
		y = b;
	}
	@SuppressWarnings("unused")
	void makePair(Type0 a, Type1 b){
		x = a; y = b;
	}
	@SuppressWarnings("unused")
	boolean equalsPair(pair<Type0, Type1> s1){
		// Using equals() instead of == to compare objects
		return (this.x != null && this.x.equals(s1.x)) && (this.y != null && this.y.equals(s1.y));
	}

	// Override equals and hashCode to make sure pair objects are properly compared in collections
	@Override
	public boolean equals(Object o){
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		pair<?, ?> pair = (pair<?, ?>) o;
		return x.equals(pair.x) && y.equals(pair.y);
	}
}

public class Order {
	private Integer OrderID = null;
	final private Customer RaisedBy;

	ArrayList<pair<FoodItem, Integer>> orderItems;
	public Order(Customer raisedBy){
		orderItems = new ArrayList<>();
		this.RaisedBy = raisedBy;
	}

	void addItemByCount(FoodItem item, int count){
		orderItems.forEach(pair1 -> {if (pair1.x.equals(item)) {pair1.y+=count; }else{orderItems.add(new pair<>(item, count));}});
	}
	void reduceItemByCount(FoodItem item, int count){
		orderItems.forEach(pair1 -> {if (pair1.x.equals(item)) {if (pair1.y > count) {pair1.y -= count;}else{removeItem(item);}}});
	}
	void decrementItem(FoodItem item){
		reduceItemByCount(item, 1);
	}
	void removeItem(FoodItem item){
		orderItems.forEach(pair1 -> {if (pair1.x.equals(item)) {orderItems.remove(pair1);}});
	}
}
