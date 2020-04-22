package com.revature.moneyHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.driver.StartingPoint;
import com.revature.model.Account;

public class MoneyHandler {
	
	private static final Logger LOGGYMONEYLOGGER = LogManager.getLogger(MoneyHandler.class);

	public int deposit(Account x, int amountToAdd) {
		if(amountToAdd > 0) {
			x.setBalance(x.getBalance() + amountToAdd);
			LOGGYMONEYLOGGER.debug("deposited to " + StartingPoint.loggedInUser + "Account named" + x.getName() + " " + amountToAdd + " points added");
			return x.getBalance();
		}
		else {
			System.out.println("You've entered an unrecognized amount. Please use your number pad to enter a number greater than 0 next time.");
			return x.getBalance();
		}
	}
	
	public int Withdraw(Account x, int amountToTake) {
		if(x.getBalance() < amountToTake) {
			System.out.println("You've entered an amount larger then the points you've deposited. Transaction canceled");
		}else if (amountToTake > 0) {
			x.setBalance(x.getBalance() - amountToTake);
			LOGGYMONEYLOGGER.debug("Withdraw from " + StartingPoint.loggedInUser + "Account named" + x.getName() + " " + amountToTake + " points taken");
			return x.getBalance();
		}
		return x.getBalance();
	}
	
	public void transfer(Account sender, Account recivier, int amountToTransfer) {
		if(sender.getBalance() < amountToTransfer) {
			System.out.println("You've entered an amount larger then the points you've deposited. Transaction canceled");
		}else if (amountToTransfer > 0) {
			sender.setBalance(sender.getBalance() - amountToTransfer);
			recivier.setBalance(recivier.getBalance() + amountToTransfer);
			LOGGYMONEYLOGGER.debug("transfer from " + sender.getName() + " to " + recivier.getName() + " of " + amountToTransfer + " points");
		} else {
			System.out.println("You've entered an unrecognized amount. Please use your number pad to enter a number greater than 0 next time.");
		}

	}
	

}
