package group.brewdaytwo.domain.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import group.brewdaytwo.domain.model.ricetta.Ricetta;
import group.brewdaytwo.domain.model.utente.Utente;
import group.brewdaytwo.services.dao.ricetta.RicettaDAO;
import group.brewdaytwo.services.dao.utente.UtenteDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControllerAccessoSito {

	@Autowired
	private UtenteDAO utenteDAO;
	
	@Autowired
	private RicettaDAO ricettaDAO;
	
	public String decode(String input) 
		{
			input = input.replace("%27", "'");
			input = input.replace("%21", "!");
			input = input.replace("%23", "#");
			input = input.replace("%5E", "^");
			input = input.replace("%7E", "~");
			input = input.replace("%24", "$");
			input = input.replace("%25", "%");
			input = input.replace("%2B", "+");
			input = input.replace("%2F", "/");
			input = input.replace("%3F", "?");
			input = input.replace("%60", "`");
			input = input.replace("%7D", "}");
			input = input.replace("%7B", "{");
			input = input.replace("%7C", "|");
			return input;
		}
	
	@GetMapping(value="/")
	public ModelAndView loadFirstPage(ModelAndView model){
		model.setViewName("firstPage");
		return model;
	}
	
	@GetMapping(value="/signin")
	public ModelAndView loadSigninPage(ModelAndView model){
		model.setViewName("signinPage");
		model.addObject("alertFlagNick", false);
		model.addObject("alertFlagEmail", false);
		return model;
	}
	
	@GetMapping(value="/login")
	public ModelAndView loadLoginPage(ModelAndView model){
		model.setViewName("loginPage");
		model.addObject("alertFlag", false);
		return model;
	}
	
	@GetMapping(value="/homePage")
	public ModelAndView loadHomePage(HttpSession session,ModelAndView model){
		String nick = (String)session.getAttribute("autore");
		Ricetta ricetta = ricettaDAO.getCDPO(nick);
		List<Ricetta> listRicetta = new ArrayList<>();
		List<String> listQuantita = new ArrayList<>();
		if(!Objects.isNull(ricetta))
			{
				listRicetta.add(ricetta);
				listQuantita.add(ricettaDAO.getQuantita(nick,ricetta.getID()));
			}
		model.setViewName("homePage");
		model.addObject("listRicetta", listRicetta);
		model.addObject("listQuantita", listQuantita);
		return model;
	}
	
	@PostMapping(value = "/signin")
	public ModelAndView saveUtente(HttpSession session,@ModelAttribute Utente u) {
		try{
			utenteDAO.save(u);
		}catch(DataAccessException ex)
		{
			if(ex.toString().contains("users.PRIMARY"))
			{
				ModelAndView model = new ModelAndView("signinPage");
				model.addObject("alertFlagNick", true);
				model.addObject("alertFlagEmail", false);
				return model;
			}
			else
			{
				ModelAndView model = new ModelAndView("signinPage");
				model.addObject("alertFlagNick", false);
				model.addObject("alertFlagEmail", true);
				return model;
			}
		}
		session.setAttribute("autore", u.getNickname());
		return new ModelAndView("redirect:/homePage");
	}
	
	@PostMapping(value = "/login")
	public ModelAndView checkUtente(HttpSession session,@RequestBody String request) {
		request = decode(request);
		String[] values = request.split("&");
		
		String nick = values[0].split("=")[1];
		String pwd = values[1].split("=")[1].replace("%26", "&").replace("%3D", "=");
		if(Objects.isNull(utenteDAO.check(nick,pwd)))
		{
			ModelAndView model = new ModelAndView("loginPage");
			model.addObject("alertFlag", true);
			return model;
		}
			
		else
		{
			ModelAndView model = new ModelAndView("redirect:/homePage");
			session.setAttribute("autore", nick);
			return model;
		}
		
	}
}
