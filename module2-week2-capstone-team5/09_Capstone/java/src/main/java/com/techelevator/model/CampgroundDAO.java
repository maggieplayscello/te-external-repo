package com.techelevator.model;


import java.math.BigDecimal;
import java.util.List;

public interface CampgroundDAO {
	
	public List<Campground> getAllCampgrounds();
	//displays id, name, open and close months, and daily fee
	
	public List<Campground> getCampgroundByParkId(int parkId);
	//pulls correct CG from menu by id
	
	public boolean isParkClosed(int parkId, int campgroundId, int userArrivalMonthInt, int userDepartureMonthInt);
	//gives open date range and confirms park is open during specified dates - there are NO dates in sites so they have to refer to this
	
}
