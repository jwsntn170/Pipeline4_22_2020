package com.revature.driver;

//import java.io.InputStream;
//import java.io.PrintStream;
import java.util.HashSet;
import java.util.Iterator;

import com.revature.model.Account;
import com.revature.model.JointAccountApplication;
import com.revature.model.User;
import com.revature.moneyHandler.MoneyHandler;
import com.revature.repository.BankingRepositoryImpl;
import com.revature.util.MyScannerHandler;

public class StartingPoint {
	
	static HashSet<User> approvedUsers = new HashSet<User>();
	static HashSet<User> pendingUsers = new HashSet<User>();
	static HashSet<JointAccountApplication> pendingJointAccounts = new HashSet<JointAccountApplication>();
	static String lastInput = "Last Input";
	static int lastInputNum = 0;
	public static User loggedInUser;
	static boolean userLogedIn = false;
	static int runProgram = 1;
	static MoneyHandler pointHandler;
	static BankingRepositoryImpl bankingRepositoryImpl;
	
	public static void main(String[] args) {

		setUp();
		//open the scanner
		MyScannerHandler userInput = new MyScannerHandler(System.in, System.out);
		
		//get the user to log in:
		do {
			do {
				LogInScreen(userInput);
			} while (!userLogedIn);
			System.out.println("Logged in. Welcome " + loggedInUser.getName());
			
			//Send them to the screen to handle their accounts by their access level
			switch (loggedInUser.getUserAccessLevel()) {
			case 0:
				normalUserMenu(userInput);
				break;
			case 1:
				employeeUserMenu(userInput);
				break;
			default:
				//something weird has occurred 
			}
			
			//System.out.println();
			runProgram = userInput.getAInt("Would you like to quit? Enter 0 to exit, 1 to return to the main menu");
			userInput.clear();
		} while (runProgram > 0);
		
		//update the data:
		ending();

		
	}
	
	static void setUp(){
		userLogedIn = false;
		bankingRepositoryImpl = new BankingRepositoryImpl();
		pointHandler = new MoneyHandler();
		//get usernames and passwords from approved users and pending users
		approvedUsers = bankingRepositoryImpl.getAllApprovedUsers();
		//get users that are pending
		pendingUsers =  bankingRepositoryImpl.getAllPendingUsers();
		// set joint accounts
		setUpJointAccounts();
		
	}
	
	static void setUpJointAccounts(){
		Account mergedAccount = null;
		Iterator<User> j = approvedUsers.iterator(); 
	    while (j.hasNext()) {
	    	User userToCheck = j.next();
	    
			HashSet<Account> accounts = userToCheck.getAccountsThatAreAccsessable();
			
			Iterator<Account> i = accounts.iterator(); 
		    while (i.hasNext()) {
		    		Account currentAccount = i.next();
		    		if(currentAccount.isJoint()) {
		    			if(currentAccount.getHostjointUserName() == userToCheck.getName()) {
		    				//doNothing
		    			}else {
		    				mergedAccount = checkIfUserNameExist(currentAccount.getHostjointUserName()).checkForAccount(currentAccount.getName());
		    				//userToCheck.removeAccount(currentAccount);
		    				i.remove();
		    				
		    			}
		    		}
		    }
		    userToCheck.addAnAccount(mergedAccount);
		}
	}
	
	static void ending(){
		bankingRepositoryImpl.DropOldTables();
		bankingRepositoryImpl.DropOldTables2();
		bankingRepositoryImpl.DropOldTables3();
		bankingRepositoryImpl.createNewTables();
		bankingRepositoryImpl.createNewTables2();
		bankingRepositoryImpl.createNewTables3();
		
		//insert pending users
		Iterator<User> i = pendingUsers.iterator(); 
	    while (i.hasNext()) {
	    	bankingRepositoryImpl.insertPendingUser(i.next());
	    }
	    
	  //insert approved users and their accounts
	    Iterator<User> j = approvedUsers.iterator(); 
	    while (j.hasNext()) {
	    	User currentUser = j.next();
	    	bankingRepositoryImpl.insertApprovedUser(currentUser);
	    	//add all his accounts
		    Iterator<Account> a = currentUser.getAccountsThatAreAccsessable().iterator();
		    while (a.hasNext()) {
		    	bankingRepositoryImpl.insertAccount(currentUser, a.next());
		    }
	    }
	}
	
