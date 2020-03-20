package com.techelevator.model;

import java.util.List;

public interface ParkDAO {

	public List<Park> getAllParks();
	//displays only the name of each park though it will pull all info needed for below

	public void toString(Park park);
	
}
