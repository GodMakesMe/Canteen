package com.assignment3.com;

import java.util.ArrayList;
import java.util.Vector;

public class FoodOrderingSystem {
	private String sysName = null;
	private int customersIDNumber = 1;
	private ArrayList<Admin> adminsData = new ArrayList<>();
	private ArrayList<Customer> customersData =  new ArrayList<>();
	protected ArrayList<FoodItem> foodMenuData = new ArrayList<>();
	public FoodOrderingSystem(String name) {
		this.sysName = name;
	}
	public FoodOrderingSystem() {

	}
	public FoodOrderingSystem(FoodOrderingSystem foodOrderingSystem) {       // "Engulfing itself" Snake biting its own tail
		this.sysName = foodOrderingSystem.sysName;
		this.adminsData = foodOrderingSystem.adminsData;
		this.customersData = foodOrderingSystem.customersData;
		this.foodMenuData = foodOrderingSystem.foodMenuData;
		this.customersIDNumber = foodOrderingSystem.customersIDNumber;
	}



	protected class Setter{
		void setSysName(String name){ sysName = name; }
		class AdminData{
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
			void updateAdmin(Admin admin1, Admin admin2){
				removeAdmin(admin1);
				addAdmin(admin2);
			}
		}
		final AdminData adminData = new AdminData();
		class CustomerData{
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
			void updateCustomer(Customer from, Customer to){
				removeCustomer(from);
				addCustomer(to);
			}
		}
		final CustomerData customerData = new CustomerData();
	}
	protected class Getter{
		String getSysName(){ return sysName;}
		protected class AdminData{
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
		protected class CustomerData{
			ArrayList<Customer> getCustomerData(){ return customersData;}
			int customerNumber(){
				return customersIDNumber;
			}
			Customer getCustomerById(String UserId){
				if (UserId == null || UserId.isEmpty()) return null;
				for (Customer i : customersData){
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