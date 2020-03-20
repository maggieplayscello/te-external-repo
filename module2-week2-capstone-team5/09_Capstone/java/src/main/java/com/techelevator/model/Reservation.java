package com.techelevator.model;

import java.time.LocalDate;
import java.time.Month;

public class Reservation {
	private int reservationId;
	private int siteId;
	private String reservationName;
	private LocalDate fromDate;
	private LocalDate toDate;
	private LocalDate createDate;
	
	public int getReservationId() {
		return reservationId;
	}
	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}
	public int getSiteId() {
		return siteId;
	}
	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
	public String getReservationName() {
		return reservationName;
	}
	public void setReservationName(String reservationName) {
		this.reservationName = reservationName;
	}
	public LocalDate getFromDate() {
		return fromDate;
	}
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	public LocalDate getToDate() {
		return toDate;
	}
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	public LocalDate getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}
	
	@Override
	public String toString() {
		return String.format("%7d %-25d %-16s %-16s $%-12s", reservationId, siteId, reservationName, fromDate, toDate);
	}

}
