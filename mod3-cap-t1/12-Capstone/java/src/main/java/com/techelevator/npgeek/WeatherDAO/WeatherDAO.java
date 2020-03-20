package com.techelevator.npgeek.WeatherDAO;

import java.util.List;

public interface WeatherDAO {

	public List<Weather> getWeatherByParkCode(String parkCode);

	public List<Weather> getWeatherByParkCode(String parkCode, String tempPref);
	
	public void saveTestWeather();
	
	public void destroyTestWeather();
	
}
