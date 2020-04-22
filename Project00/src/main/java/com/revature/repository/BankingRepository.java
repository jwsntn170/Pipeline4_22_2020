package com.revature.repository;


import java.util.HashSet;

//import com.revature.driver.*;
import com.revature.model.Account;
import com.revature.model.User;


public interface BankingRepository {
	HashSet<User> getAllApprovedUsers();


	HashSet<Account> getAllApprovedUsersAccountsByUserName(String userName);
	HashSet<User> getAllPendingUsers();

	void insertPendingUser(User userToAdd);
	void insertApprovedUser(User userToAdd);
	void insertAccount(User userWhoOwnsAccount, Account accountToAdd);

	void DropOldTables();
	void createNewTables();


	void updatePendingUser(User userToUpdate);
	void UpdateApprovedUser(User userToUpdate);
	void UpdateAccount(User userWhoOwnsAccount, Account accountToUpdate);


	void DeletePendingUser(User userToUpdate);
	void DeleteApprovedUser(User userToUpdate);
	void DeleteAccount(User userWhoOwnsAccount, Account accountToUpdate);


	User readPendingUser(User userToUpdate);
	User readApprovedUser(User userToUpdate);
	Account readAccount(User userWhoOwnsAccount, Account accountToUpdate);

	//void UpdateAllData(HashSet<User> pendingUsers, HashSet<User> approvedUsers);
}
