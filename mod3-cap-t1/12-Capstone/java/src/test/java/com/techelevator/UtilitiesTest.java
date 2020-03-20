package com.techelevator;

import static org.junit.Assert.*;

import org.junit.Test;

import com.techelevator.npgeek.UtilityClasses.Utility;

public class UtilitiesTest {

	@Test
	public void ctof_returns_56_for_134_f() {
		int expected= 56;
		int actual= Utility.fToC(134);
		assertEquals(expected,actual);
	}
	
	@Test
	public void ctof_returns_neg89_for_neg129_f() {
		int expected= -89;
		int actual= Utility.fToC(-129);
		assertEquals(expected,actual);
	}
	
	@Test
	public void ctof_returns_20_for_68_f() {
		int expected= 20;
		int actual= Utility.fToC(68);
		assertEquals(expected,actual);
	}
	

}
