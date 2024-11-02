package com.assignment3.com;

public class GenericFunctions {
	Integer startingValue = 0;
	<T> void printWithSpacing(T a, int a1){
		String formattedString = String.format("%-"+ a1 +"s", (String) a);
		System.out.print(formattedString);
	}
	<T> void  printWithSpacing(T a, int a1, T b, int b1) {      // Sundarta
		String formattedString = String.format("%-" + a1 + "s%-" + b1 + "s", (String) a, (String) b);
		System.out.print(formattedString);
	}
	<T> void printWithSpacing(T a, int a1, T b, int b1, T c, int c1) {
		String formattedString = String.format("%-" + a1 + "s%-" + b1 + "s%-" + c1 + "s", (String) a, (String) b, (String) c);
		System.out.print(formattedString);
	}
	<T> void printWithSpacing(T a, int a1, T b, int b1, T c, int c1, T d, int d1) {
		printWithSpacing(a,a1,b,b1,c,c1);
		printWithSpacing(d, d1);
	}


}
