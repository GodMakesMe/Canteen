package com.assignment3.com;

public class GenericFunctions {
	Integer startingValue = 0;
	void printWithSpacing(String a, int a1){
		String formattedString = String.format("%-"+a+"s", a);
		System.out.print(formattedString);
	}
	void printWithSpacing(String a, int a1, String b, int b1) {      // Sundarta
		String formattedString = String.format("%-" + a1 + "s%-" + b1 + "s", a, b);
		System.out.print(formattedString);
	}
	void printWithSpacing(String a, int a1, String b, int b1, String c, int c1) {
		String formattedString = String.format("%-" + a1 + "s%-" + b1 + "s%-" + c1 + "s", a, b, c);
		System.out.print(formattedString);
	}
	void printWithSpacing(String a, int a1, String b, int b1, String c, int c1, String d, int d1) {
		printWithSpacing(a,a1,b,b1,c,c1);
		printWithSpacing(d, d1);
	}

}
