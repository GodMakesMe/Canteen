package com.assignment3.com;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
	static GenericFunctions genericFunctions = new GenericFunctions();
	static void pass(){
		System.out.println("Facility under process.");
	}
	static Scanner kybrd = new Scanner(System.in);
	static Integer inputTaker(Vector<String> inputWise){
		System.out.println("---------------------------------------------------------------------------------------------------");
		short a = 1;
		for (String i : inputWise){
			System.out.println(a++ + ".\t" + i);
		}
//		System.out.println("---------------------------------------------------------------------------------------------------");
		int selectedOption;
		try{
			System.out.print("Select an option [1-" + inputWise.size() + "]:\t");
			selectedOption = kybrd.nextInt(); kybrd.nextLine();
			System.out.println("---------------------------------------------------------------------------------------------------");
		}catch (InputMismatchException e){
			System.out.println("Please enter a valid option! between 1-" + inputWise.size() + ".");
			System.out.println("---------------------------------------------------------------------------------------------------");
			kybrd.nextLine();
			return null;
		}
		if (selectedOption < 1 || selectedOption > inputWise.size()){
			return null;
		}
		return selectedOption;
	}
	static Integer inputTaker(String... inputWise){
		System.out.println("---------------------------------------------------------------------------------------------------");
		short a = 1;
		for (String i : inputWise){
			System.out.println(a++ + ".\t" + i);
		}
//		System.out.println("---------------------------------------------------------------------------------------------------");
		int selectedOption;
		try{
			System.out.print("Select an option [1-" + inputWise.length + "]:\t");
			selectedOption = kybrd.nextInt(); kybrd.nextLine();
			System.out.println("---------------------------------------------------------------------------------------------------");
		}catch (InputMismatchException e){
			System.out.println("Please enter a valid option! between 1-" + inputWise.length + ".");
			System.out.println("---------------------------------------------------------------------------------------------------");
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
		genericFunctions.printWithSpacing("S.no.", 7, "Name Of Food", 35, "Category", 15, "Veg Or Non-Veg", 7,"Price", 10, "Item No.", 10);
		System.out.println();
		for (FoodItem i : admin.get.getFoodMenuData()){
			genericFunctions.printWithSpacing(a++, 7, i.nameOfFood, 35, i.typeDet, 15, i.vegetarian, 7, i.price, 10, i.FoodID, 10);
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
			item = new FoodItem(foodName, foodPrice, veg);
			if (selected == 3){
				item.changeTypeDet(category);
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
		Integer selectedOption = inputTaker("Change Item Name", "Change Item Category", "Change Price", "Change Description", "Previous Menu");
		if (selectedOption == null || selectedOption == 5){ return;}
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

	static void adminManageOrder(Admin admin){
		while (true){
			Integer selectedOption;
			selectedOption = inputTaker("Views Orders", "Manage Order Status", "Show Special Requests", "Previous Menu");
			if (selectedOption == null){
				System.out.println("Invalid Input");
				continue;
			}
			if (selectedOption == 4){ break;}
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
		genericFunctions.printWithSpacing("S.no.", 7, "Name Of Food", 35, "Category", 15, "Veg Or Non-Veg", 7,"Price", 10, "Item No.", 10);
		for (FoodItem i : customer.get.getFoodMenuData()){
			if (i.nameOfFood.contains(input)){
				genericFunctions.printWithSpacing(a++, 7, i.nameOfFood, 35, i.typeDet, 15, i.vegetarian, 7, i.price, 10, i.FoodID, 10);
				System.out.println();
			}
		}
		addToCart(customer);
	}

	static void filterByCategory(Customer customer){
		Vector<String> allCategories = new Vector<>();
		customer.get.getFoodMenuData().forEach(item -> {if (!allCategories.contains(item.nameOfFood)){allCategories.add(item.nameOfFood);}});
		allCategories.add("Go To Previous Menu");
		Integer input = inputTaker(allCategories);
		if (input == null || input == allCategories.size()){ return;}
		System.out.println("Category:\t" + allCategories.get(input-1));
		Integer internalInput = inputTaker("Veg", "Non Veg", "Any");
		genericFunctions.printWithSpacing("S.no", 7, "Name Of Item", 35, "Price", 10, "Item No.", 10, "Veg Or Non-Veg", 30);
		AtomicInteger a = new AtomicInteger(1);
		if (internalInput == null || internalInput == 3){
			customer.get.getFoodMenuData().forEach(item -> {if (item.typeDet.equals(allCategories.get(input-1))) genericFunctions.printWithSpacing(a.getAndIncrement(), 7, item.nameOfFood, 35, item.price, 9, item.FoodID, 9, item.vegetarian, 30); });
		}else{
			boolean veg = internalInput == 1;
			customer.get.getFoodMenuData().forEach(item -> {if (item.typeDet.equals(allCategories.get(input-1)) && item.vegetarian == veg) genericFunctions.printWithSpacing(a.getAndIncrement(), 7, item.nameOfFood, 35, item.price, 9, item.FoodID, 9, item.vegetarian, 30); });
		}


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
		boolean itemFound = false;
		for (FoodItem i : customer.get.getFoodMenuData()){
			if (i.FoodID == no){
				itemFound = true;
				System.out.println("Item Added To Cart Successfully!");
				customer.cart.addItem(i);
				break;
			}
		}
		if (!itemFound){
			System.out.println("No item found");
		}
	}
	static void sortedMenu(Customer customer){
		Integer selectedOption = inputTaker("Sort By Latest Item", "Sort By Most Ordered", "Sort By Price", "Sort Alphabetically","Previous Menu");
		if (selectedOption == null || selectedOption == 5){ return;}
		if (selectedOption == 1) customer.get.getFoodMenuData().sort(FoodItem.BY_FOOD_ID);
		if (selectedOption == 2) customer.get.getFoodMenuData().sort(FoodItem.BY_ITEM_ORDERED);
		if (selectedOption == 3) customer.get.getFoodMenuData().sort(FoodItem.BY_PRICE);
		if (selectedOption == 4) customer.get.getFoodMenuData().sort(FoodItem.BY_NAME);
		viewFoodItems(customer);
		customer.get.getFoodMenuData().sort(FoodItem.BY_FOOD_ID);
	}

	static void customerBrowseMenu(Customer customer){
		pass();
		while (true){
			Integer selectedOption;
			selectedOption = inputTaker("View All Items", "Search Item", "Filter By Category", "Sort", "Previous Menu");
			if (selectedOption == null){
				System.out.println("Invalid Input");
				continue;
			}
			if (selectedOption == 5){ break;}
			if (selectedOption == 1) viewFoodItems(customer);
			if (selectedOption == 2){ customerSearchItem(customer);}
			if (selectedOption == 3) filterByCategory(customer);
			if (selectedOption == 4) sortedMenu(customer);
		}
	}

	static void customerManageCart(Customer customer){
		pass();
		while (true){
			Integer selectedOption;
			selectedOption = inputTaker("View Cart Items", "Add Item", "Manage Quantity", "View Amount", "Checkout", "Previous Menu");
			if (selectedOption == null){
				System.out.println("Invalid Input");
				continue;
			}
			if (selectedOption == 6){ break;}
		}
	}

	static void customerMangeReviews(Customer customer){
		pass();
		while (true){
			Integer selectedOption;
			selectedOption = inputTaker("Give Review", "View Reviews", "Previous Menu");
			if (selectedOption == null){
				System.out.println("Invalid Input");
				continue;
			}
			if (selectedOption == 3){ break;}
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
			Integer selectedOption = inputTaker("Membership Status", "Browse Menu", "Manage Cart", "Manage Reviews", "LogOut as Customer");
			if (selectedOption == null){
				System.out.println("Invalid Input!");
				continue;
			}
			if (selectedOption == 5){
				newCustomer.logOut();
				break;
			}
			if (selectedOption == 1){ customerManageMembership(newCustomer);}
			if (selectedOption == 2){ customerBrowseMenu(newCustomer);}
			if (selectedOption == 3){ customerManageCart(newCustomer);}
			if (selectedOption == 4){ customerMangeReviews(newCustomer);}
		}
	}

	public static void main(String[] args) {
		String nameOfOrganisation;
//		nameOfOrganisation = kybrd.nextLine();
		nameOfOrganisation = "Byte Me!";
		FoodOrderingSystem mainSystem = new FoodOrderingSystem(nameOfOrganisation);
		System.out.println("Welcome To IIITD Canteen App::\t" + mainSystem.get.getSysName());
		mainSystem.set.adminData.addAdmin(new Admin());
		while (true){
			Integer selectedOption = inputTaker("Admin", "Customer", "Exit");
			if (selectedOption == null){
				continue;
			}
			if (selectedOption == 3){
				break;
			}
			else if (selectedOption == 1){
				adminOptions(mainSystem);
			}
			else if (selectedOption == 2){
				customerOptions(mainSystem);
			}
		}
	}
}