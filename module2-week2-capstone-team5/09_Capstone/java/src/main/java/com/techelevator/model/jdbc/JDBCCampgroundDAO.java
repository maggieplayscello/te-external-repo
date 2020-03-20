package com.techelevator.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Campground;
import com.techelevator.model.CampgroundDAO;

public class JDBCCampgroundDAO implements CampgroundDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private Campground mapRowToCampgrounds(SqlRowSet results) {
		Campground campground = new Campground();
		campground.setCampgroundId(results.getInt("campground_id"));
		campground.setParkId(results.getInt("park_id"));
		campground.setName(results.getString("name"));
		campground.setOpenFromMM(results.getInt("open_from_mm"));
		campground.setOpenToMM(results.getInt("open_to_mm"));
		campground.setDailyFee(results.getBigDecimal("daily_fee"));
		return campground;
	}

	@Override
	public List<Campground> getAllCampgrounds() {
		List<Campground> allCampgrounds = new ArrayList<>();
		String sqlGetAllCampgrounds = "SELECT * FROM campground";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllCampgrounds);
		while (results.next()) {
			allCampgrounds.add(mapRowToCampgrounds(results));
		}
		return allCampgrounds;
	}

	@Override
	public List<Campground> getCampgroundByParkId(int parkId) {
		List<Campground> campgroundsByParkId = new ArrayList<>();
		String sqlGetCampgroundsByParkId = "SELECT * FROM campground WHERE park_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetCampgroundsByParkId, parkId);
		while (results.next()) {
			campgroundsByParkId.add(mapRowToCampgrounds(results));
		}
		return campgroundsByParkId;
	}

	@Override
	public boolean isParkClosed(int parkId, int campgroundId, int userArrivalMonthInt, int userDepartureMonthInt) {
		String sqlGetCampgroundsByParkId = "SELECT open_from_mm, open_to_mm FROM campground WHERE campground_id = ? AND park_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetCampgroundsByParkId, campgroundId, parkId);
		while (results.next()) {
			int openMonth = results.getInt("open_from_mm");
			int closeMonth = results.getInt("open_to_mm");
			if (userArrivalMonthInt < openMonth || userDepartureMonthInt >= closeMonth) {
				System.out.println("\nSorry, the park is closed during that time. Please make a different selection.");
				return true;
			} else {
				System.out.println("\nAvailable Campsites: \n");
				return false;
			}
		}
		return false;
	}	
}
