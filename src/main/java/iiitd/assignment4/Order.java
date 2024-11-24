package iiitd.assignment4;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


class pair<Type0, Type1> implements Serializable {
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
public class Order implements Serializable {
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
	@Override
	public String toString(){
		return (RaisedBy.VIPStatus ? "VIP" : "NON-VIP") + "," + (OrderID != null ? OrderID.toString() : "ERROR") + "," + (RaisedBy.get.username() != null ? RaisedBy.get.username(): "Anonymous User") + "," + getStatus() + "," + getTotalPrice().toString() + "," + (getSpecialRequest() == null ? "NA" : getSpecialRequest());
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
	void incrementItem(FoodItem f) throws OUTOFSTOCK{
		addItemByCount(f, 1);
	}
	@SuppressWarnings("all")
	void addItemByCount(FoodItem item, int count) throws OUTOFSTOCK{
		AtomicBoolean found = new AtomicBoolean(false);
		orderItems.forEach(pair1 -> {
			if (pair1.x.equals(item)) {
				if (pair1.y + count > item.foodLimit){
					try {
						throw new OUTOFSTOCK("OUT OF STOCK LIMIT!!");
					} catch (OUTOFSTOCK e) {
						throw new RuntimeException(e);
					}
//					return;
				}
				pair1.y+=count; found.set(true);
				item.setFoodLimit(item.foodLimit-count);
				return;
			}
		});
		if (!found.get()){
			if (count > item.foodLimit) throw new OUTOFSTOCK("Out OF STOCK LIMIT!!!");
			else{
				orderItems.add(new pair<>(item, count));
				item.setFoodLimit(item.foodLimit-count);
			}
		}
	}
	void reduceItemByCount(FoodItem item, int count){
		for (pair<FoodItem, Integer> pair1 : orderItems) {
			if (pair1.x.equals(item)) {
				if (pair1.y > count) {
					pair1.y -= count;
					item.setFoodLimit(item.foodLimit+count);
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
			item.setFoodLimit(item.foodLimit + savePair.y);
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