	public static void setUpForTest(){
		approvedUsers = new HashSet<User>();
		pendingUsers = new HashSet<User>();
		//settingUpBasics and examples
		approvedUsers.add(new User("Admin", "password", 1));
		approvedUsers.add(new User("Doug", "password", 0));
		
		
		pendingUsers.add(new User("Spot", "password", 0));
		pendingUsers.add(new User("Barkandson", "password", 1));
		
	}

	static void LogInScreen(MyScannerHandler userInput) {
		System.out.println("");  
		System.out.println("                 / \\__");
		System.out.println("                (    @\\___");
		System.out.println("                /         O");
		System.out.println("               /   (_____/");
		System.out.println("              /_____/   U");
		System.out.println("Welcome to a dog's Bank of Good Boy Points.");
		System.out.println("1 - Log into a existenting user");
		lastInputNum = userInput.getAInt("2 - Apply for a new user");
		userInput.clear();
		switch (lastInputNum) {
		case 1:
			//System.out.println();
			lastInput = userInput.getAString("Please enter your username (Case Sensitive): ");
			
			//System.out.println();
			String lastInput2 = userInput.getAString("Please enter your password (Case Sensitive): ");
			//check if user name exist
			loggedInUser = checkIfUserNameExistwithPassword(lastInput, lastInput2);
			break;
		case 2:
			//System.out.println();
			lastInput = userInput.getAString("Please enter a username (Case Sensitive): ");
			
			//System.out.println();
			String lastInput3 = userInput.getAString("Please enter a password (Case Sensitive): ");
			//check if user name exist
			
			applyForNewUser(lastInput, lastInput3);
			break;
		}
		if(!userLogedIn) {
			System.out.println("User and password did not match our system. Please try again.");
		}
	}
	
	public static User checkIfUserNameExistwithPassword(String name, String password) {
		Iterator<User> i = pendingUsers.iterator(); 
	    while (i.hasNext()) {
	    	if (i.next().getName().equals(name)) {
	    		System.out.println("Your account is in our system and waiting to be approved");
	    	}
	    }
	    
	    Iterator<User> j = approvedUsers.iterator(); 
	    while (j.hasNext()) {
	    	User currentUser = j.next();
	    	if (currentUser.getName().equals(name)) {
	    		if (currentUser.getPassword().equals(password)) {
		    		userLogedIn = true;
		    		return currentUser;
	    		}
	    	}
	    }
	    
		return null;
	}
		
	public static User checkIfUserNameExist(String name) {
		Iterator<User> i = pendingUsers.iterator(); 
	    while (i.hasNext()) {
	    	if (i.next().getName().equals(name)) {
	    		System.out.println("Your account is in our system and waiting to be approved");
	    	}
	    }
	    
	    Iterator<User> j = approvedUsers.iterator(); 
	    while (j.hasNext()) {
	    	User currentUser = j.next();
	    	if (currentUser.getName().equals(name)) {
	    		//System.out.println("Your account has been found.");
	    		userLogedIn = true;
	    		return currentUser;
	    	}
	    }
	    
		return null;
	}
	
	public static boolean checkIfAccountNameExist(String accountName, User user) {
		
		Iterator<Account> i = user.getAccountsThatAreAccsessable().iterator(); 
	    while (i.hasNext()) {
	    	if (i.next().getName().equals(accountName)) {
	    		//System.out.println("Your account was found");
	    		return true;
	    	}
	    }
	    System.out.println("An account by that name was not found in our system. Transaction canceled");
	    return false;
	    
	}
	
