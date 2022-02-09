package group.brewdaytwo.controller;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import group.brewdaytwo.utente.dao.UtenteDAO;
import group.brewdaytwo.utente.model.Utente;

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
	public ModelAndView loadFirstPage(ModelAndView model) throws IOException{
		model.setViewName("firstPage");
		return model;
	}
	
	@GetMapping(value="/signin")
	public ModelAndView loadSigninPage(ModelAndView model) throws IOException{
		model.setViewName("signinPage");
		model.addObject("alertFlagNick", false);
		model.addObject("alertFlagEmail", false);
		return model;
	}
	
	@GetMapping(value="/login")
	public ModelAndView loadLoginPage(ModelAndView model) throws IOException{
		model.setViewName("loginPage");
		model.addObject("alertFlag", false);
		return model;
	}
	
	@GetMapping(value="/homePage")
	public ModelAndView loadHomePage(ModelAndView model) throws IOException{
		model.setViewName("homePage");
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
		String values[] = request.split("&");
		
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
