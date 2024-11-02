package com.assignment3.com;

import java.util.ArrayList;

public class Admin extends FoodOrderingSystem{   // I hate there is a single admin
	FoodOrderingSystem instance;
	private boolean adminLogin;
	private final String LoginID;
	private final String Password;
	private Integer ItemNO = 1;
	Admin(){
		super();
		this.LoginID = "krishna";
		this.Password = "godmakesme";
		this.adminLogin = false;
	}
	@SuppressWarnings("unused")
	Admin(String LoginID, String Password){
		super();
		this.LoginID = LoginID;
		this.Password = Password;
		this.adminLogin = false;
	}
	void logOut(){
		this.adminLogin = false;
	}
	protected class Setter{
		void addNewItem(FoodItem item){
			if (item == null) return;
			if (instance.get.getFoodMenuData().contains(item)){ return;}
			item.FoodID = ItemNO++;
			instance.get.getFoodMenuData().add(item);
		}
		void removeItem(FoodItem item){
			if (!instance.get.getFoodMenuData().contains(item)){ return;}
			instance.get.getFoodMenuData().remove(item);
		}

	}
	protected class Getter{
		String username(){
			return LoginID;
		}
		String password(){
			return Password;
		}
		@SuppressWarnings("unused")
		ArrayList<FoodItem> getFoodMenuData(){
			return instance.get.getFoodMenuData();
		}
		@SuppressWarnings("unused")
		boolean loginStatus(){
			return adminLogin;
		}
		ArrayList<Order> getOrders(){
			return instance.get.orderData.getOrderData();
		}
		Order getOrderByID(Integer OrderID){
			return instance.get.orderData.getOrderByID(OrderID);
		}
	}

	Setter set = new Setter();
	Getter get = new Getter();

}