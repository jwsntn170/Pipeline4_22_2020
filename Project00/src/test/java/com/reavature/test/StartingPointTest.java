package com.reavature.test;

import java.util.HashSet;
import java.util.Iterator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.revature.driver.StartingPoint;
import com.revature.model.User;

@RunWith(JUnitPlatform.class)
@TestInstance(Lifecycle.PER_CLASS)
public class StartingPointTest {

	//static StartingPoint startingPoint;

		
	@BeforeAll
	public void setUp() {
		StartingPoint.setUpForTest();
		
	}
		
	@Test
	public void checkIfUserNameExistTestUserNotThere(){
		Assertions.assertEquals(null, StartingPoint.checkIfUserNameExist("Jerry"));
	}
	
	@Test
	public void checkIfUserNameExistTestUserThere(){
		User doug = null;
		HashSet<User> approvedUsers = StartingPoint.getApprovedUsers();
		Iterator<User> j = approvedUsers.iterator(); 
		    while (j.hasNext()) {
		    	User currentUser = j.next();
		    	if (currentUser.getName().equals("Doug")) {
		    		//System.out.println("Your account has been found.");
		    		doug = currentUser;
		    	}
		    }
		if(doug != null) {
			Assertions.assertEquals(doug, StartingPoint.checkIfUserNameExist("Doug"));
		}
	}
	
	@Test
	public void checkIfUserNameExistTestUserTherePasswordCorrect(){
		User doug = null;
		HashSet<User> approvedUsers = StartingPoint.getApprovedUsers();
		Iterator<User> j = approvedUsers.iterator(); 
		    while (j.hasNext()) {
		    	User currentUser = j.next();
		    	if (currentUser.getName().equals("Doug")) {
		    		//System.out.println("Your account has been found.");
		    		doug = currentUser;
		    	}
		    }
		if(doug != null) {
			Assertions.assertEquals(doug, StartingPoint.checkIfUserNameExistwithPassword("Doug", "password"));
		}
	}
	
	@Test
	public void checkIfUserNameExistTestUserTherePasswordNotCorrect(){
		User doug = null;
		HashSet<User> approvedUsers = StartingPoint.getApprovedUsers();
		Iterator<User> j = approvedUsers.iterator(); 
		    while (j.hasNext()) {
		    	User currentUser = j.next();
		    	if (currentUser.getName().equals("Doug")) {
		    		//System.out.println("Your account has been found.");
		    		doug = currentUser;
		    	}
		    }
		if(doug != null) {
			Assertions.assertEquals(null, StartingPoint.checkIfUserNameExistwithPassword("Doug", "passwordIsNotThis"));
		}
	}
	
	@Test
	public void checkIfAccountNameExistTestUserThere(){
		User doug = null;
		HashSet<User> approvedUsers = StartingPoint.getApprovedUsers();
		Iterator<User> j = approvedUsers.iterator(); 
		    while (j.hasNext()) {
		    	User currentUser = j.next();
		    	if (currentUser.getName().equals("Doug")) {
		    		//System.out.println("Your account has been found.");
		    		doug = currentUser;
		    	}
		    }
		if(doug != null) {
			Assertions.assertEquals(true, StartingPoint.checkIfAccountNameExist("checking", doug));
		}
	}
	
	@Test
	public void checkIfAccountNameExistTestUserNotThere(){
		User doug = null;
		HashSet<User> approvedUsers = StartingPoint.getApprovedUsers();
		Iterator<User> j = approvedUsers.iterator(); 
		    while (j.hasNext()) {
		    	User currentUser = j.next();
		    	if (currentUser.getName().equals("Doug")) {
		    		//System.out.println("Your account has been found.");
		    		doug = currentUser;
		    	}
		    }
		if(doug != null) {
			Assertions.assertEquals(false, StartingPoint.checkIfAccountNameExist("Cats are better then dogs", doug));
		}
	}

//	@Test
//	public void asksForNewIntegerWhenOutsideBoundsOfOneToTen() throws Exception {
//		MyScannerHandler asker = mock(MyScannerHandler.class);
//	    when(asker..thenReturn(99);
//	    when(asker..thenReturn(3);
//
//	    getBoundIntegerFromUser(asker);
//
//	    verify(asker).ask("Wrong number, try again.");
//	}
}
