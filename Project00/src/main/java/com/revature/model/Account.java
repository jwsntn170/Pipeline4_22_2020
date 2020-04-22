package com.revature.model;


public class Account{
	String name = "checking";
	int balance = 0;
	boolean isJoint = false;
	String HostjointUser;
	
	//POJO Below
	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public boolean isJoint() {
		return isJoint;
	}

	public void setJoint(boolean isJoint) {
		this.isJoint = isJoint;
	}


	public Account() {
		super();
	}

	public Account(String name, int balance) {
		super();
		this.name = name;
		this.balance = balance;
	}

	
	public Account(String name, int balance, boolean isJoint) {
		super();
		this.name = name;
		this.balance = balance;
		this.isJoint = isJoint;
	}

	public Account(String name, int balance, boolean isJoint, String hostName) {
		super();
		this.name = name;
		this.balance = balance;
		this.isJoint = isJoint;
		this.HostjointUser = hostName;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + balance);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Account other = (Account) obj;
		if (balance != other.balance)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "account [name " + name + ", balance " + balance + "]";
	}

	public String getHostjointUserName() {
		return HostjointUser;
	}

	public void setHostjointUser(String hostjointUser) {
		HostjointUser = hostjointUser;
	}
	
	
	
	
}
