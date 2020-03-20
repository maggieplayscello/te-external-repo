package com.techelevator.npgeek.ParkDAO;

import java.util.List;

public interface ParkDAO {
	
	//CRUD
	
	public List<Park> getAllParks();
	
	public Park getParkByParkCode(String parkCode);
	
	public void saveTestPark();
	
	public void deleteTestPark();
	
}
