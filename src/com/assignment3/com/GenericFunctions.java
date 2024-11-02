package com.assignment3.com;

public class GenericFunctions {
	<T> String toString(T a){
		if (a instanceof String) return "" + a;
		if (a instanceof Integer) return "" + a;
		if (a instanceof Double) return "" + a;
		if (a instanceof Float) return "" + a;
		if (a instanceof Boolean) return (Boolean) a ? "true" : "false";
		return "null";
	}
	<T> void print(T a){
		System.out.print(a);
	}
	<T> void print(T a, String end){
		print(a);
		print(end);
	}
	<T> void printWithSpacing(T a, int a1){
		String formattedString = String.format("%-"+ a1 +"s", toString(a));
		System.out.print(formattedString);
	}
	<T> void  printWithSpacing(T a, int a1, T b, int b1) {      // Sundarta
		String formattedString = String.format("%-" + a1 + "s%-" + b1 + "s", toString(a), toString(b));
		System.out.print(formattedString);
	}
	<T> void printWithSpacing(T a, int a1, T b, int b1, T c, int c1) {
		String formattedString = String.format("%-" + a1 + "s%-" + b1 + "s%-" + c1 + "s", toString(a), toString(b), toString(c));
		System.out.print(formattedString);
	}
	<T> void printWithSpacing(T a, int a1, T b, int b1, T c, int c1, T d, int d1) {
		printWithSpacing(a,a1,b,b1,c,c1);
		printWithSpacing(d, d1);
	}
	void printItem(FoodItem item){
		printWithSpacing("Item Category:", 40); print(item.typeDet, "\n");
		printWithSpacing("Item Name:", 40); print(item.nameOfFood, "\n");
		printWithSpacing("Item Price:", 40); print(item.price, "\n");
		printWithSpacing("Item Veg:", 40); print(item.vegetarian, item.vegetarian ? "\tVegetarian\n" : "\tNon-Vegetarian\n");
		printWithSpacing("Item Description:", 40); print(item.foodDescription, "\n");
	}

	<T> void printWithSpacing(T s1, int i, T s2, int i1, T s3, int i2, T s4, int i3, T s5, int i4) {
		printWithSpacing(s1, i, s2, i1, s3,  i2);
		printWithSpacing(s4, i3, s4, i4);
	}
	<T> void printWithSpacing(T s, int i, T nameOfFood, int i1, T category, int i2, T veg, int i3, T price, int i4,  T s1, int i5) {
		printWithSpacing(s, i, nameOfFood, i1, category,  i2, veg, i3);
		printWithSpacing(price, i4, s1, i5);
	}
}
