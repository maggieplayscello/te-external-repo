package com.techelevator;

import java.math.BigDecimal;

public abstract class Items {
	private String slot;
	private String type;
	private String sound;
	private String name;
	private BigDecimal price;
	private int quantity;

	public Items(String slot, String type, String sound, String name, BigDecimal price, int quantity) {
		super();
		this.slot = slot;
		this.setType(type);
		this.setSound(sound);
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}

	public Items() {
		super();
	}

	public String getSlot() {
		return slot;
	}

	public void setSlot(String slot) {
		this.slot = slot;
	}

	public String getName() {
		if (quantity == 0) {
			return "*****SOLD OUT*****";
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSound() {
		return sound;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}
}
