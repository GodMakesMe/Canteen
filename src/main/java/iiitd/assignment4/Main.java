package iiitd.assignment4;


import javafx.application.Application;
import javafx.stage.Stage;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	static GenericFunctions genericFunctions = new GenericFunctions();
	static void pass(){
		System.out.println("Facility under process.");
	}
	static Scanner kybrd = new Scanner(System.in);
	static Integer inputTaker(Vector<String> inputWise){
		System.out.println("-------------------------------------------------------------------------------------------------------");
		short a = 1;
		for (String i : inputWise){
			System.out.println(a++ + ".\t" + i);
		}
//		System.out.println("-------------------------------------------------------------------------------------------------------");
		int selectedOption;
		try{
			System.out.print("Select an option [1-" + inputWise.size() + "]:\t");
			selectedOption = kybrd.nextInt(); kybrd.nextLine();
			System.out.println("-------------------------------------------------------------------------------------------------------");
		}catch (InputMismatchException e){
			System.out.println("Please enter a valid option! between 1-" + inputWise.size() + ".");
			System.out.println("-------------------------------------------------------------------------------------------------------");
			kybrd.nextLine();
			return null;
		}
		if (selectedOption < 1 || selectedOption > inputWise.size()){
			return null;
		}
		return selectedOption;
	}
	static Integer inputTaker(String... inputWise){
		System.out.println("-------------------------------------------------------------------------------------------------------");
		short a = 1;
		for (String i : inputWise){
			System.out.println(a++ + ".\t" + i);
		}
//		System.out.println("-------------------------------------------------------------------------------------------------------");
		int selectedOption;
		try{
			System.out.print("Select an option [1-" + inputWise.length + "]:\t");
			selectedOption = kybrd.nextInt(); kybrd.nextLine();
			System.out.println("-------------------------------------------------------------------------------------------------------");
		}catch (InputMismatchException e){
			System.out.println("Please enter a valid option! between 1-" + inputWise.length + ".");
			System.out.println("-------------------------------------------------------------------------------------------------------");
			kybrd.nextLine();
			return null;
		}
		if (selectedOption < 1 || selectedOption > inputWise.length){
			return null;
		}
		return selectedOption;
	}

	static Vector<String> loginPopup(){
		System.out.print("""
				Login:-------------------------------------------------------------------------------------------------
				""");
		Vector<String> loginInfo = new Vector<>();
		System.out.print("\tUsername:\t");
		loginInfo.add(kybrd.nextLine());
		System.out.print("\tPassword:\t");
		loginInfo.add(kybrd.nextLine());
		return loginInfo;
	}

	static Vector<Object> registerPopup(FoodOrderingSystem foodOrderingSystem){
		System.out.print("""
				Signup:-------------------------------------------------------------------------------------------------
				""");
		Vector<Object> registerInfo = new Vector<>();
		System.out.print("\tUsername:\t");
		registerInfo.add(kybrd.nextLine());
		for (Customer i : foodOrderingSystem.get.customerData.getCustomerData()){
			if (i.LoginID.equals(registerInfo.get(0))){
				System.out.println("Username Already Exists");
				return registerPopup(foodOrderingSystem);
			}
		}
		System.out.print("\tPassword:\t");
		registerInfo.add(kybrd.nextLine());
		System.out.print("\tName Of User:\t");
		registerInfo.add(kybrd.nextLine());
		registerInfo.add(foodOrderingSystem.get.customerData.customerNumber());
		return registerInfo;
	}

	static void viewFoodItems(Admin admin){
		viewFoodItems(admin.instance);
	}

	static void viewFoodItems(FoodOrderingSystem admin){
		int a = 1;
		genericFunctions.printWithSpacing("S.no.", 7, "Name Of Food", 35, "Category", 15, "Veg Or Non-Veg", 20,"Price", 10, "Stock", 10,"Item No.", 10);
		System.out.println();
		for (FoodItem i : admin.get.getFoodMenuData()){
			genericFunctions.printWithSpacing(a++, 7, i.nameOfFood, 35, i.typeDet, 15, i.vegetarian ? "Veg" : "Non Veg", 20, i.price, 10, i.foodLimit, 10, i.FoodID, 10);
			System.out.println();
		}
	}

	static void viewFoodItems(Customer customer){
		viewFoodItems(customer.instance);
		addToCart(customer);
	}

	static void addNewItem(Admin admin){
		Integer selected = inputTaker("Drinks", "General", "Other", "Previous Menu");
		if (selected == null || selected == 4){ return;}
		FoodItem item = null;
		if (selected == 1){
			System.out.println("Enter Details Of Drinks.");
			System.out.print("Drink Name:\t");
			String drinkName = kybrd.nextLine();
			int drinkPrice;
			while (true) {
				System.out.print("Drink Price:\t");
				try{
					drinkPrice = kybrd.nextInt();
					kybrd.nextLine();
				}
				catch (InputMismatchException e){
					kybrd.nextLine();
					System.out.println("Please enter a valid drink price.");
					continue;
				}
				break;
			}
			System.out.print("Drink Description:\t");
			String drinkDescription = kybrd.nextLine();
			item = new FoodItem(drinkName, drinkPrice, true);
			item.typeDet = "Drink";
			item.foodDescription = drinkDescription;
		}else if (selected == 2 || selected == 3){
			String category = "General";
			if (selected == 3){
				System.out.println("Enter The Food Category:\t");
				category = kybrd.nextLine();
			}
			Integer option = inputTaker("Veg", "Non-Veg", "Previous Menu");
			if (option == null || option == 3){ return;}
			String foodName;
			boolean veg = false;
			if (option == 1){
				System.out.println("Enter Details Of Veg Item.");
				veg = true;
			}if (option == 2){
				System.out.println("Enter Details Of Non-Veg Item.");
			}
			System.out.print("Food Name:\t");
			foodName = kybrd.nextLine();
			int foodPrice;
			while (true) {
				System.out.print("Food Price:\t");
				try{
					foodPrice = kybrd.nextInt();
					kybrd.nextLine();
				}catch (InputMismatchException e){
					kybrd.nextLine();
					System.out.println("Please enter a valid food price.");
					continue;
				}
				break;
			}
			System.out.println(category + " Description:\t");
			item = new FoodItem(foodName, foodPrice, veg);
			item.foodDescription = kybrd.nextLine();
			if (selected == 3){
				item.changeTypeDet(category);
			}
		}
		System.out.println("Do you want to add limit:\t");
		Integer limitInputAsker = inputTaker("Yes", "No");
		if (limitInputAsker != null && limitInputAsker == 1){
			int limit;
			while (true) {
				System.out.print("Enter Limit [Stock]:\t");
				try{
					limit = kybrd.nextInt();
					kybrd.nextLine();
					assert item != null;
					item.setFoodLimit(limit);
				}catch (InputMismatchException j){
					kybrd.nextLine();
					System.out.println("Please enter a valid limit.");
					continue;
				}catch (AssertionError e){
					break;
				}
				break;
			}
		}
		admin.set.addNewItem(item);
		System.out.println("Item Added Successfully.");
	}

	static FoodItem getItem(Admin admin){
		viewFoodItems(admin);
		System.out.print("Select S/no To Edit:\t");
		int input;
		try{
			input = kybrd.nextInt();
			kybrd.nextLine();
		}catch (InputMismatchException e){
			kybrd.nextLine();
			System.out.println("Invalid input.");
			return null;
		}
		if (input < 1 || input > admin.instance.get.getFoodMenuData().size()){
			System.out.println("Invalid input.");
			return null;
		}
		return admin.instance.get.getFoodMenuData().get(input-1);
	}

	static void updateItem(Admin admin){
		FoodItem item = getItem(admin);
		if (item == null){ return;}
		genericFunctions.printItem(item);
		Integer selectedOption = inputTaker("Change Item Name", "Change Item Category", "Change Price", "Change Description", "Change Stock", "Previous Menu");
		if (selectedOption == null || selectedOption == 6){ return;}
		if (selectedOption == 1){
			System.out.println("Enter New Name:\t");
			item.nameOfFood = kybrd.nextLine();
		}if (selectedOption == 2){
			System.out.print("Enter New Category:\t");
			item.typeDet = kybrd.nextLine();
		}if (selectedOption == 3){
			System.out.print("Enter New Price:\t");
			try{
				item.price = kybrd.nextInt();
				kybrd.nextLine();
			}catch (InputMismatchException e){
				kybrd.nextLine();
				System.out.println("Invalid input.");
				return;
			}
		}if (selectedOption == 4){
			System.out.print("Enter New Description:\t");
			item.foodDescription = kybrd.nextLine();
		}if (selectedOption == 5){
			System.out.println("Old Stock:\t" + item.foodLimit);
			System.out.print("Enter New Stock Count:\t");
			try{
				item.setFoodLimit(kybrd.nextInt());
				kybrd.nextLine();
			}catch (InputMismatchException e){
				kybrd.nextLine();
				System.out.println("Invalid input.");
				return;
			}
		}
		System.out.println("Details Changed Successfully.");
	}

	static void removeItem(Admin admin){
		FoodItem item = getItem(admin);
		if (item == null){ return;}
		admin.set.removeItem(item);
		System.out.println("Item Removed Successfully.");
	}

	static void adminManageMenu(Admin admin){
		while (true){
			Integer selectedOption;
			selectedOption = inputTaker("View Items", "Add New Item", "Update Item", "Remove Item", "Previous Menu");
			if (selectedOption == null){
				System.out.println("Invalid Input");
				continue;
			}
			if (selectedOption == 5){ break;}
			if (selectedOption == 1) viewFoodItems(admin);
			if (selectedOption == 2) addNewItem(admin);
			if (selectedOption == 3) updateItem(admin);
			if (selectedOption == 4) removeItem(admin);
		}
	}
	static void viewOrdersAdmin(Admin admin){
		System.out.println("Orders By VIP Members");
		admin.get.getOrders().forEach(order -> {if (order.getRaisedBy().VIPStatus && !order.delivered) {genericFunctions.printOrder(order);}});
		System.out.println("Orders By Non-VIP Members");
		admin.get.getOrders().forEach(order -> {if (!order.getRaisedBy().VIPStatus && !order.delivered) {genericFunctions.printOrder(order);}});
	}
	static void manageOrderStatusAdmin(Admin admin){
		viewOrdersAdmin(admin);
		System.out.print("Select Order ID to change Status [0 to Skip]:\t");
		int selectedOption;
		try{
			selectedOption = kybrd.nextInt(); kybrd.nextLine();
		}catch (InputMismatchException e){
			kybrd.nextLine();
			System.out.println("Invalid input.");
			return;
		}
		if (selectedOption == 0){
			return;
		}
		Order orderSave = !admin.get.getOrderByID(selectedOption).delivered ? admin.get.getOrderByID(selectedOption) : null;
		if (orderSave != null){
			System.out.println("Current Order Status:\t" + orderSave.getStatus());
			if (!orderSave.prepared){
				if (orderSave.initiateRefund){
					System.out.println("Refund Initiated as Not Prepared");
					admin.instance.refundOrdersData.remove(orderSave);
					admin.instance.get.orderData.getOrderData().remove(orderSave);
					return;
				}
				Integer a = inputTaker("Prepared", "Still Preparing");
				if (a == null || a == 2) return;
				if (a == 1) orderSave.prepared = true;
			}
			if (!orderSave.packed){
				Integer a = inputTaker("Out For Delivery", "Still Packing");
				if (a == null || a == 2) return;
				if (a == 1) orderSave.packed = true;
			}
			if (!orderSave.delivered){
				Integer a = inputTaker("Delivered", "To Be Delivered");
				if (a == null || a == 2) return;
				if (a == 1) orderSave.delivered = true;
			}
		}
	}
	static void showSpecialRequestsAdmin(Admin admin){
		for (Order order : admin.get.getOrders()) {
			if (!order.delivered) {
				System.out.println("Order ID: " + order.getOrderId());
				System.out.println("Status: " + order.getStatus());
				System.out.println("Special Request: " + order.getSpecialRequest());
			}
		}
	}

	static void adminManageOrder(Admin admin){
		while (true){
			Integer selectedOption;
			selectedOption = inputTaker("Views Orders", "Manage Order Status", "Show Special Requests", "Previous Menu");
			if (selectedOption == null){
				System.out.println("Invalid Input");
				continue;
			}
			if (selectedOption == 4){ break;}
			if (selectedOption == 1){ viewOrdersAdmin(admin);}
			if (selectedOption == 2){ manageOrderStatusAdmin(admin);}
			if (selectedOption == 3){ showSpecialRequestsAdmin(admin);}
		}
	}
	static void viewDailyReport(Admin admin){
		Integer selectedOption = inputTaker("Total Sales", "Most Sold Item", "Previous Menu");
		if (selectedOption == null || selectedOption == 3){return;}
		if (selectedOption == 1){
			AtomicInteger sales = new AtomicInteger(0);
			admin.get.getOrders().forEach(order -> sales.getAndAdd(order.getTotalPrice()));
			System.out.println("Total Sales: " + sales);
		}if (selectedOption == 2){
			FoodItem itemSave = null;
			Integer itemMax = 0;
			for (FoodItem item : admin.get.getFoodMenuData()) {
				if (item.itemOrdered > itemMax) itemMax = item.itemOrdered; itemSave = item;
			}
			if (itemSave == null) return;
			System.out.println("Most Sold Item:---");
			genericFunctions.printItem(itemSave);
		}
	}

	static void adminManageRecords(Admin admin){
		while (true){
			Integer selectedOption;
			selectedOption = inputTaker("View Daily Report", "Previous Menu");
			if (selectedOption == null){
				System.out.println("Invalid Input");
				continue;
			}
			if (selectedOption == 2){ break;}
			if (selectedOption == 1){ viewDailyReport(admin);}
		}
	}

	public static void adminOptions(FoodOrderingSystem mainSystem){
		Vector<String> userInput = loginPopup();
		Admin newAdmin;
		if (!mainSystem.verifyAdmin(userInput)){
			System.out.println("Bad Credentials.");
			return;
		}
		System.out.println("Login Successful!");
		newAdmin = mainSystem.get.adminData.getAdminById(userInput.get(0));
		while (true){
			mainSystem.set.updateSavedItems();
			Integer selectedOption = inputTaker("Manage Menu", "Manage Orders", "Manage Records", "LogOut as Admin");
			if (selectedOption == null){
				continue;
			}
			if (selectedOption == 4){
				newAdmin.logOut();
				break;
			}
			if (selectedOption == 1){ adminManageMenu(newAdmin);}
			if (selectedOption == 2){ adminManageOrder(newAdmin);}
			if (selectedOption == 3){ adminManageRecords(newAdmin);}
		}
		pass();
	}

	@SuppressWarnings("all")    // Incorrect Warnings Were There
	static void showMembershipStatus(Customer customer){
		if (customer.VIPStatus && customer.customerLogin){
			System.out.println("Membership Status:\tVIP");
		}else if (customer.VIPStatus && !customer.customerLogin){
			System.out.println("Membership not valid!!!");
			System.out.println("Login Again. Or Use as Guest");
		}else if (!customer.VIPStatus && !customer.customerLogin){
			System.out.println("Membership Status:\tAnonymous Login");
		}else{
			System.out.println("Membership Status:\tRegular Membership");
		}
	}

	static void changeMembershipStatus(Customer customer){
		System.out.print("Current ");
		showMembershipStatus(customer);
		if (!customer.customerLogin){
			while (true){
				Integer option = inputTaker("Register Account", "Continue Being Guest");
				if (option == null) {
					System.out.println("Invalid Input");
					continue;
				}
				if (option == 2){ break;}
				if (option == 1) {
					Vector<Object> a = registerPopup(customer);
					customer.set.registerLogin(genericFunctions.toString(a.get(0)), genericFunctions.toString(a.get(1)));
					customer.set.customerName(genericFunctions.toString(a.get(2)));
					customer.set.customerID((int) a.get(3));
					customer.set.initiateLogin(genericFunctions.toString(a.get(0)), genericFunctions.toString(a.get(1)));
					System.out.println("User Account Created Successfully!");
					break;
				}
			}
		}
		if (!customer.VIPStatus){
			while (true){
				Integer option = inputTaker("Become VIP", "Continue Being Regular Member");
				if (option == null) {
					System.out.println("Invalid Input");
					continue;
				}
				if (option == 2){ break;}
				if (option == 1){
					System.out.println("Why? VIP??, To have privileges for priority orders and supporting us.");
					System.out.println("To Become You Have to Pay 5 Million Dollars.");
					while (true){
						Integer op = inputTaker("Pay", "Leave");
						if (op == null) {
							System.out.println("Invalid Input");
							continue;
						}
						if (op == 2){ break;}
						if (op == 1){
							System.out.println("Payment Successful!");
							customer.set.makeVIP();
							System.out.println("Thanks For Supporting Us.");
							break;
						}
					}
					break;
				}

			}
		}else{
			System.out.println("You are already VIP... Enjoy Seamless Services");
		}
	}

	static void customerManageMembership(Customer customer){
		while (true){
			Integer selectedOption;
			selectedOption = inputTaker("Current Status", "Change Membership", "Previous Menu");
			if (selectedOption == null){
				System.out.println("Invalid Input");
				continue;
			}
			if (selectedOption == 3){ break;}
			if (selectedOption == 1){ showMembershipStatus(customer);}
			if (selectedOption == 2){ changeMembershipStatus(customer);}
		}
	}

	static void customerSearchItem(Customer customer){
		System.out.println("Last 5 Search History:-\t");
		int a = 1;
		for (int i = customer.searchHistory.size() < 5 ? 0 : customer.searchHistory.size() - 5; i < customer.searchHistory.size(); i++){
			System.out.println(a++ + ".\t" + customer.searchHistory.get(i));
		}
		if (customer.searchHistory.isEmpty()){
			System.out.println("No Search History");
		}

		System.out.print("\nSearch Input:\t");
		String input = kybrd.nextLine();
		customer.searchHistory.add(input);
		a = 1;
		genericFunctions.printWithSpacing("S.no.", 7, "Name Of Food", 35, "Category", 15, "Veg Or Non-Veg", 20,"Price", 10, "Item No.", 10);
		System.out.println();
		for (FoodItem i : customer.get.getFoodMenuData()){
			if (i.nameOfFood.toLowerCase().contains(input.toLowerCase())){
				genericFunctions.printWithSpacing(a++, 7, i.nameOfFood, 35, i.typeDet, 15, i.vegetarian ? "Veg" : "Non Veg", 20, i.price, 10, i.FoodID, 10);
				System.out.println();
			}
		}
		addToCart(customer);
	}

	static void filterByCategory(Customer customer){
		AtomicInteger a = new AtomicInteger(1);
		Integer internalInput = inputTaker("Veg", "Non Veg", "Any");
		Vector<String> allCategories = new Vector<>();
		customer.get.getFoodMenuData().forEach(item -> {if (!allCategories.contains(item.typeDet)){allCategories.add(item.typeDet);}});
		allCategories.add("Skip");
		allCategories.add("Go To Previous Menu");
		Integer input = inputTaker(allCategories);
		if (internalInput == null)	internalInput = 3;
		if (input == null) input = allCategories.size()-1;
		boolean veg = internalInput == 1;
		int input1 = input;
		Integer internal = internalInput;
		System.out.println("Category:\t" + allCategories.get(input-1));
		if (input == allCategories.size()){ return;}
		genericFunctions.printWithSpacing("S.no", 7, "Name Of Item", 35, "Price", 10, "Item No.", 10, "Veg Or Non-Veg", 30);
		System.out.println();
		customer.get.getFoodMenuData().forEach(item -> {if ((item.typeDet.equals(allCategories.get(input1-1)) || input1 == allCategories.size()-1) && (item.vegetarian == veg || internal == 3)){ genericFunctions.printWithSpacing(a.getAndIncrement(), 7, item.nameOfFood, 35, item.price, 10, item.FoodID, 10, item.vegetarian ? "Veg" : "Non Veg", 30); System.out.println();}});
		System.out.println();
		addToCart(customer);
	}

	static void addToCart(Customer customer){
		System.out.print("Select Item To Add To Cart By Item no. [0 to Skip]:\t");
		int no = 0;
		try {
			no = kybrd.nextInt();
			kybrd.nextLine();
		}catch(Exception e){
			kybrd.nextLine();
			System.out.println("Invalid Input");
		}
		if (no == 0){
			return;
		}
		boolean itemFound = false; FoodItem temp = null;
		for (FoodItem i : customer.get.getFoodMenuData()){
			if (i.FoodID == no){
				itemFound = true;
				temp = i;
				System.out.println("Item Details:--");
				genericFunctions.printItem(i);
				System.out.println();
				break;
			}
		}
		if (!itemFound){
			System.out.println("No item found");
		}else{
			System.out.println("Enter the the quantity:\t");
			int quantity = 1;
			try{
				quantity = kybrd.nextInt(); kybrd.nextLine();
			}catch (Exception e){
				kybrd.nextLine();
				System.out.println("Invalid Input Adding single quantity");
			}
			try {
				customer.cart.addItemByCount(temp, quantity);
			}catch (OUTOFSTOCK e){
				System.out.println(e.getMessage());
			}
			System.out.println("Item Added To Cart Successfully!");
		}
	}
	static void sortedMenu(Customer customer){
		Integer selectedOption = inputTaker("Sort By Latest Item", "Sort By Most Ordered", "Sort By Low To High Price", "Sort Alphabetically","Previous Menu");
		if (selectedOption == null || selectedOption == 5){ return;}
		if (selectedOption == 1) customer.get.getFoodMenuData().sort(FoodItem.BY_FOOD_ID.reversed());
		if (selectedOption == 2) customer.get.getFoodMenuData().sort(FoodItem.BY_ITEM_ORDERED.reversed());
		if (selectedOption == 3) customer.get.getFoodMenuData().sort(FoodItem.BY_PRICE);
		if (selectedOption == 4) customer.get.getFoodMenuData().sort(FoodItem.BY_NAME);
		viewFoodItems(customer);
		customer.get.getFoodMenuData().sort(FoodItem.BY_FOOD_ID);
	}

	static void customerBrowseMenu(Customer customer){
		while (true){
			Integer selectedOption;
			selectedOption = inputTaker("View All Items", "Search Item", "Filter By Category", "Sort", "Previous Menu");
			if (selectedOption == null){
				System.out.println("Invalid Input");
				continue;
			}
			if (selectedOption == 5){ break;}
			if (selectedOption == 1) viewFoodItems(customer);
			if (selectedOption == 2) customerSearchItem(customer);
			if (selectedOption == 3) filterByCategory(customer);
			if (selectedOption == 4) sortedMenu(customer);
		}
	}
	static void viewCartItems(Customer customer){
		genericFunctions.printCart(customer.cart);
	}
	static void manageCartQuantity(Customer customer){
		viewCartItems(customer);
		System.out.println("Enter the S.no [0 to Skip]:\t");
		int no;
		try{
			no = kybrd.nextInt(); kybrd.nextLine();
		}catch (Exception e){
			kybrd.nextLine();
			System.out.println("Going to Previous Menu");
			return;
		}
		if (no == 0){
			System.out.println("Going to Previous Menu");
			return;
		}
		no--;
		Integer inp = inputTaker("Add Quantity", "Reduce Quantity", "Remove Item", "Previous Menu");
		if (inp == null || inp == 4){
			System.out.println("Going to Previous Menu");
			return;
		}
		if (inp == 1) {
			int amount;
			try{
				amount = kybrd.nextInt(); kybrd.nextLine();
			}catch (Exception e){
				kybrd.nextLine();
				System.out.println("Invalid Quantity");
				System.out.println("Incrementing single quantity");
				try{
				customer.cart.incrementItem(customer.cart.orderItems.get(no).x);
				}catch (OUTOFSTOCK a){
					System.out.println(a.getMessage());
				}
				return;
			}
			if (amount < 0){
				System.out.println("Invalid Quantity");
				System.out.println("Incrementing single quantity");
				try{
					customer.cart.incrementItem(customer.cart.orderItems.get(no).x);
				}catch (OUTOFSTOCK e){
					System.out.println(e.getMessage());
				}
				return;
			}
			try {
				customer.cart.addItemByCount(customer.cart.orderItems.get(no).x, amount);
				System.out.println("Quantity Changed");
			}catch (OUTOFSTOCK e){
				System.out.println(e.getMessage());
			}

		}else if (inp == 2){
			System.out.print("Enter the Amount to be reduced:\t");
			int amount;
			try{
				amount = kybrd.nextInt(); kybrd.nextLine();
			}catch (Exception e){
				kybrd.nextLine();
				System.out.println("Invalid Quantity");
				System.out.println("Decrementing single quantity");
				customer.cart.decrementItem(customer.cart.orderItems.get(no).x);
				return;
			}
			if (amount < 0 || amount > customer.cart.orderItems.get(no).y) {
				System.out.println("Invalid Quantity");
				System.out.println("Decrementing single quantity");
				customer.cart.decrementItem(customer.cart.orderItems.get(no).x);
				return;
			}
			customer.cart.reduceItemByCount(customer.cart.orderItems.get(no).x, amount);
			System.out.println("Quantity Changed");
		}
		else if (inp == 3){
			customer.cart.removeItem(customer.cart.orderItems.get(no).x);
			System.out.println("Item Removed");
		}
	}
	static void viewCartTotal(Customer customer){
		AtomicInteger total = new AtomicInteger(0);
		customer.cart.orderItems.forEach(item -> total.getAndAdd(item.x.price*item.y));
		System.out.println("Total Amount To Pay After GST:\t" + total.get()*118/100);
	}
	static void checkoutCart(Customer customer){
		viewCartItems(customer);
		viewCartTotal(customer);
		Integer p = inputTaker("Any Special Request", "Skip");
		if (p == null){
			System.out.println("Invalid Input, Skipping");
		}else if (p == 1){
			System.out.print("Enter the request:\t");
			customer.cart.setSpecialRequest(kybrd.nextLine());
		}
		Integer k = inputTaker("Place Order", "Previous Menu");
		if (k == null){
			System.out.println("Invalid Input");
			return;
		}if (k == 2) return;
		if (k == 1){
			System.out.println("Payment Successful");
			customer.set.placeOrder();
			customer.instance.set.updateSavedItems();
		}
	}

	static void customerManageCart(Customer customer){
		pass();
		while (true){
			Integer selectedOption;
			selectedOption = inputTaker("View Cart Items", "Manage Quantity", "View Amount", "Checkout", "Previous Menu");
			if (selectedOption == null){
				System.out.println("Invalid Input");
				continue;
			}
			if (selectedOption == 5) break;
			if (selectedOption == 1) viewCartItems(customer);
			if (selectedOption == 2) manageCartQuantity(customer);
			if (selectedOption == 3) viewCartTotal(customer);
			if (selectedOption == 4) checkoutCart(customer);
		}
	}
	static void giveReview(Customer customer){
		System.out.println("All Unreviewed Orders.");
		genericFunctions.printWithSpacing("S.no.", 10, "Order Id", 16, "Order Status", 17, "OrderTotalPrice", 14);
		System.out.println();
		AtomicInteger a = new AtomicInteger(1);
		customer.previousOrders.forEach(order -> { if (order.feedback == null) {genericFunctions.printWithSpacing(a.getAndIncrement(), 10, order.getOrderId(), 16, order.getStatus(), 17, order.getTotalPrice(), 14);
			System.out.println();}});
		System.out.println();
		System.out.print("Enter the Order ID to give Feedback [0 to Skip]:\t");
		int inp = 0;
		try{
			inp = kybrd.nextInt(); kybrd.nextLine();
		}catch (Exception e){
			kybrd.nextLine();
			System.out.println("Invalid Input");
			giveReview(customer);
		}
		if (inp == 0){
			System.out.println("Going to Previous Menu");
			return;
		}
		for (Order order : customer.previousOrders){
			if (order.feedback != null && order.getOrderId().equals(inp)){
				System.out.println("Feedback already given.");
				return;
			}
			else if (order.getOrderId().equals(inp)){
				System.out.print("Enter the Review:\t");
				order.setFeedback(kybrd.nextLine());
				break;
			}
		}
	}
	static void viewReviews(Customer customer){
		System.out.println("All Reviewed Orders.");
		customer.previousOrders.forEach(order -> { if (order.feedback != null) genericFunctions.printOrder(order);});
	}
	static void customerManageReviews(Customer customer){
		while (true){
			Integer selectedOption;
			selectedOption = inputTaker("Give Review", "View Reviews", "Previous Menu");
			if (selectedOption == null){
				System.out.println("Invalid Input");
				continue;
			}
			if (selectedOption == 3){ break;}
			if (selectedOption == 1) giveReview(customer);
			if (selectedOption == 2) viewReviews(customer);
		}
	}
	static void customerPreviousOrders(Customer customer){
		System.out.println("All Previous Orders.");
		customer.previousOrders.forEach(order -> genericFunctions.printOrder(order));
		Integer inp = inputTaker("Reorder", "Cancel Order", "Previous Menu");
		if (inp == null || inp == 3) return;
		if (inp == 1){
			System.out.print("Enter the Order ID to reorder:\t");
			int orderID;
			try{
				orderID = kybrd.nextInt();
				kybrd.nextLine();
			}catch(Exception e){
				kybrd.nextLine();
				System.out.println("Invalid Input");
				return;
			}
			customer.set.placeOrder(customer.get.specificPreviousOrder(orderID));
		}if (inp == 2){
			boolean unpreparedOrder = false;
			for (Order order : customer.previousOrders) {
				if (!order.prepared){
					unpreparedOrder = true;
					genericFunctions.printOrder(order);
				}
			}
			if (!unpreparedOrder){
				System.out.println("Cannot Cancel At This Stage, No Unprepared Order");
				return;
			}
			System.out.print("Enter the Order ID to Cancel:\t");
			int can;
			try{
				can = kybrd.nextInt(); kybrd.nextLine();
			}catch (Exception e){
				kybrd.nextLine();
				System.out.println("Invalid Input");
				return;
			}
			for (Order order : customer.previousOrders) {
				if (!order.prepared && order.getOrderId().equals(can)){
					customer.set.initiateRefundForOrder(order);
					System.out.println("Order Refund Requested");
					return;
				}
			}
		}

	}

	public static void customerOptions(FoodOrderingSystem mainSystem){
		Integer loginOption = inputTaker("Login", "Sign Up", "As Guest");
		Customer newCustomer;
		if (loginOption == null){
			System.out.println("Invalid Input.");
			return;
		}
		if (loginOption == 1){
			Vector<String> userInput = loginPopup();
			if (!mainSystem.verifyCustomer(userInput)){
				System.out.println("Login Failed going as Guest.");
				newCustomer = new Customer();
			}else{
				System.out.println("Login Successful!");
				newCustomer = mainSystem.get.customerData.getCustomerById(userInput.get(0));
				newCustomer.customerLogin = true;
			}
		}else if (loginOption == 2){
			Vector<Object> userInput = registerPopup(mainSystem);
			newCustomer = new Customer(genericFunctions.toString(userInput.get(0)), genericFunctions.toString(userInput.get(1)));
			newCustomer.set.customerName(genericFunctions.toString(userInput.get(2)));
			newCustomer.set.customerID((int) userInput.get(3));
			newCustomer.customerLogin = true;
			System.out.println("Registration and Login Successful!");
		} else {
			newCustomer = new Customer();
			System.out.println("Anonymous Login...");
		}
		mainSystem.set.customerData.addCustomer(newCustomer);
		while (true){
			mainSystem.set.updateSavedItems();
			Integer selectedOption = inputTaker("Membership Status", "Browse Menu", "Manage Cart", "Previous Orders", "Give Reviews", "LogOut as Customer");
			if (selectedOption == null){
				System.out.println("Invalid Input!");
				continue;
			}
			if (selectedOption == 6){
				newCustomer.logOut();
				break;
			}
			if (selectedOption == 1){ customerManageMembership(newCustomer);}
			if (selectedOption == 2){ customerBrowseMenu(newCustomer);}
			if (selectedOption == 3){ customerManageCart(newCustomer);}
			if (selectedOption == 4){ customerPreviousOrders(newCustomer);}
			if (selectedOption == 5){ customerManageReviews(newCustomer);}
		}
	}

	Parent root;
	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("assignment-4.fxml"));
		root = fxmlLoader.load();
		Scene scene = new Scene(root);
		stage.setTitle("Byte Me!!");
		stage.setScene(scene);
		stage.show();
	}

	static void SerializeFullSystem(FoodOrderingSystem mainSystem){
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("ByteMe"))) {
			out.writeObject(mainSystem);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	static FoodOrderingSystem LoadFullSystem() {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("ByteMe"))) {
			return (FoodOrderingSystem) in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static void main(String[] args) {
		String nameOfOrganisation;
//		nameOfOrganisation = kybrd.nextLine();
		nameOfOrganisation = "Byte Me!";
		FoodOrderingSystem mainSystem = new FoodOrderingSystem(nameOfOrganisation);
		System.out.println("Welcome To IIITD Canteen App::\t" + mainSystem.get.getSysName());
		mainSystem.set.adminData.addAdmin(new Admin());
		System.out.println("Do You Want To Load Presets:---");
		Integer presetSelection = inputTaker("Yes", "No");
		if (presetSelection == null || presetSelection != 1){
			System.out.println("Not Loading Presets");
		}else{
			FoodOrderingSystem sys = LoadFullSystem();
			if (sys != null){
				mainSystem = sys;
			}else{
				mainSystem.set.loadSavedItems();
				mainSystem.set.loadSavedOrderAndCarts();
			}
		}
		while (true){
			Integer selectedOption = inputTaker("Admin", "Customer", "Exit");
			if (selectedOption == null){
				continue;
			}
			if (selectedOption == 3){
				SerializeFullSystem(mainSystem);
				mainSystem.set.updateSavedItems();
				mainSystem.set.saveOrdersAndCarts();
				break;
			}
			else if (selectedOption == 1){
				adminOptions(mainSystem);
			}
			else if (selectedOption == 2){
				customerOptions(mainSystem);
			}
			SerializeFullSystem(mainSystem);
			mainSystem.set.updateSavedItems();
			mainSystem.set.saveOrdersAndCarts();
		}
		launch();
	}
}