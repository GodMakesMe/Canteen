package com.assignment3.com;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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
	boolean packed = false;
	boolean delivered = false;
	boolean prepared = false;
	String feedback = null;
	String specialRequest = null;
	boolean initiateRefund = false;
	ArrayList<pair<FoodItem, Integer>> orderItems;
	public Order(Customer raisedBy){
		orderItems = new ArrayList<>();
		this.RaisedBy = raisedBy;
	}
	public Order(Order order){
		RaisedBy = order.getRaisedBy();
		orderItems = order.orderItems;
		packed = order.packed;
		delivered = order.delivered;
		prepared = order.prepared;
		feedback = order.feedback;
		specialRequest = order.specialRequest;
		initiateRefund = order.initiateRefund;
		this.OrderID = order.OrderID;
	}
	void incrementItem(FoodItem f){
		addItemByCount(f, 1);
	}
	@SuppressWarnings("all")
	void addItemByCount(FoodItem item, int count){
		AtomicBoolean found = new AtomicBoolean(false);
		orderItems.forEach(pair1 -> {
			if (pair1.x.equals(item)) {
				pair1.y+=count; found.set(true);
				return;
			}
		});
		if (!found.get()){
			orderItems.add(new pair<>(item, count));
		}
	}
	void reduceItemByCount(FoodItem item, int count){
		for (pair<FoodItem, Integer> pair1 : orderItems) {
			if (pair1.x.equals(item)) {
				if (pair1.y > count) {
					pair1.y -= count;
				} else {
					removeItem(item);
					break;
				}
			}
		}
	}
	void decrementItem(FoodItem item){
		reduceItemByCount(item, 1);
	}
	void removeItem(FoodItem item){
		pair<FoodItem, Integer> savePair = null;
		for (pair<FoodItem, Integer> pair1 : orderItems) {
			if (pair1.x.equals(item)) {
				savePair = pair1;
				break;
			}
		}
		if (savePair != null){
			orderItems.remove(savePair);
		}
	}
	Integer getOrderId(){
		return OrderID;
	}
	void setOrderId(Integer orderID){
		OrderID = orderID;
	}
	void setFeedback(String feedback){
		this.feedback = feedback;
	}
	String getFeedback(){
		return feedback;
	}
	String getStatus(){
		return this.delivered ? "Completed" : this.packed ? "Packed" : this.prepared ? "Prepared" : this.initiateRefund ? "Need Refund" : "Preparing";
	}
	Integer getTotalPrice(){
		AtomicInteger a = new AtomicInteger(0);
		orderItems.forEach(pair1 -> a.addAndGet(pair1.y*pair1.x.price));
		return a.get();
	}
	Customer getRaisedBy(){
		return RaisedBy;
	}
	void setSpecialRequest(String specialRequest){
		this.specialRequest = specialRequest;
	}
	String getSpecialRequest(){
		return specialRequest;
	}
	void initiateRefund(){
		initiateRefund = true;
	}
}
