package com.revature.model;

import java.util.HashSet;
import java.util.Iterator;

public class JointAccountApplication {
	
	int jointAccountID;
	public Account theAccount;
	HashSet<User> userThatAreAccsessable = new HashSet<User>();
	boolean isApproved = false;
	User hostUser;
	
	public void addAUser(String name) {
		User newUser = new User (name, "password", 0);
		userThatAreAccsessable.add(newUser);
	}
	
	public void addAUser(User newUser) {
		//account newAccount = name;
		userThatAreAccsessable.add(newUser);
	}
	
	public void ListUser() {
		//account newAccount = name;
		Iterator<User> i = userThatAreAccsessable.iterator(); 
		 while (i.hasNext()) {
			User currentAccount = i.next();
		    System.out.println("User name: " + currentAccount.name);
		    //list connected accounts
		    //checkAccountsBalance(currentUser);
		}
	}
	
	public void ApproveAccountToAllUsers() {
		theAccount.setHostjointUser(hostUser.getName());
		Iterator<User> i = userThatAreAccsessable.iterator(); 
		 while (i.hasNext()) {
			User currentAccount = i.next();
			theAccount.setJoint(true);
			currentAccount.addAnAccount(theAccount);
		}
	}
	
	//pojo:
	
	public Account getTheAccount() {
		return theAccount;
	}
	public int getJointAccountID() {
		return jointAccountID;
	}

	public void setJointAccountID(int jointAccountID) {
		this.jointAccountID = jointAccountID;
	}

	public void setTheAccount(Account theAccount) {
		this.theAccount = theAccount;
	}
	public HashSet<User> getAccountsThatAreAccsessable() {
		return userThatAreAccsessable;
	}
	public void setAccountsThatAreAccsessable(HashSet<User> accountsThatAreAccsessable) {
		this.userThatAreAccsessable = accountsThatAreAccsessable;
	}
	public boolean isApproved() {
		return isApproved;
	}
	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
	
	public JointAccountApplication() {
		super();
		
	}
	public JointAccountApplication(Account theAccount, HashSet<User> usersThatAreAccsessable, boolean isApproved) {
		super();
		this.theAccount = theAccount;
		this.userThatAreAccsessable = usersThatAreAccsessable;
		this.isApproved = isApproved;
	}

	public User getHostUser() {
		return hostUser;
	}

	public void setHostUser(User hostUser) {
		this.hostUser = hostUser;
	}
	

	
	
}
