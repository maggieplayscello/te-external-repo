package com.techelevator.npgeek.SurveyDAO;

import java.util.List;
import java.util.Map;

public interface SurveyDAO {

	//CRUD
	
	public boolean save(Survey survey);
	
	public List<String> getTopParks();
	
	public Map<String, Integer> getSurveyResults();
	
	public void saveTestSurvey();
	
	public void destroy(Survey survey);
	
}
