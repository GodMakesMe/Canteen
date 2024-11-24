package iiitd.assignment4;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

public class FoodOrderingSystem implements Serializable{
	private String sysName = null;
	private int customersIDNumber = 1;
	private int orderNo = 1;
	private ArrayList<Admin> adminsData = new ArrayList<>();
	private ArrayList<Customer> customersData =  new ArrayList<>();
	private ArrayList<FoodItem> foodMenuData = new ArrayList<>();
	private ArrayList<Order> ordersData = new ArrayList<>();
	protected ArrayList<Order> refundOrdersData = new ArrayList<>();
	public FoodOrderingSystem(String name) {
		this.sysName = name;
	}
	public FoodOrderingSystem() {

	}
	@SuppressWarnings("unused")
	public FoodOrderingSystem(FoodOrderingSystem foodOrderingSystem) {       // "Engulfing itself" Snake biting its own tail
		this.sysName = foodOrderingSystem.sysName;
		this.adminsData = foodOrderingSystem.adminsData;
		this.customersData = foodOrderingSystem.customersData;
		this.foodMenuData = foodOrderingSystem.foodMenuData;
		this.customersIDNumber = foodOrderingSystem.customersIDNumber;
		this.ordersData = foodOrderingSystem.ordersData;
		this.orderNo = foodOrderingSystem.orderNo;
		this.refundOrdersData = foodOrderingSystem.refundOrdersData;

	}

	protected class Setter implements Serializable {
		void loadSavedItems(){
			try{
				BufferedReader reader = new BufferedReader(new FileReader("ItemData.txt"));
				for (String line = reader.readLine(); line != null; line = reader.readLine()) {
					String[] itemData = line.split(",");
					FoodItem item = new FoodItem();
					item.loaderDataFromString(itemData);
					item.FoodID = get.getFoodMenuData().size()+1;
					get.getFoodMenuData().add(item);
				}
			}catch(IOException e){
				System.out.println("No Saved File System...");
				return;
			}
			updateSavedItems();
		}void loadSavedOrderAndCarts(){
			try{
				BufferedReader reader = new BufferedReader(new FileReader("OrderData.txt"));
				for (String line = reader.readLine(); line != null; line = reader.readLine()) {
					String[] itemData = line.split(",");
					FoodItem item = new FoodItem();
					item.loaderDataFromString(itemData);
					item.FoodID = get.getFoodMenuData().size()+1;
					get.getFoodMenuData().add(item);
				}
			}catch(IOException e){
				System.out.println("No Saved File System...");
				return;
			}
			updateSavedItems();
		}
		void saveOrdersAndCarts(){
			try{
				File myFile = new File("OrderDataForJavaFX.txt");
				myFile.createNewFile();
				BufferedWriter writer = new BufferedWriter(new FileWriter("OrderDataForJavaFX.txt"));
				for (Order order : ordersData) {
					writer.append(order.toString()).append("\n");
				}
				writer.close();
			}catch(IOException e){
				System.out.println(e.getMessage());
			}
		}
		void updateSavedItems(){
			try{
				File myFile = new File("ItemData.txt");
				myFile.createNewFile();
				BufferedWriter writer = new BufferedWriter(new FileWriter("ItemData.txt"));
				for (FoodItem items : get.getFoodMenuData()) {
					writer.append(items.toString()).append("\n");
				}
				writer.close();
			}catch(IOException e){
				System.out.println(e.getMessage());
			}
		}
		@SuppressWarnings("unused")
		void setSysName(String name){ sysName = name; }
		class AdminData implements Serializable{
			void addAdmin(Admin admin){
				for (Admin i : adminsData){
					if (i.equals(admin)){
						return;
					}
				}
				admin.instance = FoodOrderingSystem.this;
				adminsData.add(admin);
			}
			void removeAdmin(Admin admin){
				for (Admin i : adminsData){
					if (i.equals(admin)){
						adminsData.remove(admin);
						return;
					}
				}
			}
			@SuppressWarnings("unused")
			void updateAdmin(Admin admin1, Admin admin2){
				removeAdmin(admin1);
				addAdmin(admin2);
			}
		}
		final AdminData adminData = new AdminData();
		class CustomerData implements Serializable{
			void addCustomer(Customer customer){
				for (Customer i : customersData){
					if (i.equals(customer)){
						return;
					}
				}
				customer.instance = FoodOrderingSystem.this;
				customersData.add(customer);
				customersIDNumber++;
			}
			void removeCustomer(Customer customer){
				for (Customer i : customersData){
					if (i.equals(customer)){
						customersData.remove(customer);
						return;
					}
				}
			}
			@SuppressWarnings("unused")
			void updateCustomer(Customer from, Customer to){
				removeCustomer(from);
				addCustomer(to);
			}
		}
		final CustomerData customerData = new CustomerData();
		class OrderData implements Serializable{
			void addOrder(Order order){
				if (ordersData.contains(order)){
//					order.setOrderId(orderNo++);
					Order newOrder = new Order(order);
					newOrder.setOrderId(orderNo++);
					ordersData.add(newOrder);
					set.saveOrdersAndCarts();
				}else {
					order.setOrderId(orderNo++);
					ordersData.add(order);
					set.saveOrdersAndCarts();
				}
			}
		}
		final OrderData orderData = new OrderData();
	}
	protected class Getter implements Serializable{
		String getSysName(){ return sysName;}
		protected class OrderData implements Serializable {
			ArrayList<Order> getOrderData(){
				return ordersData;
			}
			Order getOrderByID(Integer orderId){
				for (Order i : ordersData){
					if (i.getOrderId().equals(orderId)){
						return i;
					}
				}
				return null;
			}
		}
		final OrderData orderData = new OrderData();
		protected class AdminData implements Serializable {
			@SuppressWarnings("unused")
			ArrayList<Admin> getAdminData(){ return adminsData;}
			Admin getAdminById(String UserId){
				if (UserId == null || UserId.isEmpty()) return null;
				for (Admin i : adminsData){
					if (i.get.username().equals(UserId)){
						return i;
					}
				}
				return null;
			}
		}
		AdminData adminData = new AdminData();
		protected class CustomerData implements Serializable{
			ArrayList<Customer> getCustomerData(){ return customersData;}
			int customerNumber(){
				return customersIDNumber;
			}
			Customer getCustomerById(String UserId){
				if (UserId == null || UserId.isEmpty()) return null;
				for (Customer i : customersData){
					if (i.get.username() == null){
						return null;
					}
					if (i.get.username().equals(UserId)){
						return i;
					}
				}
				return null;
			}
		}
		CustomerData customerData = new CustomerData();
		ArrayList<FoodItem> getFoodMenuData(){ return foodMenuData;}

	}
	final Getter get = new Getter();
	final Setter set = new Setter();

	boolean verifyAdmin(Vector<String> userInput){
		return verifyAdmin(userInput.get(0), userInput.get(1));
	}
	boolean verifyAdmin(String userId, String password){
		Admin newAdmin = get.adminData.getAdminById(userId);
		return newAdmin != null && newAdmin.get.password().equals(password);
	}

	boolean verifyCustomer(Vector<String> userInput){
		return verifyCustomer(userInput.get(0), userInput.get(1));
	}
	boolean verifyCustomer(String userId, String password){
		Customer newCustomer = get.customerData.getCustomerById(userId);
		return newCustomer != null && newCustomer.get.password().equals(password);
	}

}