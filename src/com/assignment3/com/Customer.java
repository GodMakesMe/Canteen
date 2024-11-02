package com.assignment3.com;

import java.util.ArrayList;
import java.util.Vector;

public class Customer extends FoodOrderingSystem{
	FoodOrderingSystem instance;
	boolean customerLogin;
	String CustomerName;
	Integer CustomerID;
	String LoginID;
	String Password;
	boolean VIPStatus;
	Order cart;
	Vector<String> searchHistory = new Vector<>();
	ArrayList<Order> previousOrders = new ArrayList<>();

	public Customer(){
		super();
		this.customerLogin = false;
		this.CustomerName = null;
		this.CustomerID = null;
		this.LoginID = null;
		this.Password = null;
		this.VIPStatus = false;
		cart = new Order(this);
	}
	Customer(String LoginID, String Password){
		super();
		this.LoginID = LoginID;
		this.Password = Password;
		VIPStatus = false;
		this.CustomerID = null;
		this.CustomerName = null;
		this.customerLogin = false;
	}
	void logOut(){
		customerLogin = false;
	}

	void newCart(){
		cart = new Order(this);
	}

	protected class Setter{
		void customerID(int customerID){
			CustomerID = customerID;
		}
		void customerName(String customerName){
			CustomerName = customerName;
		}
		void makeVIP(){
			VIPStatus = true;
		}
		@SuppressWarnings("unused")
		void removeVIP(){
			VIPStatus = false;
		}
		void initiateLogin(String Username, String Pass){
			if (LoginID.equals(Username) && Password.equals(Pass)){
				customerLogin = true;
			}
		}
		void registerLogin(String Username, String Pass){
			LoginID = Username;
			Password = Pass;
		}
		void placeOrder(){
			instance.set.orderData.addOrder(cart);
			newCart();
		}
		void placeOrder(Order neworder){
			if (neworder == null){
				System.out.println("Order is null");
				return;
			}
			instance.set.orderData.addOrder(neworder);
		}
	}
	protected class Getter {
		String username(){
			return LoginID;
		}
		String password(){
			return Password;
		}
		ArrayList<FoodItem> getFoodMenuData(){
			return instance.get.getFoodMenuData();
		}
		ArrayList<Order> getPreviousOrderData(){ return previousOrders; }
		Order specificPreviousOrder(Integer previousOrderID){
			for (Order order : previousOrders) {
				if (order.getOrderId().equals(previousOrderID)) {
					return order;
				}
			}
			return null;
		}
	}

	Setter set = new Setter();
	Getter get = new Getter();

}
