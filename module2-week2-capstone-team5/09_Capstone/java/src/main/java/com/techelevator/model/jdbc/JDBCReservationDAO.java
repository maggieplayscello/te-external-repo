package com.techelevator.model.jdbc;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Campground;
import com.techelevator.model.Reservation;
import com.techelevator.model.ReservationDAO;

public class JDBCReservationDAO implements ReservationDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	} 
	
	private Reservation mapRowToReservations(SqlRowSet results) {
		Reservation reservation = new Reservation();
		reservation.setReservationId(results.getInt("reservation_id"));
		reservation.setSiteId(results.getInt("site_id"));
		reservation.setReservationName(results.getString("name"));
		reservation.setFromDate(results.getDate("from_date").toLocalDate());
		reservation.setToDate(results.getDate("to_date").toLocalDate());
		reservation.setCreateDate(results.getDate("create_date").toLocalDate());
		return reservation;
	}
	
//	@Override
//	public List<Reservation> searchAllReservations(LocalDate searchFromDateLD, LocalDate searchToDateLD) {
//			List<Reservation> allReservations = new ArrayList<>();
//			String sqlGetAllReservations = "SELECT * FROM reservation WHERE from_date = ? AND to_date = ?";
//			SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllReservations, searchFromDateLD, searchToDateLD);
//			while (results.next()) {
//				allReservations.add(mapRowToReservations(results));
//			}
//			for (Reservation reservation : allReservations) {
//				System.out.println(reservation.toString());
//				System.out.println("Reservations");
//				if(allReservations.size() < 1) {
//					System.out.println("There are no reservations during that time.");
//				}
//		}
//			return allReservations;
//	}
	
	@Override
	public List<Reservation> getReservationsById(int reservationId) {
		List<Reservation> reservationsById = new ArrayList<>();
		String sqlGetReservationsById = "SELECT * FROM campground WHERE reservation_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetReservationsById, reservationId);
		while (results.next()) {
			reservationsById.add(mapRowToReservations(results));
		}
		return reservationsById;
	}

	@Override
	public BigDecimal totalReservationCost(LocalDate userSelectedArrivalDate, LocalDate userSelectedDepartureDate, int userSelectedCampgroundId) {
		BigDecimal totalCost = new BigDecimal ("0.00");
		BigDecimal dailyFee = null;
		String sqlGetDailyFeeByCampgroundId = "SELECT campground_id, daily_fee FROM campground WHERE campground_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetDailyFeeByCampgroundId, userSelectedCampgroundId);
		while (results.next()) {
			dailyFee = results.getBigDecimal("daily_fee");
			}						
		long bookedDays = ChronoUnit.DAYS.between(userSelectedArrivalDate, userSelectedDepartureDate);
		totalCost = BigDecimal.valueOf(bookedDays).multiply(dailyFee);
		System.out.println("\nFor " + bookedDays + " day(s) at this park with a daily fee of " + NumberFormat.getCurrencyInstance().format(dailyFee) 
				+ ", the total is: " + NumberFormat.getCurrencyInstance().format(totalCost) + ".");
		return totalCost;
	}

	@Override
	public void makeReservation(int userSelectedCampgroundId, int userSelectedSiteNo, String userReservationName, LocalDate userSelectedArrivalDate, 
			LocalDate userSelectedDepartureDate) {
		LocalDate createDate = LocalDate.now();
		int siteId = 0;
		String sqlFindSiteId = "SELECT site_id FROM site WHERE campground_id = ? AND site_number = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindSiteId, userSelectedCampgroundId, userSelectedSiteNo);
		while (results.next()) {
			siteId = results.getInt("site_id");
		}
		String sqlMakeReservation = "INSERT INTO reservation (reservation_id, site_id, name, from_date, to_date, create_date) " +
				"VALUES (DEFAULT, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sqlMakeReservation, siteId, userReservationName, userSelectedArrivalDate, userSelectedDepartureDate, createDate);
		System.out.println("Reservation for Site " + userSelectedSiteNo + " for " + userReservationName + " arriving on " + 
				userSelectedArrivalDate + " and departing " + userSelectedDepartureDate + ". Reservation created " + createDate + ".");
	}

	@Override
	public boolean reservationConfirmed(String userReservationName, LocalDate userSelectedArrivalDate, LocalDate userSelectedDepartureDate) {
		int reservationId = 0;
		String sqlConfirmReservation = "SELECT reservation_id FROM reservation WHERE name = ? AND from_date = ? AND to_date = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlConfirmReservation, userReservationName, userSelectedArrivalDate, userSelectedDepartureDate);
		while (results.next()) {
			reservationId = results.getInt("reservation_id");
		} if (reservationId > 0) {
			System.out.println("Your reservation is confirmed! Your confirmation number is " + reservationId + ".");
			return true;
		}
		return false;
	}
	
}
