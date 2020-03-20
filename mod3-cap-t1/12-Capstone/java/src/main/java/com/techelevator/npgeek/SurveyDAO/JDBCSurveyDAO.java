package com.techelevator.npgeek.SurveyDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JDBCSurveyDAO implements SurveyDAO {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JDBCSurveyDAO (DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public boolean save(Survey survey) {
		String sql = "INSERT INTO survey_result (surveyid, parkcode, emailaddress, state, activitylevel)  VALUES (nextval('seq_surveyid'),?,?,?,?)";
		jdbcTemplate.update(sql, survey.getParkCode(), survey.getEmailAddress(), survey.getState(), survey.getActivityLevel());
		return false;
	}

	@Override
	public List<String> getTopParks() {
		
		String sqlTopParks ="SELECT parkcode, COUNT(parkcode) FROM survey_result GROUP BY parkcode ORDER BY COUNT(parkcode) DESC, parkcode ASC;";
		List<String> topParkCodes =new ArrayList <>();
		SqlRowSet parkCode= jdbcTemplate.queryForRowSet(sqlTopParks); 
		while(parkCode.next()) {
			topParkCodes.add(parkCode.getString("parkcode"));
		}
		return topParkCodes;
	}
	
	@Override
	public Map<String, Integer> getSurveyResults() {
		String sqlTopParks ="SELECT parkcode, COUNT(parkcode) FROM survey_result GROUP BY parkcode ORDER BY COUNT(parkcode) DESC;";
		Map<String, Integer> surveyResults = new HashMap<>();
		SqlRowSet parkCode= jdbcTemplate.queryForRowSet(sqlTopParks); 
		while(parkCode.next()) {
			surveyResults.put(parkCode.getString("parkcode"), parkCode.getInt("count"));
		}
		return surveyResults;
	}

	@Override
	public void saveTestSurvey() {
		String sql = "INSERT INTO survey_result (surveyid, parkcode, emailaddress, state, activitylevel) "
				+ " VALUES ('2147483647', 'TEST', 'test@test.com', 'test', 'test')";
		jdbcTemplate.update(sql);
	}

	@Override
	public void destroy(Survey survey) {
			String sql = "DELETE FROM survey WHERE surveyid = '2147483647'";
			jdbcTemplate.update(sql);
	}
}

