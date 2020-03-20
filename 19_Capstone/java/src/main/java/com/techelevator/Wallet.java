package com.techelevator;

import java.math.BigDecimal;

public class Wallet {

	private BigDecimal balance;

	public BigDecimal getBalance() {
		return balance;
	}

	public Wallet() {
		this.balance = new BigDecimal(0.00);
	}

	public BigDecimal deposit(BigDecimal cashDeposit) {
		System.out.println("Amount deposited: $" + cashDeposit);
		this.balance = balance.add(cashDeposit);
		System.out.println("Current balance: $" + balance);
		return balance;
	}

	public BigDecimal deplete(BigDecimal costOfProduct) {
		if (balance.compareTo(costOfProduct) >= 0) {
			balance = balance.subtract(costOfProduct);
			return balance;

		} else {
			System.out.println("Insufficient Funds. Add more money.");
		}
		return balance;
	}

	public String getChange(Wallet cashDeposited) {
		BigDecimal quarterValue = new BigDecimal("0.25");
		BigDecimal dimeValue = new BigDecimal("0.10");
		BigDecimal nickelValue = new BigDecimal("0.05");
		BigDecimal pennyValue = new BigDecimal("0.01");

		BigDecimal quartersInChange = this.balance.divideToIntegralValue(quarterValue);
		BigDecimal quarterLeftovers = this.balance.remainder(quarterValue);
		BigDecimal dimesInChange = quarterLeftovers.divideToIntegralValue(dimeValue);
		BigDecimal dimeLeftovers = quarterLeftovers.remainder(dimeValue);
		BigDecimal nickelsInChange = dimeLeftovers.divideToIntegralValue(nickelValue);
		BigDecimal nickelLeftovers = dimeLeftovers.remainder(nickelValue);
		BigDecimal penniesInChange = nickelLeftovers.divideToIntegralValue(pennyValue);

		System.out.println("\nHere is your change: " + quartersInChange + " Quarters, " + dimesInChange + " Dimes, "
				+ nickelsInChange + " Nickels, and " + penniesInChange + " Pennies in change.");
		return ("Total returned: $" + balance);
	}
}
