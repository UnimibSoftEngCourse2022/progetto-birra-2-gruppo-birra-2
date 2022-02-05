package group.brewdaytwo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
	
	public Map<String, String> parseBody(String str) {
		Map<String, String> body = new HashMap<>();
		  String[] values = str.split("&");
		  for (int i = 0; i < values.length; ++i)
		  {
			  String[] coppia = values[i].split("=");
			    if (coppia.length != 2) {
			    	continue;
			    }
			    else
			    {
			      body.put(coppia[0], coppia[1]);
			    }
		  }
		  return body;
	}
	
	@Autowired
	private UtenteDAO utenteDAO;
	
	@GetMapping(value="/")
	public ModelAndView loadFirstPage(ModelAndView model) throws IOException{
		model.setViewName("firstPage");
		return model;
	}
	
	@GetMapping(value="/signin")
	public ModelAndView loadSigninPage(ModelAndView model) throws IOException{
		model.setViewName("signinPage");
		return model;
	}
	
	@GetMapping(value="/login")
	public ModelAndView loadLoginPage(ModelAndView model) throws IOException{
		model.setViewName("loginPage");
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
				return new ModelAndView("signinFailedNickPage");
			else
				return new ModelAndView("signinFailedEmailPage");
		}
		session.setAttribute("autore", u.getNickname());
		return new ModelAndView("redirect:/homePage");
	}
	
	@PostMapping(value = "/login")
	public ModelAndView checkUtente(HttpSession session,@RequestBody String request) {
		
		request=request.replace("%40", "@");
		
		Map<String, String> body = parseBody(request);
		
		String nick = body.get("nickname");
		String pwd = body.get("password");
		
		if(Objects.isNull(utenteDAO.check(nick,pwd)))
			return new ModelAndView("loginFailedPage");
		else
		{
			ModelAndView model = new ModelAndView("redirect:/homePage");
			session.setAttribute("autore", nick);
			return model;
		}
		
	}
	
	

}
