package group.brewdaytwo.controller;

import java.io.IOException;

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
		//ModelAndView model = new ModelAndView("beerCreatePage");
		ModelAndView m; 
		String[] values = request.split("&");
		
		double quantita = Double.parseDouble(values[0].split("=")[1]);
		String autore = values[1].split("=")[1];
		int IDRicetta = Integer.parseInt(values[2].split("=")[1]); 		
		String note = values[3].split("=")[1];
		
		System.out.println(values[0]);
		System.out.println(values[1]);
		System.out.println(values[2]);
		System.out.println(values[3]);
		
		boolean b = BirraDAO.controlloCreaBirra(IDRicetta, quantita, autore);
		if(b) {
			
			
			Birra bb = new Birra(0, note, quantita, autore, IDRicetta); 
			BirraDAO.save(bb); 
			
			m = new ModelAndView("homePage"); 
			
			
		}else {
			
			m = new ModelAndView("beerCreatePage"); 
		}
		return m; 
	}
	

	@GetMapping(value="/makeBeerPage")
	public ModelAndView makeBeerPage(HttpSession session,HttpServletRequest request) throws IOException{
		int recipeID = Integer.parseInt(request.getParameter("id"));
		session.setAttribute("IDRicetta", recipeID);
		Ricetta r = RicettaDAO.get(recipeID);
		ModelAndView model = new ModelAndView("makeBeerPage");
		model.addObject("Ricetta", r);
		return model;
	}

}
