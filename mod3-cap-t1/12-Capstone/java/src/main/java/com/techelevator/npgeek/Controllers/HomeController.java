package com.techelevator.npgeek.Controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.techelevator.npgeek.ParkDAO.Park;
import com.techelevator.npgeek.ParkDAO.ParkDAO;
import com.techelevator.npgeek.WeatherDAO.Weather;
import com.techelevator.npgeek.WeatherDAO.WeatherDAO;

@Controller
public class HomeController {

	@Autowired
	public ParkDAO parkDAO;
	
	@Autowired
	public WeatherDAO weatherDAO;
	
	@RequestMapping("/")
	public String getHome (ModelMap map , HttpSession session) {
		List<Park> allParks = parkDAO.getAllParks();
		map.put("parks", allParks);
		if(session.getAttribute("temppref") == null) {
			session.setAttribute("temppref", "F");
		}
		session.setAttribute("currentPage", "");
		return "home";
	}
	
	@RequestMapping("/parkDetails")
	public String displayParkDetails(@RequestParam String parkCode, ModelMap map, HttpSession session) {
		Park park = parkDAO.getParkByParkCode(parkCode);
		String tempPref=(String) session.getAttribute("temppref");
		List <Weather> forecast = weatherDAO.getWeatherByParkCode(parkCode,tempPref);
		map.put("park", park);
		map.put("forecast", forecast);
		session.setAttribute("currentPage", "parkDetails?parkCode="+parkCode);
		return "parkDetails";
	}
	@RequestMapping("/changeTemp")
	public String getHome (HttpSession session) {
		if(session.getAttribute("temppref") == null) {
			session.setAttribute("temppref", "F");
		}
		if(session.getAttribute("temppref").equals("F")) {
			session.setAttribute("temppref", "C");
		} else {
			session.setAttribute("temppref", "F");
		}
		return "redirect:/"+session.getAttribute("currentPage");
	}
	

}