	public static boolean applyForNewUser(String accountName, String password) {
		User userToCheck = checkIfUserNameExist(accountName);
		userLogedIn = false;
		if (userToCheck == null) {
			pendingUsers.add(new User(accountName, password, 0));
			System.out.println("your user has been added to our pending system. Please see an employee for approval.");
			return true;
		} else {
			System.out.println("That username is already taken. try again.");
		}
		return false;
	}
	
	static void normalUserMenu(MyScannerHandler userInput) {
		do {
			System.out.println("Please enter a number using the Num pad to make a choice");
			System.out.println("1 - check accounts balance");
			System.out.println("2 - Open a new account or join a joint account");
			System.out.println("3 - Deposit points");
			System.out.println("4 - Withdraw points");
			System.out.println("5 - Transfer points");
//			System.out.println("6 - Account History");
			//System.out.println();
			lastInputNum = userInput.getAInt("0 - Log Out");
			switch (lastInputNum) {
			
			case 0:
				userLogedIn = false;
				break;
			case 1:
				checkAccountsBalance(loggedInUser);
				break;
			case 2:
				openANewAccount(userInput);
				break;
			case 3:
				deposit(userInput);
				break;
			case 4:
				withdraw(userInput);
				break;
			case 5:
				transfer(userInput);
				break;
//			case 6:
//				//send a log of details
//				break;
			default:
				
			} 
		} while (userLogedIn);
	}
	
	static void employeeUserMenu(MyScannerHandler userInput) {
		do {
			System.out.println("Please enter a number using the Num pad to make a choice");
			System.out.println("1 - Access the pending user and account applications");
			System.out.println("2 - Access an approved user");
			System.out.println("3 - upgrade a user to employee");
			System.out.println("4 - downgrade a employee to a user");
			System.out.println("5 - cancel an account from a user (Account must be empty)");
			//System.out.println();
			
			lastInputNum = userInput.getAInt("0 - Log Out");
			switch (lastInputNum) {
			
			case 0:
				userLogedIn = false;
				break;
			case 1:
				pendingUserApplications(userInput);
				break;
			case 2:
				accessAnApprovedUser(userInput);
				break;
			case 3:
				upgradeUserToEmployee(userInput, 1);
				break;
			case 4:
				upgradeUserToEmployee(userInput, 0);
				break;
			case 5:
				obliterateAccount(userInput);
				break;
			default:
				
			}
		} while (userLogedIn);
	}
	
	static public void checkAccountsBalance(User userToCheck) {
		HashSet<Account> accounts = userToCheck.getAccountsThatAreAccsessable();
		
		Iterator<Account> i = accounts.iterator(); 
	    while (i.hasNext()) {
	    		Account currentAccount = i.next();
	    		System.out.println(currentAccount.getName() + " Balance " + currentAccount.getBalance());
	    }
	}

	static public void openANewAccount(MyScannerHandler userInput) {
		userInput.clear();
		System.out.println("Please enter a number using the Num pad to make a choice");
		System.out.println("1 - Open a new account");
		System.out.println("2 - join a joint account");
		//System.out.println();
		lastInputNum = userInput.getAInt("0 - Cancel");
		switch (lastInputNum) {
		case 0:
			
			break;
		case 1:
			userInput.clear();
			//System.out.println();
			lastInput = userInput.getAString("What would you like to name the account?");
			loggedInUser.addAnAccount(lastInput);
			break;
		case 2:
			userInput.clear();
			//System.out.println();
			lastInput = userInput.getAString("What is the name of the user you are trying to open a joint account with?");
			User targetUser = checkIfUserNameExist(lastInput);
			//System.out.println();
			lastInput = userInput.getAString("What is the name of the account you are trying to join?");
			
			boolean check = checkIfAccountNameExist(lastInput, targetUser);
			if(check) {
				JointAccountApplication jointApplication = new JointAccountApplication();
				jointApplication.addAUser(loggedInUser);
				jointApplication.addAUser(targetUser);
				jointApplication.setHostUser(targetUser);
				jointApplication.theAccount = targetUser.checkForAccount(lastInput);
				pendingJointAccounts.add(jointApplication);
				//loggedInUser.addAnAccount(targetUser.checkForAccount(lastInput));
			}
			break;
		}

	}

