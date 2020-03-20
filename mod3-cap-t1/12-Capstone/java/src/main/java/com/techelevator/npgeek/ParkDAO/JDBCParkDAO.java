package com.techelevator.npgeek.ParkDAO;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JDBCParkDAO implements ParkDAO {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JDBCParkDAO (DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Park> getAllParks() {
		List<Park> allParks = new ArrayList<>();
		String sql = "SELECT * FROM park";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while (results.next()) {
			allParks.add(mapRowToPark(results));
		}
		return allParks;
	}

	@Override
	public Park getParkByParkCode(String parkCode) {
		Park park = new Park();
		String sql = "SELECT * FROM park WHERE parkcode = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, parkCode);
		while (results.next()) {
			park = mapRowToPark(results);
		}
		return park;
	}

	public Park mapRowToPark(SqlRowSet row) {
		Park park = new Park();
		park.setAcreage(row.getInt("acreage"));
		park.setAnnualVisitorCount(row.getInt("annualvisitorcount"));
		park.setClimate(row.getString("climate"));
		park.setElevationInFeet(row.getInt("elevationinfeet"));
		park.setEntryFee(row.getInt("entryfee"));
		park.setInspirationalQuote(row.getString("inspirationalquote"));
		park.setInspirationalQuoteSource(row.getString("inspirationalquotesource"));
		park.setMilesOfTrail(row.getDouble("milesoftrail"));
		park.setNumberOfAnimalSpecies(row.getInt("numberofanimalspecies"));
		park.setNumberOfCampsites(row.getInt("numberofcampsites"));
		park.setParkCode(row.getString("parkCode"));
		park.setParkDescription(row.getString("parkDescription"));
		park.setParkName(row.getString("parkname"));
		park.setState(row.getString("state"));
		park.setYearFounded(row.getInt("yearfounded"));		
		return park;
	}

	@Override
	public void saveTestPark() {
		String sql = "INSERT INTO park (acreage, annualvisitorcount, climate, elevationinfeet, entryfee, inspirationalquote, inspirationalquotesource, "
				+ "milesoftrail, numberofanimalspecies, numberofcampsites, parkcode, parkdescription, parkname, state, yearfounded) " 
				+ "VALUES ('22', '22', 'test', '22', '22', 'test', 'test', '22', '22', '22, 'TEST', 'test', 'test', 'test', '1066')";
		jdbcTemplate.update(sql);
	}

	@Override
	public void deleteTestPark() {
		String sql = "DELETE FROM park WHERE parkcode = 'TEST'";
		jdbcTemplate.update(sql);
	}
	
}
