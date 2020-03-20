package com.techelevator.model.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Park;
import com.techelevator.model.Reservation;
import com.techelevator.model.Site;
import com.techelevator.model.SiteDAO;

public class JDBCSiteDAO implements SiteDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private Site mapRowToSite(SqlRowSet results) {
		Site site = new Site();
		site.setSiteId(results.getInt("site_id"));
		site.setCampgroundId(results.getInt("campground_id"));
		site.setSiteNumber(results.getInt("site_number"));
		site.setMaxOccupancy(results.getInt("max_occupancy"));
		site.setAccessible(results.getBoolean("accessible"));
		site.setMaxRVLength(results.getInt("max_rv_length"));
		site.setUtilities(results.getBoolean("utilities"));
		return site;
	}

	@Override
	public List<Site> getAllSites() {
		List<Site> allSites = new ArrayList<>();
		String sqlGetAllSites = "SELECT * FROM reservation r JOIN site s ON s.site_id = r.site_id "
				+ "JOIN campground c ON c.campground_id = s.campground_id JOIN park p on p.park_id = c.park_id";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllSites);
		while (results.next()) {
			allSites.add(mapRowToSite(results));
		}
		System.out.println(allSites);
		return allSites;
	}

	@Override
	public List<Site> checkSiteAvailability(int userSelectedCampgroundId, LocalDate userSelectedArrivalDate, LocalDate userSelectedDepartureDate) {
		List<Site> availableSites = new ArrayList<>();
		String sqlGetAvailableSites = 
				"SELECT * FROM site WHERE campground_id = ? AND site_id NOT IN (SELECT site_id FROM reservation " 
				+ "WHERE ( to_date <= ? AND from_date >= ?) OR ( to_date <= ? AND from_date >= ? ) OR (to_date >= ? AND to_date <= ?)) LIMIT 5 ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAvailableSites, userSelectedCampgroundId,
				userSelectedArrivalDate, userSelectedArrivalDate, userSelectedDepartureDate, userSelectedDepartureDate,
				userSelectedArrivalDate, userSelectedDepartureDate);
		while (results.next()) {
			availableSites.add(mapRowToSite(results));		
		} if (availableSites.size() < 1) {
			System.out.println("Sorry, there are no campsites available at that time.");
		} else 
		System.out.printf("%-10s %-15s %-12s %-15s %-12s \n", "Site Number", "Max Occupance", "Accessible",
				"Max RV Length", "Utilities");
		System.out.printf("%-10s %-15s %-12s %-15s %-12s \n", "___________", "_____________", "__________",
				"_____________", "_________");
		for (Site site : availableSites) {
			System.out.printf("%7d %12d %15s %11d %15s \n", site.getSiteNumber(), site.getMaxOccupancy(),
					site.isAccessible(),  site.getMaxRVLength(), site.isUtilities());
		return availableSites;
			}
		return availableSites;
	}
}
