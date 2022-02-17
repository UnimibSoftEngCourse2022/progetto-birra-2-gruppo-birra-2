package group.brewdaytwo.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import group.brewdaytwo.attrezzo.dao.AttrezzoDAO;
import group.brewdaytwo.dao.birra.BirraDAO;
import group.brewdaytwo.ingrediente.dao.IngredienteDAO;
import group.brewdaytwo.model.birra.Birra;
import group.brewdaytwo.ricetta.dao.RicettaDAO;
import group.brewdaytwo.ricetta.model.Ricetta;

@Controller
public class ControllerUtente {
	
	@Autowired
	private IngredienteDAO IngredienteDAO;
	
	@Autowired
	private AttrezzoDAO AttrezzoDAO;
	
	@Autowired
	private RicettaDAO RicettaDAO; 
	
	@Autowired
	private BirraDAO BirraDAO; 
	
	@PostMapping(value="/editUserIng")
	public ModelAndView editUserIng(@RequestBody String request) {
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
	
	@PostMapping(value="/makebeer")
	public ModelAndView makeBeer(@RequestBody String request) throws IOException{
		ModelAndView model = new ModelAndView("brewsPage"); 
		String[] values = request.split("&");
		
		double quantita = Double.parseDouble(values[0].split("=")[1]);
		String autore = values[1].split("=")[1];
		int IDRicetta = Integer.parseInt(values[2].split("=")[1]);
		String note = "";
		if(values[3].length() > 5)
			note = values[3].split("=")[1];
		
		List<String> spesa = BirraDAO.controlloCreaBirra(IDRicetta, quantita, autore);
		if(spesa.size() == 0) {
			Birra birra = new Birra(0, note, quantita, autore, IDRicetta); 
			BirraDAO.save(birra); 
		}else {
			model.addObject("spesa", spesa);
		}
		List<String> listBirre = BirraDAO.getBirre(autore);
		model.addObject("listBirre", listBirre);
		return model; 
	}
	

	@GetMapping(value="/makeBeerPage")
	public ModelAndView makeBeerPage(HttpSession session,HttpServletRequest request) throws IOException{
		int recipeID = Integer.parseInt(request.getParameter("id"));
		session.setAttribute("IDRicetta", recipeID);
		Ricetta r = RicettaDAO.get(recipeID);
		List<String> listRecComponents = RicettaDAO.getComponents(recipeID);
		List<String> listRecTools = RicettaDAO.getTools(recipeID);
		ModelAndView model = new ModelAndView("makeBeerPage");
		model.addObject("Ricetta", r);
		model.addObject("listRecComponents", listRecComponents);
		model.addObject("listRecTools", listRecTools);
		return model;
	}
	
	@GetMapping(value="/createBeer")
	public ModelAndView loadBeerPage(ModelAndView model) throws IOException{
		model.setViewName("showRecBeerPage");
		return model;
	}

}
