package com.revature.repository;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.ArrayList;
import java.util.HashSet;
//import java.util.Iterator;
//import java.util.List;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

//import com.revature.driver.*;
import com.revature.model.Account;
import com.revature.model.User;


public class BankingRepositoryImpl implements BankingRepository {
	
	//private static final Logger LOGGY = LogManager.getLogger(BankingRepositoryImpl.class);

	//---------------------------------------------getting the DATA----------------------------------------------
	@Override
	public HashSet<User> getAllApprovedUsers(){
		HashSet<User> approvedUsers = new HashSet<User>();
		final String SQL = "select * from ApprovedUsers";
		Statement stmt = null;
		ResultSet set = null;
		
		try(Connection conn = DriverManager.getConnection(System.getenv("url"), System.getenv("user"), System.getenv("password"))) {
			stmt = conn.createStatement();
			stmt.execute(SQL);
			set = stmt.getResultSet();
			
			while(set.next()) {
				approvedUsers.add(new User(getAllApprovedUsersAccountsByUserName(set.getString(1)),set.getInt(3), set.getString(1), set.getString(2)));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
				set.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//LOGGY.debug("The PolkamanRepository getAllApprovedUsers was successfully called, and it returned: " + approvedUsers);
		return approvedUsers;
	}
	
	@Override
	public HashSet<Account> getAllApprovedUsersAccountsByUserName(String userName){
		
		HashSet<Account> accounts = new HashSet<Account>();
		final String SQL = "SELECT * FROM Accounts WHERE ownerofaccount = '" + userName + "'";
		Statement stmt = null;
		ResultSet set = null;
		
		try(Connection conn = DriverManager.getConnection(System.getenv("url"), System.getenv("user"), System.getenv("password"))) {
			stmt = conn.createStatement();
			stmt.execute(SQL);
			set = stmt.getResultSet();
			
			while(set.next()) {
				accounts.add(new Account( set.getString(1),set.getInt(2), set.getBoolean(3),  set.getString(5)));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
				set.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//LOGGY.debug("The PolkamanRepository getAllApprovedUsers was successfully called, and it returned: " + accounts);
		return accounts;
	}
	
	@Override
	public HashSet<User> getAllPendingUsers(){
		
		HashSet<User> pendingUsers = new HashSet<User>();
		final String SQL = "select * from PendingUsers";
		Statement stmt = null;
		ResultSet set = null;
		
		try(Connection conn = DriverManager.getConnection(System.getenv("url"), System.getenv("user"), System.getenv("password"))) {
			stmt = conn.createStatement();
			stmt.execute(SQL);
			set = stmt.getResultSet();
			
			while(set.next()) {
				pendingUsers.add(new User(set.getString(1), set.getString(2), set.getInt(3)));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
				set.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//LOGGY.debug("The PolkamanRepository getAllpendingUsers was successfully called, and it returned: " + pendingUsers);
		return pendingUsers;
	}
	//---------------------------------------------Setting the DATA----------------------------------------------
//	@Override
//	public void UpdateAllData(HashSet<User> pendingUsers, HashSet<User> approvedUsers) {
//		DropOldTables();
//		DropOldTables2();
//		DropOldTables3();
//		createNewTables();
//		createNewTables2();
//		createNewTables3();
//		
//		//insert pending users
//		Iterator<User> i = pendingUsers.iterator(); 
//	    while (i.hasNext()) {
//	    	insertPendingUser(i.next());
//	    }
//	    
//	  //insert approved users and their accounts
//	    Iterator<User> j = approvedUsers.iterator(); 
//	    while (j.hasNext()) {
//	    	User currentUser = j.next();
//	    	insertApprovedUser(currentUser);
//	    	//add all his accounts
//		    Iterator<Account> a = currentUser.getAccountsThatAreAccsessable().iterator();
//		    while (j.hasNext()) {
//		    	insertAccount(currentUser, a.next());
//		    }
//	    }
//	}
	
	@Override
	public void DropOldTables() {
		//Connection conn = null;
		PreparedStatement ps = null;
		final String SQL = "drop table pendingUsers";
		
		try(Connection conn = DriverManager.getConnection(System.getenv("url"), System.getenv("user"), System.getenv("password"))) {
			ps = conn.prepareStatement(SQL);
			ps.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	public void DropOldTables2() {
		//Connection conn = null;
		PreparedStatement ps = null;
		final String SQL = "drop table ApprovedUsers";
		
		try(Connection conn = DriverManager.getConnection(System.getenv("url"), System.getenv("user"), System.getenv("password"))) {
			ps = conn.prepareStatement(SQL);
			ps.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void DropOldTables3() {
		//Connection conn = null;
		PreparedStatement ps = null;
		final String SQL = "drop table Accounts";
		
		try(Connection conn = DriverManager.getConnection(System.getenv("url"), System.getenv("user"), System.getenv("password"))) {
			ps = conn.prepareStatement(SQL);
			ps.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void createNewTables() {
		//Connection conn = null;
		PreparedStatement ps = null;
		final String SQL = "create table approvedusers(nameOfUser varchar primary key, PasswordOfuser varchar, userAccessLevel numeric)";
		
		try(Connection conn = DriverManager.getConnection(System.getenv("url"), System.getenv("user"), System.getenv("password"))) {
			ps = conn.prepareStatement(SQL);
			ps.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void createNewTables2() {
		//Connection conn = null;
		PreparedStatement ps = null;
	    final String SQL = "create table pendingusers(nameOfUser varchar primary key, PasswordOfuser varchar, userAccessLevel numeric)";
	   
	    try(Connection conn = DriverManager.getConnection(System.getenv("url"), System.getenv("user"), System.getenv("password"))) {
			ps = conn.prepareStatement(SQL);
			ps.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void createNewTables3() {
		//Connection conn = null;
		PreparedStatement ps = null;
		final String SQL = "create table Accounts(nameOfAccount varchar, amountInAccount numeric, isJoint boolean, ownerOfAccount varchar, hostJointUser varchar)";

		try(Connection conn = DriverManager.getConnection(System.getenv("url"), System.getenv("user"), System.getenv("password"))) {
			ps = conn.prepareStatement(SQL);
			ps.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void insertPendingUser(User userToAdd) {
		//Connection conn = null;
		PreparedStatement ps = null;
		final String SQL = "insert into pendingusers values(?, ?, ?)";
		
		try(Connection conn = DriverManager.getConnection(System.getenv("url"), System.getenv("user"), System.getenv("password"))) {
			ps = conn.prepareStatement(SQL);
			ps.setString(1, userToAdd.getName());
			ps.setString(2, userToAdd.getPassword());
			ps.setInt(3, userToAdd.getUserAccessLevel());
			ps.execute();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void insertApprovedUser(User userToAdd) {
		//Connection conn = null;
		PreparedStatement ps = null;
		final String SQL = "insert into approvedusers values(?, ?, ?)";
		
		try(Connection conn = DriverManager.getConnection(System.getenv("url"), System.getenv("user"), System.getenv("password"))) {
			ps = conn.prepareStatement(SQL);
			ps.setString(1, userToAdd.getName());
			ps.setString(2, userToAdd.getPassword());
			ps.setInt(3, userToAdd.getUserAccessLevel());
			ps.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void insertAccount(User userWhoOwnsAccount, Account accountToAdd) {
		//Connection conn = null;
		PreparedStatement ps = null;
		final String SQL = "insert into accounts values(?, ?, ?, ?, ?)";
	
		try(Connection conn = DriverManager.getConnection(System.getenv("url"), System.getenv("user"), System.getenv("password"))) {
			ps = conn.prepareStatement(SQL);
			ps.setString(1, accountToAdd.getName());
			ps.setInt(2, accountToAdd.getBalance());
			ps.setBoolean(3, accountToAdd.isJoint());
			ps.setString(4, userWhoOwnsAccount.getName());
			ps.setString(5, accountToAdd.getHostjointUserName());
			ps.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void UpdateAccount(User userWhoOwnsAccount, Account accountToUpdate) {
		//Connection conn = null;
		PreparedStatement ps = null;
		final String SQL = "UPDATE accounts SET amountInAccount = ?, isjoint = ?  Where nameofaccount ? AND ownerofaccount = ?";
	
		try(Connection conn = DriverManager.getConnection(System.getenv("url"), System.getenv("user"), System.getenv("password"))) {
			ps = conn.prepareStatement(SQL);
			ps.setInt(1, accountToUpdate.getBalance());
			ps.setBoolean(2, accountToUpdate.isJoint());
			ps.setString(3, accountToUpdate.getName());
			ps.setString(4, userWhoOwnsAccount.getName());
			ps.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void UpdateApprovedUser(User userToUpdate) {
		//Connection conn = null;
		PreparedStatement ps = null;
		final String SQL = "UPDATE approvedusers SET useraccesslevel = ? Where nameofuser = ? AND passwordofuser = ?";
		
		try(Connection conn = DriverManager.getConnection(System.getenv("url"), System.getenv("user"), System.getenv("password"))) {
			ps = conn.prepareStatement(SQL);
			ps.setInt(1, userToUpdate.getUserAccessLevel());
			ps.setString(2 , userToUpdate.getName());
			ps.setString(3, userToUpdate.getPassword());
			ps.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void updatePendingUser(User userToUpdate) {
		//Connection conn = null;
		PreparedStatement ps = null;
		final String SQL = "UPDATE PendingUsers SET useraccesslevel = ? Where nameofuser = ?";
		
		try(Connection conn = DriverManager.getConnection(System.getenv("url"), System.getenv("user"), System.getenv("password"))) {
			ps = conn.prepareStatement(SQL);
			ps.setInt(1, userToUpdate.getUserAccessLevel());
			ps.setString(2 , userToUpdate.getName());
			//ps.setString(3, userToUpdate.getPassword());
			ps.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void DeleteAccount(User userWhoOwnsAccount, Account accountToDelete) {
		//Connection conn = null;
		PreparedStatement ps = null;
		final String SQL = "DELETE FROM accounts Where nameofaccount = ? AND ownerofaccount = ?";
	
		try(Connection conn = DriverManager.getConnection(System.getenv("url"), System.getenv("user"), System.getenv("password"))) {
			ps = conn.prepareStatement(SQL);
			ps.setString(1, accountToDelete.getName());
			ps.setString(2, userWhoOwnsAccount.getName());
			ps.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void DeleteApprovedUser(User userToDelete) {
		//Connection conn = null;
		PreparedStatement ps = null;
		final String SQL = "DELETE FROM approvedusers Where nameofuser = ? AND passwordofuser = ?";
		
		try(Connection conn = DriverManager.getConnection(System.getenv("url"), System.getenv("user"), System.getenv("password"))) {
			ps = conn.prepareStatement(SQL);
			ps.setString(1 , userToDelete.getName());
			ps.setString(2, userToDelete.getPassword());
			ps.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void DeletePendingUser(User userToDelete) {
		//Connection conn = null;
		PreparedStatement ps = null;
		final String SQL = "DELETE FROM PendingUsers Where nameofuser = ? AND passwordofuser = ?";
		
		try(Connection conn = DriverManager.getConnection(System.getenv("url"), System.getenv("user"), System.getenv("password"))) {
			ps = conn.prepareStatement(SQL);
			ps.setString(1 , userToDelete.getName());
			ps.setString(2, userToDelete.getPassword());
			ps.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public Account readAccount(User userWhoOwnsAccount, Account accountToRead) {
		Account UserToReturn = null;
		ResultSet set = null;
		PreparedStatement ps = null;
		final String SQL = "SELECT * FROM accounts Where nameofaccount = ? AND ownerofaccount = ?";
	
		try(Connection conn = DriverManager.getConnection(System.getenv("url"), System.getenv("user"), System.getenv("password"))) {
			ps = conn.prepareStatement(SQL);
			ps.setString(1, accountToRead.getName());
			ps.setString(2, userWhoOwnsAccount.getName());
			ps.execute();
			
			set = ps.getResultSet();
			while(set.next()) {
				UserToReturn = new Account(set.getString(1), set.getInt(2), set.getBoolean(3));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return UserToReturn;
	}
	
	@Override
	public User readApprovedUser(User userToRead) {
		User UserToReturn = null;
		ResultSet set = null;
		PreparedStatement ps = null;
		final String SQL = "SELECT * FROM approvedusers Where nameofuser = ?";
		
		try(Connection conn = DriverManager.getConnection(System.getenv("url"), System.getenv("user"), System.getenv("password"))) {
			ps = conn.prepareStatement(SQL);
			ps.setString(1 , userToRead.getName());
			//ps.setString(2, userToRead.getPassword());
			ps.execute();
			
			set = ps.getResultSet();
			while(set.next()) {
				UserToReturn = new User(getAllApprovedUsersAccountsByUserName(set.getString(1)), set.getInt(3), set.getString(1), set.getString(2));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return UserToReturn;
	}
	
	@Override
	public User readPendingUser(User userToDelete) {
		User UserToReturn = null;
		ResultSet set = null;
		PreparedStatement ps = null;
		final String SQL = "SELECT * FROM PendingUsers Where nameofuser = ?";
		
		try(Connection conn = DriverManager.getConnection(System.getenv("url"), System.getenv("user"), System.getenv("password"))) {
			ps = conn.prepareStatement(SQL);
			ps.setString(1 , userToDelete.getName());
			//ps.setString(2, userToDelete.getPassword());
			ps.execute();
			
			set = ps.getResultSet();
			while(set.next()) {
				UserToReturn = new User(set.getString(1), set.getString(2), set.getInt(3));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return UserToReturn;
	}
}

