package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InitialInventory {

	public List<Items> displayInventory() throws FileNotFoundException {
		List<Items> itemList = new ArrayList<>();
		File file = new File("vendingmachine.csv");
		Scanner fileInput = new Scanner(file);

		while (fileInput.hasNextLine()) {
			String line = fileInput.nextLine();
			String[] inputInfo = line.split("\\|");

			BigDecimal priceBD = new BigDecimal(inputInfo[2]);
			switch (inputInfo[3]) {
			case "Candy":
				itemList.add(new Candy(inputInfo[0], inputInfo[1], priceBD));
				break;
			case "Chip":
				itemList.add(new Chip(inputInfo[0], inputInfo[1], priceBD));
				break;
			case "Gum":
				itemList.add(new Gum(inputInfo[0], inputInfo[1], priceBD));
				break;
			case "Drink":
				itemList.add(new Drink(inputInfo[0], inputInfo[1], priceBD));
				break;
			}
		}
		fileInput.close();
		return itemList;
	}
}
