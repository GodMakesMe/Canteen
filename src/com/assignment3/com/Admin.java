package com.assignment3.com;

public class Admin extends FoodOrderingSystem{   // I hate there is a single admin
	private boolean adminLogin;
	private final String LoginID;
	private final String Password;

	Admin(){
		this.LoginID = "krishna";
		this.Password = "godmakesme";
		this.adminLogin = false;
	}

	Admin(String LoginID, String Password){
		this.LoginID = LoginID;
		this.Password = Password;
		this.adminLogin = false;
	}
	void logOut(){
		this.adminLogin = false;
	}
	protected class Setter extends FoodOrderingSystem.Setter{
		void addNewItem(FoodItem item){
			if (item == null) return;
			if (foodMenuData.contains(item)){ return;}
			foodMenuData.add(item);
		}
		void removeItem(FoodItem item){
			if (!foodMenuData.contains(item)){ return;}
			foodMenuData.remove(item);
		}
		void updateItem(FoodItem item, FoodItem newItem){
			if (!foodMenuData.contains(item)){ addNewItem(newItem);}
			else{
				removeItem(item);
				addNewItem(newItem);
			}
		}
	}
	protected class Getter extends FoodOrderingSystem.Getter{
		String username(){
			return LoginID;
		}
		String password(){
			return Password;
		}
	}

	Setter set = new Setter();
	Getter get = new Getter();

}