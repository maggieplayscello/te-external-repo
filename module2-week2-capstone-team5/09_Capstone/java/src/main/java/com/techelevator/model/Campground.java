package com.techelevator.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

public class Campground {
	private int campgroundId;
	private int parkId;
	private String campgroundName;
	private int openFromMM;
	private int openToMM;
	private BigDecimal dailyFee;
	
	
	public int getCampgroundId() {
		return campgroundId;
	}
	public void setCampgroundId(int campgroundId) {
		this.campgroundId = campgroundId;
	}
	public int getParkId() {
		return parkId;
	}
	public void setParkId(int parkId) {
		this.parkId = parkId;
	}
	public String getName() {
		return campgroundName;
	}
	public void setName(String name) {
		this.campgroundName = name;
	}
	public int getOpenFromMM() {
		return openFromMM;
	}
	public void setOpenFromMM(int openFromMM) {
		this.openFromMM = openFromMM;
	}
	public int getOpenToMM() {
		return openToMM;
	}

	public void setOpenToMM(int openToMM) {
		this.openToMM = openToMM;
	}
	public BigDecimal getDailyFee() {
		return dailyFee;
	}
	public void setDailyFee(BigDecimal dailyFee) {
		this.dailyFee = dailyFee;
	}
	
	@Override
	public String toString() {
		return String.format("%7d         %-25s %-16s %-16s $%-12.2f", campgroundId, campgroundName, Month.of(openFromMM).name(), Month.of(openToMM).name(), dailyFee);
	}

}
