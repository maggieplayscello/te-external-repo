package com.techelevator.model;

import java.time.LocalDate;
import java.util.List;

public interface SiteDAO {
	
	public List<Site> getAllSites();
	//may not truly need this one but it won't hurt to have it
	
	List<Site> checkSiteAvailability(int userSelectedCampgroundId, LocalDate userSelectedArrivalDate,
			LocalDate userSelectedDepartureDate);

}
