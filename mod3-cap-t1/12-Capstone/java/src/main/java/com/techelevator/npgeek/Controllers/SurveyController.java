package com.techelevator.npgeek.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techelevator.npgeek.ParkDAO.Park;
import com.techelevator.npgeek.ParkDAO.ParkDAO;
import com.techelevator.npgeek.SurveyDAO.Survey;
import com.techelevator.npgeek.SurveyDAO.SurveyDAO;
import com.techelevator.npgeek.WeatherDAO.WeatherDAO;

@Controller
public class SurveyController {

	@Autowired
	public ParkDAO parkDAO;
	
	@Autowired
	public WeatherDAO weatherDAO;
	
	@Autowired
	public SurveyDAO surveyDAO;
	
	@RequestMapping("/survey")
	public String getSurveyForm (ModelMap map,ModelMap modelHolder, HttpSession session) {
		if (!modelHolder.containsAttribute("survey")) {
			modelHolder.put("survey", new Survey());
		}

		List<Park> allParks = parkDAO.getAllParks();
		map.put("parks", allParks);
		session.setAttribute("currentPage", "survey");
		//limit one survey per user as stretch goal
		return "survey";
	}
	@RequestMapping(path = "/postSurvey", method = RequestMethod.POST)
	public String processMailingListForm(@Valid @ModelAttribute Survey survey, BindingResult result,
			RedirectAttributes flash) {

		flash.addFlashAttribute("survey", survey);
			surveyDAO.save(survey);
		if (result.hasErrors()) {
			flash.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "survey", result);
			return "redirect:/surveys";
		}
		// This is where things happen for the signup
		// mailingListDao.save(signUp);

		return "redirect:/topParks";
	}	
	@RequestMapping (path = "/topParks", method = RequestMethod.GET)
	public String getTopParks(ModelMap map, HttpSession session) {
		List<String> parkCodes = surveyDAO.getTopParks();
		Map<String, Integer> surveyResults = surveyDAO.getSurveyResults();
		map.put("surveyResults", surveyResults);
		List<Park> topParks = new ArrayList<>();
		for (String parkCode : parkCodes) {
			topParks.add(parkDAO.getParkByParkCode(parkCode));
		}
		map.put("topParks", topParks);
		session.setAttribute("currentPage", "topParks");
		return "topParks";
		
	}
}
