package group.brewdaytwo.domain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import group.brewdaytwo.services.dao.attrezzo.AttrezzoDAO;
import group.brewdaytwo.services.dao.ingrediente.IngredienteDAO;

@Controller
public class ControllerUtente {
	
	@Autowired
	private IngredienteDAO IngredienteDAO;
	
	@Autowired
	private AttrezzoDAO AttrezzoDAO;
	
	@PostMapping(value="/editUserIng")
	public ModelAndView editUserIng(@RequestBody String request) {
		request = request.replace("+", " ");
		String[] values = request.split("&");
		String autore = values[values.length-1].substring(values[values.length-1].lastIndexOf("=") + 1);
		String comp="";
		IngredienteDAO.deleteUserIng(autore);
		for (int i = 0; i < values.length-1; ++i)
		  {
		      if(values[i].contains("comp"))
		        comp = values[i].substring(values[i].lastIndexOf("=") + 1);
		      else
		    	  IngredienteDAO.saveUserIng(autore,comp, Double.parseDouble(values[i].substring(values[i].lastIndexOf("=") + 1)));
		  }
		ModelAndView model = new ModelAndView("redirect:/homePage");
		
		return model;
	}
	
	@PostMapping(value="/editUserTools")
	public ModelAndView editUserTools(@RequestBody String request) {
		String[] values = request.split("&");
		String autore = values[values.length-1].substring(values[values.length-1].lastIndexOf("=") + 1);
		String eqp="";
		AttrezzoDAO.deleteUserTool(autore);
		for (int i = 0; i < values.length-1; ++i)
		  {
		      if(values[i].contains("eqp"))
		        eqp = values[i].substring(values[i].lastIndexOf("=") + 1);
		      else
		    	AttrezzoDAO.saveUserTool(autore,eqp, Integer.parseInt(values[i].substring(values[i].lastIndexOf("=") + 1)));
		  }
		ModelAndView model = new ModelAndView("redirect:/homePage");
		return model;
	}

}
