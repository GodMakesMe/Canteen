package com.assignment3.com;
import java.util.*;

public class Main {
	static GenericFunctions genericFunctions = new GenericFunctions();
	static void pass(){
		System.out.println("Facility under process.");
	}
	static Scanner kybrd = new Scanner(System.in);
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
		System.out.print("\tPassword:\t");
		registerInfo.add(kybrd.nextLine());
		System.out.print("\tName Of User:\t");
		registerInfo.add(kybrd.nextLine());
		registerInfo.add(foodOrderingSystem.get.customerData.customerNumber());
		return registerInfo;
	}
	static void viewFoodItems(Admin admin){
		int a = 1;
		genericFunctions.printWithSpacing("S.no.", 5, "Name Of Food", 35, "Category", 15, "Price", 10);
		for (FoodItem i : admin.get.getFoodMenuData()){
			genericFunctions.printWithSpacing(a++, 5, i.nameOfFood, 35, i.typeDet, 15, i.price, 10);
		}
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
			item.foodDescription = drinkDescription;
		}else if (selected == 2 || selected == 3){
			String category = "General";
			if (selected == 3){
				System.out.println("Enter The Food Category:");
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
	}

	static void updateItem(Admin admin){
		viewFoodItems(admin);
		System.out.print("Select S/no To Edit:\t");
		int input;
		try{
			input = kybrd.nextInt();
			kybrd.nextLine();
		}catch (InputMismatchException e){
			kybrd.nextLine();
			System.out.println("Invalid input.");
			return;
		}
		if (input < 1 || input > admin.get.getFoodMenuData().size()){
			System.out.println("Invalid input.");
			return;
		}

		Integer selectedOption = inputTaker("Change Item Name", "Change Item Category", "Change Price", "Previous Menu");
		if (selectedOption == null || selectedOption == 4){ return;}
		if (selectedOption == 1){
			System.out.print("Enter New Name:");
			admin.get.getFoodMenuData().get(input-1).nameOfFood = kybrd.nextLine();
		}if (selectedOption == 2){
			System.out.print("Enter New Category:");

			admin.get.getFoodMenuData().get(input-2).typeDet = kybrd.nextLine();
		}
	}

	static void adminManageMenu(Admin admin){
		while (true){
			Integer selectedOption;
			selectedOption = inputTaker("View Items", "Add New Item", "Update Item", "Remove Item", "Previous Menu");
			if (selectedOption == null){
				System.out.println("Invalid Input");
				continue;
			}
			if (selectedOption == 4){ break;}
			if (selectedOption == 1) viewFoodItems(admin);
			if (selectedOption == 2) addNewItem(admin);
			if (selectedOption == 3) updateItem(admin);
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
					customer.set.registerLogin((String) a.get(0), (String) a.get(1));
					customer.set.customerName((String) a.get(2));
					customer.set.customerID((int) a.get(3));
					customer.set.initiateLogin((String) a.get(0), (String) a.get(1));
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
	static void customerBrowseMenu(Customer customer){
		pass();
		while (true){
			Integer selectedOption;
			selectedOption = inputTaker("View All Items", "Search Item", "Filter By Category", "Sort By Price", "Previous Menu");
			if (selectedOption == null){
				System.out.println("Invalid Input");
				continue;
			}
			if (selectedOption == 5){ break;}
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
			newCustomer = new Customer((String) userInput.get(0), (String) userInput.get(1));
			newCustomer.set.customerName((String) userInput.get(2));
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