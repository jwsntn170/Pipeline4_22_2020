package com.revature.util;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MyScannerHandler {
	
	//Scanner UserInput = new Scanner(System.in);
    private final Scanner UserInput;
    private final PrintStream out;
	
//	public Scanner setUp(){
//		return UserInput;
//	}
	
	public void clear(){
		UserInput.nextLine();
	}
	
	public String getAString(String message) {
        out.println(message);
		String lastInput;
		try {
			lastInput = UserInput.nextLine();
		}
		catch(InputMismatchException e) {
			//System.out.println();
			lastInput = getAString("Please try again.");
		}

		return lastInput;
	}
	
	public int getAInt(String message) {
        out.println(message);
		int lastInputNum = 0;
		try {
			lastInputNum = UserInput.nextInt();
		}
		catch(InputMismatchException e) {
			//System.out.println("That is not a number. Please try again using the number pad.");
			UserInput.nextLine();
			lastInputNum = getAInt("That is not a number. Please try again using the number pad.");
		}

		return lastInputNum;
	}
	
	public MyScannerHandler(InputStream in, PrintStream out) {
		UserInput = new Scanner(in);
        this.out = out;
    }
	
	public int ask(String message) {

        return UserInput.nextInt();
    }
}
