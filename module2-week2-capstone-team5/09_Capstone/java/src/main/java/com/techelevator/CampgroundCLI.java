package com.techelevator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.model.Campground;
import com.techelevator.model.CampgroundDAO;
import com.techelevator.model.Park;
import com.techelevator.model.ParkDAO;
import com.techelevator.model.ReservationDAO;
import com.techelevator.model.SiteDAO;
import com.techelevator.model.jdbc.JDBCCampgroundDAO;
import com.techelevator.model.jdbc.JDBCParkDAO;
import com.techelevator.model.jdbc.JDBCReservationDAO;
import com.techelevator.model.jdbc.JDBCSiteDAO;
import com.techelevator.view.Menu;

public class CampgroundCLI {

	private Menu menu;
	private ParkDAO parkDAO;
	private CampgroundDAO campgroundDAO;
	private ReservationDAO reservationDAO;
	private SiteDAO siteDAO;

	final String VIEW_CAMPGROUNDS = "View Campgrounds";
	final String SEARCH_FOR_RESERVATION = "Search for Reservation";
	final String RETURN_TO_PREVIOUS_SCREEN = "Return to Previous Screen";
	final String[] campgroundMenu = new String[] { VIEW_CAMPGROUNDS, SEARCH_FOR_RESERVATION,
			RETURN_TO_PREVIOUS_SCREEN };

	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
	}

	public CampgroundCLI(DataSource dataSource) {
		this.menu = new Menu(System.in, System.out);

		parkDAO = new JDBCParkDAO(dataSource);
		campgroundDAO = new JDBCCampgroundDAO(dataSource);
		reservationDAO = new JDBCReservationDAO(dataSource);
		siteDAO = new JDBCSiteDAO(dataSource);
	}

	@SuppressWarnings("resource")
	private String getUserInput(String prompt) {
		System.out.print(prompt + " >>> ");
		return new Scanner(System.in).nextLine();
	}

	Park selectedPark = new Park();
	Campground selectedCampground = new Campground();

	public void run() {
		System.out.println("Welcome to the National Parks Reservation System!");

		List<Park> parkList = parkDAO.getAllParks();
		String[] parkNames = new String[parkList.size() + 1];
		int i;
		for (i = 0; i < parkList.size(); i++) {
			parkNames[i] = parkList.get(i).getParkName();
		}
		parkNames[i] = "Quit";
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(parkNames);
			System.out.println(choice);
			if (choice.equals("Quit")) {
				System.out.println("Exiting System");
				System.exit(0);
			}
			for (Park park : parkList) {
				if (choice.equals(park.getParkName())) {
					parkDAO.toString(park);
					campgroundSelection(park);
					selectedPark = park;
					break;
				}
			}
		}
	}

	public void campgroundSelection(Park park) {
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(campgroundMenu);
			if (choice.equals(VIEW_CAMPGROUNDS)) {
				handleAllCampgroundsById(park);
			}
			String userSelectedCampground = getUserInput("\nWhich campground would you like to reserve? (Or enter 0 to cancel) ");			
			if (userSelectedCampground.equals("0")) {
				break;
			}			
			int userSelectedCampgroundId = Integer.parseInt(userSelectedCampground);
			int userSelectedParkId = park.getParkId();
			String userSelectedArrival = getUserInput("What is your arrival date? Please use the format YYYY-MM-DD");
			String userSelectedDeparture = getUserInput("What is your departure date? Please use the format YYYY-MM-DD");
			String userArrivalMonth = userSelectedArrival.substring(5, 7);
			String userDepartureMonth = userSelectedDeparture.substring(5, 7);
			int userArrivalMonthInt = Integer.parseInt(userArrivalMonth);
			int userDepartureMonthInt = Integer.parseInt(userDepartureMonth);
			LocalDate userSelectedArrivalDate = LocalDate.parse(userSelectedArrival);
			LocalDate userSelectedDepartureDate = LocalDate.parse(userSelectedDeparture);
			if (campgroundDAO.isParkClosed(userSelectedParkId, userSelectedCampgroundId, userArrivalMonthInt,
					userDepartureMonthInt)) {
				break;
			} else				
			siteDAO.checkSiteAvailability(userSelectedCampgroundId, userSelectedArrivalDate, userSelectedDepartureDate);
			BigDecimal totalCost = reservationDAO.totalReservationCost(userSelectedArrivalDate,
					userSelectedDepartureDate, userSelectedCampgroundId);
			String userSelectedSite = getUserInput("\nWhich site would should be reserved (enter 0 to cancel)? ");			
			if (userSelectedSite.equals("0")) {
				break;
			}			
			int userSelectedSiteNo = Integer.parseInt(userSelectedSite);
			String userReservationName = getUserInput("\nUnder which name should the reservation be made?");
			reservationDAO.makeReservation(userSelectedCampgroundId, userSelectedSiteNo, userReservationName,
					userSelectedArrivalDate, userSelectedDepartureDate);
			reservationDAO.reservationConfirmed(userReservationName, userSelectedArrivalDate,
					userSelectedDepartureDate);
		}
//			if (choice.contentEquals(SEARCH_FOR_RESERVATION) ) {
//				handleSearchForReservation(park);
//			}
	}

	private void handleAllCampgroundsById(Park park) {
		List<Campground> campgroundsById = campgroundDAO.getCampgroundByParkId(park.getParkId());
		System.out.println("\nWelcome to " + park.getParkName() + " National Park!");
		System.out.println("Available Campgrounds: \n");
		System.out.printf("\n %-14s %-25s %-16s %-16s %-12s", "Campground Id", "Campground", "Opening Month",
				"Closing Month", "Daily Fee");
		System.out.printf("\n %-14s %-25s %-16s %-16s %-12s \n", "_____________", "__________", "_____________",
				"_____________", "_________");
		for (Campground cg : campgroundsById) {
			System.out.println(cg.toString());
		}
	}
	
//	public void handleSearchForReservation(Park park) {
//		String searchFromDate = getUserInput("What is the arrival date? ");
//		String searchToDate = getUserInput("What is the departure date? "); 
//		LocalDate searchFromDateLD = LocalDate.parse(searchFromDate);
//		LocalDate searchToDateLD = LocalDate.parse(searchToDate);
//		reservationDAO.searchAllReservations(searchFromDateLD, searchToDateLD);
//		
//		
//	}
}
