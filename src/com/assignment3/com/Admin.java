package com.assignment3.com;

public class Admin extends FoodOrderingSystem{   // I hate there is a single admin
	private boolean adminLogin = false;
	private final String LoginID;
	private final String Password;

	Admin(){
		this.LoginID = "krishna";
		this.Password = "godmakesme";
	}

	Admin(String LoginID, String Password){
		this.LoginID = LoginID;
		this.Password = Password;
	}
	void logOut(){
		this.adminLogin = false;
	}
	protected class Setter{

	}
	protected class Getter{
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