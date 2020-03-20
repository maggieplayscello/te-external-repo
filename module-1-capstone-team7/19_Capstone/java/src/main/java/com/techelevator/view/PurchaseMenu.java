package com.techelevator.view;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import com.techelevator.FileLog;
import com.techelevator.Items;
import com.techelevator.Wallet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PurchaseMenu {

	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_EXIT_TO_MAIN = "Cash Out and Exit";
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY,
			PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_EXIT_TO_MAIN };

	private Menu menu;
	private FileLog log = new FileLog();

	Wallet cashDeposited = new Wallet();

	// Time Stamp processes
	LocalDateTime dateTime = LocalDateTime.now();
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
	public String getTimestamp() {
		String timestamp = dateTime.format(formatter);
		return timestamp;
	}

	Scanner in = new Scanner(System.in);

	public PurchaseMenu(List<Items> inventoryDisplay) {

		while (true) {
			menu = new Menu(System.in, System.out);
			String purchaseChoice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

			if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
				String[] cashDepositOptions = { "1.00", "2.00", "5.00", "10.00" };
				String cashChoice = (String) menu.getChoiceFromOptions(cashDepositOptions);
				BigDecimal cashDepositAsBD = new BigDecimal(cashChoice);
				this.cashDeposited.deposit(cashDepositAsBD);
				log.writeToLogFile(
						getTimestamp() + "  FEED MONEY:  $" + cashDepositAsBD + " $" + this.cashDeposited.getBalance());

			} else if (purchaseChoice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
				System.out.println("\nCODE     ITEM             PRICE      QUANTITY");
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				for (Items item : inventoryDisplay) {
					System.out.printf("%-3s  %-20s  %-8s %5s \n", item.getSlot(), item.getName(), item.getPrice(),
							item.getQuantity());
				}
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("Please input product code: ");
				String slotSelection = in.nextLine().toUpperCase();
				for (Items item : inventoryDisplay) {
					if (item.getSlot().equals(slotSelection)) {

						if (item.getPrice().compareTo(cashDeposited.getBalance()) >= 0) {
							System.out.println("Insufficient Funds. Please deposit more money.");
							break;
						} else if (item.getQuantity() <= 0) {
							System.out.println("That item is sold out. Please select something different.");
							break;
						}

						System.out.println("Item selected: " + item.getName() + "; Price: $" + item.getPrice());
						System.out.println(item.getSound());
						this.cashDeposited.deplete(item.getPrice());
						item.setQuantity(item.getQuantity() - 1);

						System.out.println("Cash balance remaining: $" + this.cashDeposited.getBalance());
						log.writeToLogFile(getTimestamp() + "  " + item.getName() + "  " + item.getSlot() + "  $"
								+ this.cashDeposited.getBalance().add(item.getPrice()) + " $"
								+ this.cashDeposited.getBalance());
					} 
				}

			} else if (purchaseChoice.equals(PURCHASE_MENU_OPTION_EXIT_TO_MAIN)) {
				System.out.println(cashDeposited.getChange(cashDeposited));
				log.writeToLogFile(getTimestamp() + "  GIVE CHANGE: $" + cashDeposited.getBalance() + "  $0.00");
				System.out.println("Thank you and have a great day!");
				System.exit(0);
			}
		}
	}
}