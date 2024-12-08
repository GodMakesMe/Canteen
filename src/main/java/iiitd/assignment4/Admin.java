package iiitd.assignment4;

import java.io.*;
import java.util.ArrayList;

public class Admin extends FoodOrderingSystem implements Serializable{   // I hate there is a single admin
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
	protected class Setter implements Serializable{

		void addNewItem(FoodItem item){
			if (item == null) return;
			if (instance.get.getFoodMenuData().contains(item)){ return;}
			item.FoodID = ItemNO++;
			if (instance.get.getToSaveOrSerializable()){
				if (instance.get.getFoodMenuData().isEmpty()){
					try {
						File myFile = new File("ItemData.txt");
						myFile.createNewFile();
						BufferedWriter writer = new BufferedWriter(new FileWriter("ItemData.txt"));
						writer.write(item.toString());
						writer.append("\n");
						writer.close();
					}catch(IOException ioE){
						System.out.println(ioE.getMessage());
					}
				}else{
					try{
						BufferedWriter writer = new BufferedWriter(new FileWriter("ItemData.txt", true));
						writer.append(item.toString()).append("\n");
						writer.close();
					}catch(IOException ioE){
						try {
							File myFile = new File("ItemData.txt");
							myFile.createNewFile();
							BufferedWriter writer = new BufferedWriter(new FileWriter("ItemData.txt", true));
							for (FoodItem items : instance.get.getFoodMenuData()) {
								writer.append(items.toString()).append("\n");
							}
							writer.append(item.toString()).append("\n");
							writer.close();
						}catch (IOException e) {
							System.out.println(e.getMessage());
						}
					}
				}
			}

			instance.get.getFoodMenuData().add(item);
		}
		void removeItem(FoodItem item){
			if (!instance.get.getFoodMenuData().contains(item)){ return;}
			instance.get.getFoodMenuData().remove(item);
			if (instance.get.getToSaveOrSerializable()) instance.set.updateSavedItems();
		}

	}
	protected class Getter implements Serializable{
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