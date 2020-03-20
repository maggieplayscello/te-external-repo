package com.techelevator.npgeek.WeatherDAO;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.npgeek.UtilityClasses.Utility;
@Component
public class JDBCWeatherDAO implements WeatherDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JDBCWeatherDAO (DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Weather> getWeatherByParkCode(String parkCode) {
		List <Weather> forecast = new ArrayList <> ();
		String sql = "SELECT * FROM weather WHERE parkcode = ? ORDER BY fivedayforecastvalue";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, parkCode);
		while (results.next()) {
			forecast.add(mapRowToWeather(results));
		}
			
		return forecast;
	}
	@Override
	public List<Weather> getWeatherByParkCode(String parkCode, String tempPref) {
		List <Weather> forecast = new ArrayList <> ();
		String sql = "SELECT * FROM weather WHERE parkcode = ? ORDER BY fivedayforecastvalue";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, parkCode);
		while (results.next()) {
			Weather tempAdjustedForecast =mapRowToWeather(results);
			tempAdjustedForecast.setRecommendation(constructRecommendation(tempAdjustedForecast));
			if (tempPref.equals("C")) {
				tempAdjustedForecast.setHigh(Utility.fToC(tempAdjustedForecast.getHigh()));
				tempAdjustedForecast.setLow(Utility.fToC(tempAdjustedForecast.getLow()));
			}
			forecast.add(tempAdjustedForecast);
		}			
		return forecast;
	}
	
	public Weather mapRowToWeather(SqlRowSet row) {
		Weather weather = new Weather();
		weather.setFiveDayForecastValue(row.getInt("fivedayforecastvalue"));
		weather.setLow(row.getInt("low"));
		weather.setHigh(row.getInt("high"));
		weather.setParkCode(row.getString("parkCode"));
		weather.setForecast(row.getString("forecast"));
		return weather;
	}
	
	public String constructRecommendation(Weather weather) {
		String tempRecommendation = "";
		String weatherRecommendation = "It should be a wonderful day to visit the park!";
		//temp logic
		if (weather.getHigh() > 75) {
			tempRecommendation += "High temperature are expected; pack an extra gallon of water and stay hydrated! ";
		}
		if (weather.getLow() < 20) {
			tempRecommendation += "Prolonged exposure to cold temperatures can lead to frostbite. Please dress warmly! ";
		}
		if (weather.getHigh() - weather.getLow() > 20) {
			tempRecommendation += "Prepare for variable temperatures by dressing in layers. ";
		}
		if (tempRecommendation.equals("")) {
			tempRecommendation = "It should be a wonderful day to visit the park! ";
		}
	
		//weather logic
		if (weather.getForecast().equals("snow")) {
			weatherRecommendation = "Expect ice and snow. Pack snowshoes and crampons.";
		}
		if (weather.getForecast().equals("rain")) {
			weatherRecommendation =  "Expect rain; bring ponchos, waterproof shoes, and a dry change of clothes.";
		}
		if (weather.getForecast().equals("sunny")) {
			weatherRecommendation = "Expect a sunny day. Sunscreen and a hat are smart choices!";
		}
	
		return tempRecommendation + weatherRecommendation;
	}

	@Override
	public void saveTestWeather() {
		String sql = "INSERT INTO weather (parkcode, fivedayforecastvalue, low, high, forecast, recommendation) VALUES ('TEST', 22, -22, 222, 'test', 'test')";
		jdbcTemplate.update(sql);
	}

	@Override
	public void destroyTestWeather() {
		String sql = "DELETE FROM weather WHERE parkcode = 'TEST'";
		jdbcTemplate.update(sql);
	}
	
}







