package iiitd.assignment4;
import java.io.Serializable;
import java.util.Comparator;

public class FoodItem implements Serializable {
	public String nameOfFood = "";
	public int price = 0;
	public boolean vegetarian = false;
	public String typeDet = "General";
	public String foodDescription = "";
	public Integer FoodID = null;
	public Integer itemOrdered = 0;
	public Integer foodLimit = 1000;

	@Override
	public String toString(){
		return nameOfFood + "," + price + "," + vegetarian + "," + typeDet + "," + foodDescription + "," + (FoodID != null ? FoodID : "null") + "," + itemOrdered;
	}
	public void setFoodLimit(int limit){
		foodLimit = limit;
	}
	public int getPrice() {
		return price;
	}
	public int getFoodID() {
		return FoodID;
	}
	public String getTypeDet() {
		return typeDet;
	}
	public String getNameOfFood() {
		return nameOfFood;
	}
	public int getItemOrdered() {
		return itemOrdered;
	}
	public String getFoodDescription(){
		return foodDescription;
	}
	public String getVegetarian(){
		return vegetarian ? "Veg" : "Non-Veg";
	}

	public static final Comparator<FoodItem> BY_PRICE = Comparator.comparingInt(FoodItem::getPrice);
	public static final Comparator<FoodItem> BY_NAME = Comparator.comparing(FoodItem::getNameOfFood);
	public static final Comparator<FoodItem> BY_ITEM_ORDERED = Comparator.comparingInt(FoodItem::getItemOrdered);
	public static final Comparator<FoodItem> BY_FOOD_ID = Comparator.comparingInt(FoodItem::getFoodID);


	public FoodItem(String nameOfFood, int price, boolean vegetarian) {
		this.nameOfFood = nameOfFood;
		this.price = price;
		this.vegetarian = vegetarian;
	}
	public FoodItem(){

	}
	void loaderDataFromString(String nameOfFood , String price, String vegetarian, String typeDet, String foodDescription, String FoodID, String itemOrdered){
		this.nameOfFood = nameOfFood;
		this.price = Integer.parseInt(price);
		this.vegetarian = Boolean.parseBoolean(vegetarian);
		this.typeDet = typeDet;
		this.foodDescription = foodDescription;
		this.FoodID = Integer.parseInt(FoodID);
		this.itemOrdered = Integer.parseInt(itemOrdered);
	}
	void loaderDataFromString(String[] itemData){
		loaderDataFromString(itemData[0], itemData[1], itemData[2], itemData[3], itemData[4], itemData[5], itemData[6]);
	}
	void changeTypeDet(String category){
		typeDet = category;
	}
}