	static public void deposit(MyScannerHandler userInput) {
		userInput.clear();
		//System.out.println();
		lastInput = userInput.getAString("What is the name of the account you are trying to deposite to?");
		//check if it exist loggedInUser
		boolean check = checkIfAccountNameExist(lastInput, loggedInUser);
		
		if(check) {
			//System.out.println();
			lastInputNum = userInput.getAInt("How many points are you depositing?");
			pointHandler.deposit(loggedInUser.checkForAccount(lastInput), lastInputNum);
		}
	}
	
	static public void withdraw(MyScannerHandler userInput) {
		userInput.clear();
		//System.out.println();
		lastInput = userInput.getAString("What is the name of the account you are trying to withdraw from?");
		
		boolean check = checkIfAccountNameExist(lastInput, loggedInUser);
		if(check) {
			//System.out.println();
			lastInputNum = userInput.getAInt("How many points are you withdrawig?");
			pointHandler.Withdraw(loggedInUser.checkForAccount(lastInput), lastInputNum);
		}
	}
	
	static public void transfer(MyScannerHandler userInput) {
		userInput.clear();
		//System.out.println();
		String userAccountInput = userInput.getAString("What is the name of your account you are trying to transfer from?");
		boolean check = checkIfAccountNameExist(lastInput, loggedInUser);
		if(check) {
			//System.out.println();
			lastInput = userInput.getAString("What is the name of the user you are trying to transfer to?");
			User targetUser = checkIfUserNameExist(lastInput);
			
			//System.out.println();
			lastInput = userInput.getAString("What is the name of the account you are trying to transfer?");
			boolean check2 = checkIfAccountNameExist(lastInput, loggedInUser);
			if(check2) {
				//System.out.println();
				lastInputNum = userInput.getAInt("How much would you like to transfer?");
				
				pointHandler.transfer(loggedInUser.checkForAccount(userAccountInput), targetUser.checkForAccount(lastInput), lastInputNum);
			}
		}
	}
	
	static public void pendingUserApplications(MyScannerHandler userInput) {
		System.out.println("Please enter a number using the Num pad to make a choice");
		System.out.println("1 - list pending Users");
		System.out.println("2 - List pending joint account");
		System.out.println("3 - Approve user by name");
		//System.out.println();
		lastInputNum = userInput.getAInt("4 - Approve joint account by name");
		switch (lastInputNum) {
		
		case 1:
			//list pending Users
			 Iterator<User> j = pendingUsers.iterator(); 
			 while (j.hasNext()) {
			    User currentUser = j.next();
			    System.out.println("User: " + currentUser.getName());
			    //list connected accounts
			    //checkAccountsBalance(currentUser);
			}
			break;
		case 2:
			//List pending joint accounts
			int idCounter = 0;
			Iterator<JointAccountApplication> i = pendingJointAccounts.iterator(); 
			 while (i.hasNext()) {
				idCounter++;
			    JointAccountApplication currentAccount = i.next();
			    currentAccount.setJointAccountID(idCounter);
			    currentAccount.ListUser();
			    System.out.println("The Account " + currentAccount.getTheAccount());
			    System.out.println("----------------------------------------------------");
			}
			break;
		case 3:
			//System.out.println();
			userInput.clear();
			lastInput = userInput.getAString("enter user name to be approved");
			User targetUser = null;
			
			Iterator<User> jj = pendingUsers.iterator(); 
		    while (jj.hasNext()) {
		    	User userWeAreSearching = jj.next();
		    	if (userWeAreSearching.getName().equals(lastInput)) {
		    		targetUser = userWeAreSearching;
		    	}
		    }
			
			if (targetUser != null) {
			    pendingUsers.remove(targetUser);
			    approvedUsers.add(targetUser);
			}else {
				System.out.println("Account nameed " + lastInput + " could not be found in pending users. Canceling transaction");
			}
			break;
		case 4:
			//Approve joint account by name
			//System.out.println();
			lastInputNum = userInput.getAInt("enter joint account id to be approved");
			
			Iterator<JointAccountApplication> ii = pendingJointAccounts.iterator(); 
			 while (ii.hasNext()) {
			    JointAccountApplication currentAccount = ii.next();
			    if(currentAccount.getJointAccountID() == lastInputNum) {
			    	currentAccount.ApproveAccountToAllUsers();
			    }
			    
			}
			break;
		}
	}
	
