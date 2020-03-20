package com.techelevator;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.npgeek.ParkDAO.JDBCParkDAO;
import com.techelevator.npgeek.ParkDAO.Park;
import com.techelevator.npgeek.SurveyDAO.JDBCSurveyDAO;
import com.techelevator.npgeek.WeatherDAO.JDBCWeatherDAO;

public abstract class DAOIntegrationTest {

	/* Using this particular implementation of DataSource so that
	 * every database interaction is part of the same database
	 * session and hence the same database transaction */
	private static SingleConnectionDataSource dataSource;
	private JDBCSurveyDAO jdbcSurveyDAO;
	private JDBCParkDAO jdbcParkDAO;
	private JDBCWeatherDAO jdbcWeatherDAO;
	

	@Before
	public void setupTestObjects() {
		jdbcSurveyDAO.saveTestSurvey();
		jdbcParkDAO.saveTestPark();
		jdbcWeatherDAO.saveTestWeather();
	}
	
	/* Before any tests are run, this method initializes the datasource for testing. */
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/historygeek");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		/* The following line disables autocommit for connections
		 * returned by this DataSource. This allows us to rollback
		 * any changes after each test */
		dataSource.setAutoCommit(false);
	}

	/* After all tests have finished running, this method will close the DataSource */
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	/* After each test, we rollback any changes that were made to the database so that
	 * everything is clean for the next test */
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	/* This method provides access to the DataSource for subclasses so that
	 * they can instantiate a DAO for testing */
	public DataSource getDataSource() {
		return dataSource;
	}
	
	@Test
	public void get_test_park_by_id_returns_test_park() {
		Park expected = new Park();
		expected.setAcreage(22);
		expected.setAnnualVisitorCount(22);
		expected.setClimate("test");
		expected.setElevationInFeet(22);
		expected.setEntryFee(22);
		expected.setInspirationalQuote("test");
		expected.setInspirationalQuoteSource("test");
		expected.setMilesOfTrail(22);
		expected.setNumberOfAnimalSpecies(22);
		expected.setNumberOfCampsites(22);
		expected.setParkCode("TEST");
		expected.setParkDescription("test");
		expected.setParkName("test");
		expected.setState("test");
		expected.setYearFounded(1066);	
		Park actual = jdbcParkDAO.getParkByParkCode("TEST");
		assertEquals(expected, actual);
	}
	
}
