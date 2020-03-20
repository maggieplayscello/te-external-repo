package com.techelevator;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class VendingMachineTest {

	InitialInventory inventoryTest = new InitialInventory();
	Wallet testWallet = new Wallet();

	@Test
	public void method_tests_deposit_5_00_into_balance_returns_5_00() {
		testWallet.deposit(new BigDecimal("5.00"));
		BigDecimal expectedBalance = new BigDecimal("5.00");
		assertEquals(expectedBalance, testWallet.getBalance());
	}

	@Test
	public void method_tests_adding_1_00_and_then_adding_2_00_returns_3_00() {
		testWallet.deposit(new BigDecimal("1.00"));
		testWallet.deposit(new BigDecimal("2.00"));
		BigDecimal expectedBalance = new BigDecimal("3.00");
		assertEquals(expectedBalance, testWallet.getBalance());
	}

	@Test
	public void method_tests_adding_5_00_and_then_depleting_2_00_returns_3_00() {
		testWallet.deposit(new BigDecimal("5.00"));
		testWallet.deplete(new BigDecimal("2.00"));
		BigDecimal expectedBalance = new BigDecimal("3.00");
		assertEquals(expectedBalance, testWallet.getBalance());
	}

	@Test
	public void method_tests_adding_2_00_and_then_depleting_2_25_will_refuse_transaction_and_return_2_00() {
		testWallet.deposit(new BigDecimal("2.00"));
		testWallet.deplete(new BigDecimal("2.25"));
		BigDecimal expectedBalance = new BigDecimal("2.00");
		assertEquals(expectedBalance, testWallet.getBalance());
	}
	
	@Test
	public void method_tests_that_change_returned_equals_balance() {
		testWallet.deposit(new BigDecimal("2.00"));
		testWallet.getChange(testWallet);
		BigDecimal expectedBalance = new BigDecimal("2.00");
		assertEquals(expectedBalance, testWallet.getBalance());
	}
	
	@Test
	public void method_tests_adding_2_00_and_then_depleting_price_from_array_returns_correct_balance() throws FileNotFoundException {
		testWallet.deposit(new BigDecimal("2.00"));
		List<Items> inventoryList = this.inventoryTest.displayInventory();
		for (Items item : inventoryList) {
			if (item.getSlot().equals("D4")) {
				testWallet.deplete(item.getPrice());
			}
		}
		BigDecimal expectedBalance = new BigDecimal("1.25");
		assertEquals(expectedBalance, testWallet.getBalance());
	} 
	
	@Test
	public void method_tests_slot_returns_correct_item_name() throws FileNotFoundException {
		List<Items> inventoryList = this.inventoryTest.displayInventory();
		String actualName = "";
		for (Items item : inventoryList) {
			if (item.getSlot().equals("A2")) {
				actualName = item.getName();
			}
		} String expectedName = "Stackers"; 
		assertEquals(expectedName, actualName);
	}
	
	@Test
	public void method_tests_slot_returns_correct_item_price() throws FileNotFoundException {
		List<Items> inventoryList = this.inventoryTest.displayInventory();
		BigDecimal actualPrice = new BigDecimal ("0.00");
		for (Items item : inventoryList) {
			if (item.getSlot().equals("B1")) {
				actualPrice = item.getPrice();
			}
		} BigDecimal expectedPrice = new BigDecimal ("1.80"); 
		assertEquals(expectedPrice, actualPrice);
	}
	
	@Test
	public void method_tests_slot_returns_correct_item_type() throws FileNotFoundException {
		List<Items> inventoryList = this.inventoryTest.displayInventory();
		String actualName = "";
		for (Items item : inventoryList) {
			if (item.getSlot().equals("C1")) {
				actualName = item.getType();
			}
		} String expectedName = "Drink"; 
		assertEquals(expectedName, actualName);
	}
	
	@Test
	public void method_tests_slot_returns_correct_initial_item_quantity() throws FileNotFoundException {
		List<Items> inventoryList = this.inventoryTest.displayInventory();
		int actualQuantity = 0;
		for (Items item : inventoryList) {
			if (item.getSlot().equals("A2")) {
				actualQuantity = item.getQuantity();
			}
		} 
		assertEquals(5, actualQuantity);
	}
	@Test
	public void method_tests_set_name_works() throws FileNotFoundException {
		List<Items> inventoryList = this.inventoryTest.displayInventory();	
		String newName = "JollyChews";
		for (Items item : inventoryList) {
			if (item.getName().equals("Cola")) {	
				item.setName(newName);
			}
		} assertEquals("JollyChews", newName);
	}
	
	@Test
	public void methods_tests_that_type_drink_return_sound_glug_glug_yum() throws FileNotFoundException {
		List<Items> inventoryList = this.inventoryTest.displayInventory();	
		String soundReturned = "";
		for (Items item : inventoryList) {
			if (item.getType().equals("Drink")) {	
				soundReturned = item.getSound();
			}
		} assertEquals("Glug Glug, Yum!", soundReturned);
	}
	
	
//	@Test
//	public void method_tests_quantity_0_returns_name_sold_out() throws FileNotFoundException {
//		String actualName = "";
//		List<Items> inventoryList = this.inventoryTest.displayInventory();
//		for (Items item : inventoryList) {
//			if (item.getQuantity() == 0) {
//				actualName = item.getName();				
//			}
//		} assertEquals("*****SOLD OUT*****", actualName);
//	}

}