	static public void accessAnApprovedUser(MyScannerHandler userInput) {
		userInput.clear();
		//System.out.println();
		lastInput = userInput.getAString("Enter the user's name");
		User targetUser = checkIfUserNameExist(lastInput);
		User employeeUser = loggedInUser;
		if(targetUser != null) {
			loggedInUser = targetUser;
			//open menu as user
			normalUserMenu(userInput);
			userLogedIn = true;
		}
		loggedInUser = employeeUser;
	}
	
	static public void upgradeUserToEmployee(MyScannerHandler userInput, int upgradeLevel) {
		userInput.clear();
		//System.out.println();
		lastInput = userInput.getAString("Enter the user's name to upgrade them into an employee");
		User targetUser = checkIfUserNameExist(lastInput);
		if(targetUser != null) {
			targetUser.setUserAccessLevel(upgradeLevel);
		}
	}
	
	static public void obliterateAccount(MyScannerHandler userInput) {
		//System.out.println();
		lastInput = userInput.getAString("Enter the name of the user you are trying to remvoe an account from?");
		User targetUser = checkIfUserNameExist(lastInput);
		if(targetUser != null) {
			//System.out.println();
			lastInput = userInput.getAString("Enter the name of the account you are trying to remove?");
			boolean check = checkIfAccountNameExist(lastInput, targetUser);
			if(check) {
				Account accountToRmove = targetUser.checkForAccount(lastInput);
				if(accountToRmove.getBalance() == 0) {
					targetUser.removeAccount(accountToRmove);
				}else {
					System.out.println("The account you are trying to remove has points stored in it. Please empty the points before attempting to delete an account");
				}
			}
		}
	}

	
	//--------------------------------------------POJO--------------------------------------------------------
	public static HashSet<User> getApprovedUsers() {
		return approvedUsers;
	}

	public static void setApprovedUsers(HashSet<User> approvedUsers) {
		StartingPoint.approvedUsers = approvedUsers;
	}

	public static HashSet<User> getPendingUsers() {
		return pendingUsers;
	}

	public static void setPendingUsers(HashSet<User> pendingUsers) {
		StartingPoint.pendingUsers = pendingUsers;
	}

	public static HashSet<JointAccountApplication> getPendingJointAccounts() {
		return pendingJointAccounts;
	}

	public static void setPendingJointAccounts(HashSet<JointAccountApplication> pendingJointAccounts) {
		StartingPoint.pendingJointAccounts = pendingJointAccounts;
	}

	public static String getLastInput() {
		return lastInput;
	}

	public static void setLastInput(String lastInput) {
		StartingPoint.lastInput = lastInput;
	}

	public static int getLastInputNum() {
		return lastInputNum;
	}

	public static void setLastInputNum(int lastInputNum) {
		StartingPoint.lastInputNum = lastInputNum;
	}

	public static User getLoggedInUser() {
		return loggedInUser;
	}

	public static void setLoggedInUser(User loggedInUser) {
		StartingPoint.loggedInUser = loggedInUser;
	}

	public static boolean isUserLogedIn() {
		return userLogedIn;
	}

	public static void setUserLogedIn(boolean userLogedIn) {
		StartingPoint.userLogedIn = userLogedIn;
	}

	public static int getRunProgram() {
		return runProgram;
	}

	public static void setRunProgram(int runProgram) {
		StartingPoint.runProgram = runProgram;
	}

	public static MoneyHandler getPointHandler() {
		return pointHandler;
	}

	public static void setPointHandler(MoneyHandler pointHandler) {
		StartingPoint.pointHandler = pointHandler;
	}
	
	
	
}




