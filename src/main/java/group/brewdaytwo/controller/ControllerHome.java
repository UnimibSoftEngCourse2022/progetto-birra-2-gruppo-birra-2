package group.brewdaytwo.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControllerHome {
	
	@GetMapping(value="/recipes")
	public ModelAndView loadRecipesPage(ModelAndView model) throws IOException{
		model.setViewName("recipesPage");
		return model;
	}
	
	@GetMapping(value="/logout")
	public ModelAndView loadFirstPage(HttpSession session,ModelAndView model) throws IOException{
		session.invalidate();
		model.setViewName("firstPage");
		return model;
	}
}
