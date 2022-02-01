package group.brewdaytwo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import group.brewdaytwo.utente.dao.UtenteDAO;
import group.brewdaytwo.utente.model.Utente;
import group.brewdaytwo.ricetta.dao.RicettaDAO;
import group.brewdaytwo.ricetta.model.Ricetta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControllerHome {
	
	@Autowired
	private UtenteDAO utenteDAO;
	
	@Autowired
	private RicettaDAO ricettaDAO;
	
	@GetMapping(value="/recipes")
	public ModelAndView loadRecipesPage(ModelAndView model) throws IOException{
		model.setViewName("recipes");
		return model;
	}
	
	

}
