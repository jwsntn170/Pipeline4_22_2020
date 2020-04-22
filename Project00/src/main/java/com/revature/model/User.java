package com.revature.model;


import java.util.HashSet;
import java.util.Iterator;

public class User{
	//accounts this user has access to
	HashSet<Account> accountsThatAreAccsessable = new HashSet<Account>();
	
	//type of user: Employee VS user
	int userAccessLevel = 0; // 0 - normal user. 1- employee.
	String name = "null";
	String password = "null";

	
	public void addAnAccount(String name) {
		Account newAccount = new Account (name, 0);
		accountsThatAreAccsessable.add(newAccount);
	}
	
	public void addAnAccount(Account newAccount) {
		//account newAccount = name;
		accountsThatAreAccsessable.add(newAccount);
	}
	

	public Account checkForAccount(String name) {
		
		Iterator<Account> i = accountsThatAreAccsessable.iterator(); 
	    while (i.hasNext()) {
	    	Account curAccount = i.next();
	    	if (curAccount.getName().equals(name)) {
	    		return curAccount;
	    	}
	    }
	    System.out.println("Account could not be found");
		return null;
	}
	
	public void removeAccount(Account accountToRmove) {
		accountsThatAreAccsessable.remove(accountToRmove);
	}
	
	void createBasicAccounts() {
		Account account1 = new Account("checking", 0);
		Account account2 = new Account("savings", 10);
		addAnAccount(account1);
		addAnAccount(account2);
	}
	
	//Java Bean Below
	public HashSet<Account> getAccountsThatAreAccsessable() {
		return accountsThatAreAccsessable;
	}

	public void setAccountsThatAreAccsessable(HashSet<Account> accountsThatAreAccsessable) {
		this.accountsThatAreAccsessable = accountsThatAreAccsessable;
	}

	public int getUserAccessLevel() {
		return userAccessLevel;
	}

	public void setUserAccessLevel(int userAccessLevel) {
		this.userAccessLevel = userAccessLevel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountsThatAreAccsessable == null) ? 0 : accountsThatAreAccsessable.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + userAccessLevel;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (accountsThatAreAccsessable == null) {
			if (other.accountsThatAreAccsessable != null)
				return false;
		} else if (!accountsThatAreAccsessable.equals(other.accountsThatAreAccsessable))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (userAccessLevel != other.userAccessLevel)
			return false;
		return true;
	}

	
	
	public User() {
		super();
		createBasicAccounts();
		
	}

	public User(String name, String password, int userAccessLevel) {
		super();
		//this.accountsThatAreAccsessable = accountsThatAreAccsessable;
		this.userAccessLevel = userAccessLevel;
		this.name = name;
		this.password = password;
		createBasicAccounts();
	}

	public User(HashSet<Account> accountsThatAreAccsessable, int userAccessLevel, String name, String password) {
		super();
		this.accountsThatAreAccsessable = accountsThatAreAccsessable;
		this.userAccessLevel = userAccessLevel;
		this.name = name;
		this.password = password;
	}
	
	
}
