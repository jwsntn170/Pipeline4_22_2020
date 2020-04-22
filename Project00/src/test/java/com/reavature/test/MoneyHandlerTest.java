package com.reavature.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.revature.model.Account;
import com.revature.moneyHandler.MoneyHandler;

@RunWith(JUnitPlatform.class)
@TestInstance(Lifecycle.PER_CLASS)
public class MoneyHandlerTest {

	static MoneyHandler c;
	static Account checkingsExample;
	static Account checkingsExampleForTransfer;
	
	@BeforeAll
	public void setUp() {
		c = new MoneyHandler();
		checkingsExample = new Account("CheckingsExmaple", 5);
		checkingsExampleForTransfer = new Account("checkingsExampleForTransfer", 5);
		
	}
	
	@BeforeEach
	public void reset() {
		//used to reset state or get a new instance of an object
		checkingsExample.setBalance(5);
		checkingsExampleForTransfer.setBalance(5);
	}
	
	@Test
	public void testDeposit() {
		Assertions.assertEquals(10, c.deposit(checkingsExample,  5));
	}
	@Test
	public void testDepositNegativeNumber() {
		Assertions.assertEquals(5, c.deposit(checkingsExample,  -5));
	}
	
	@Test
	public void testWithdraw() {
		Assertions.assertEquals(0, c.Withdraw(checkingsExample,  5));
	}
	
	@Test
	public void testWithdrawOverDraft() {
		Assertions.assertEquals(5, c.Withdraw(checkingsExample,  10));
	}
	
	@Test
	public void testTransferReciver() {
		c.transfer(checkingsExample, checkingsExampleForTransfer, 5);
		
		Assertions.assertEquals(10, checkingsExampleForTransfer.getBalance());
	}
	
	@Test
	public void testTransferSender() {
		c.transfer(checkingsExample, checkingsExampleForTransfer, 5);
		
		Assertions.assertEquals(0, checkingsExample.getBalance());
	}
	
	@Test
	public void testTransferSenderOverDraft() {
		c.transfer(checkingsExample, checkingsExampleForTransfer, 10);
		
		Assertions.assertEquals(5, checkingsExample.getBalance());
	}
}
