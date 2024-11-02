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

	}

	Setter set = new Setter();
	Getter get = new Getter();

}
