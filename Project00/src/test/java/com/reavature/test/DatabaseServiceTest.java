package com.reavature.test;

import java.util.HashSet;
import java.util.Iterator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.revature.model.*;
import com.revature.repository.BankingRepositoryImpl;
//import com.revature.service.PolkamanService;


@RunWith(JUnitPlatform.class)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class DatabaseServiceTest {

	@Mock
	BankingRepositoryImpl bankingRepository;
	
	BankingRepositoryImpl bankingRepositoryRealDeal;
	//@InjectMocks //Inject your mocks
	//PolkamanService polkamanService;
	
	public User userForTest = new User("TheTestDogNoOneWillNameThereAccountThisBecauseItsSoLong", "password", 0);
	public Account accountForTest = new Account("ACCOUNTFORTEST", 10);
	
	@BeforeAll
	public void setUp() {
		//polkamanService = new PolkamanService();
		bankingRepository = new BankingRepositoryImpl(); 
		MockitoAnnotations.initMocks(this);
		
		bankingRepositoryRealDeal = new BankingRepositoryImpl(); 
		
		userForTest.addAnAccount(accountForTest);
	}
	
	@Test
	@Order(1)  
	public void testGetAllApprovedUsers() {
		HashSet<User> mockUsers = new HashSet<User>();
		mockUsers.add(new User("Jerry", "password", 0));
		mockUsers.add(new User("Johnny", "password", 1));
		
		Mockito.when(bankingRepository.getAllApprovedUsers()).thenReturn((HashSet<User>) mockUsers);
		
		Iterator<User> j = bankingRepository.getAllApprovedUsers().iterator(); 
	    while (j.hasNext()) {
	    	User accountToCheck = j.next();
	    	if (accountToCheck.getName().equals("Jerry")) {
	    		Assertions.assertEquals("Jerry", accountToCheck.getName());
	    	}
	    }
	}

	@Test
	@Order(2)  
	public void testGetAllPendingUsers() {
		HashSet<User> mockUsers = new HashSet<User>();
		mockUsers.add(new User("Jerry", "password", 0));
		mockUsers.add(new User("Johnny", "password", 1));
		
		Mockito.when(bankingRepository.getAllPendingUsers()).thenReturn((HashSet<User>) mockUsers);
		
		Iterator<User> j = bankingRepository.getAllPendingUsers().iterator(); 
	    while (j.hasNext()) {
	    	User accountToCheck = j.next();
	    	if (accountToCheck.getName().equals("Jerry")) {
	    		Assertions.assertEquals("Jerry", accountToCheck.getName());
	    	}
	    }
	}
	
	//we are going to test insert, read, and delete for each group below
	@Test
	@Order(3)  
	public void testinsertPendingUser() {
		bankingRepositoryRealDeal.insertPendingUser(userForTest);
	    Assertions.assertEquals(userForTest.getName(), bankingRepositoryRealDeal.readPendingUser(userForTest).getName());
	}
	
	@Test
	@Order(4)  
	public void testReadPendingUser() {
	    Assertions.assertEquals(userForTest.getName(), bankingRepositoryRealDeal.readPendingUser(userForTest).getName());
	}
	
	@Test
	@Order(5)  
	public void testUpdatePendingUser() {
		userForTest.setUserAccessLevel(1);
		bankingRepositoryRealDeal.updatePendingUser(userForTest);
		Assertions.assertEquals(1, bankingRepositoryRealDeal.readPendingUser(userForTest).getUserAccessLevel());
		userForTest.setUserAccessLevel(0);
	}
	
	@Test
	@Order(6)  
	public void testDeletePendingUser() {
		bankingRepositoryRealDeal.DeletePendingUser(userForTest);
	    Assertions.assertEquals(null, bankingRepositoryRealDeal.readPendingUser(userForTest));
	}
	
	@Test
	@Order(7)  
	public void testinsertApprovedUser() {
		bankingRepositoryRealDeal.insertApprovedUser(userForTest);
	    Assertions.assertEquals(userForTest.getName(), bankingRepositoryRealDeal.readApprovedUser(userForTest).getName());
	}
	
	@Test
	@Order(8)  
	public void testReadApprovedUser() {
	    Assertions.assertEquals(userForTest.getName(), bankingRepositoryRealDeal.readApprovedUser(userForTest).getName());
	}
	
	@Test
	@Order(9)  
	public void testDeleteApprovedUser() {
		bankingRepositoryRealDeal.DeleteApprovedUser(userForTest);
	    Assertions.assertEquals(null, bankingRepositoryRealDeal.readApprovedUser(userForTest));
	}
	
	@Test
	@Order(10)  
	public void testinsertAccount() {
		bankingRepositoryRealDeal.insertAccount(userForTest, accountForTest);
	    Assertions.assertEquals(accountForTest.getName(), bankingRepositoryRealDeal.readAccount(userForTest, userForTest.checkForAccount("ACCOUNTFORTEST")).getName());
	}
	
	@Test
	@Order(11)  
	public void testReadAccount() {
	    Assertions.assertEquals(accountForTest.getName(), bankingRepositoryRealDeal.readAccount(userForTest, userForTest.checkForAccount("ACCOUNTFORTEST")).getName());
	}
	
	@Test
	@Order(12)  
	public void testDeleteAccount() {
		bankingRepositoryRealDeal.DeleteAccount(userForTest, userForTest.checkForAccount("ACCOUNTFORTEST"));
	    Assertions.assertEquals(null, bankingRepositoryRealDeal.readAccount(userForTest, userForTest.checkForAccount("ACCOUNTFORTEST")));
	}
}
