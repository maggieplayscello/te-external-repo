package com.techelevator.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ReservationDAO {
	
//	public List<Reservation> searchAllReservations(LocalDate searchFromDateLD, LocalDate searchToDateLD);
	
	public void makeReservation(int userSelectedCampgroundId, int userSelectedSiteId, String userReservationName, LocalDate userSelectedArrivalDate, LocalDate userSelectedDepartureDate);
	// will require name and dates from user input

	public boolean reservationConfirmed(String userReservationName, LocalDate userSelectedArrivalDate, LocalDate userSelectedDepartureDate);
	//returns true if reservation was made successfully

	List<Reservation> getReservationsById(int reservationId);
	
	public BigDecimal totalReservationCost(LocalDate userSelectedArrivalDate, LocalDate userSelectedDepartureDate, int userSelectedCampgroundId);

}
