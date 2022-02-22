package group.brewdaytwo.domain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import group.brewdaytwo.domain.model.birra.Birra;
import group.brewdaytwo.domain.model.ricetta.Ricetta;
import group.brewdaytwo.services.dao.attrezzo.AttrezzoDAO;
import group.brewdaytwo.services.dao.birra.BirraDAO;
import group.brewdaytwo.services.dao.ingrediente.IngredienteDAO;
import group.brewdaytwo.services.dao.ricetta.RicettaDAO;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession; 

@Controller
public class ControllerUtente {
	
	@Autowired
	private IngredienteDAO ingredienteDAO;
	
	@Autowired
	private AttrezzoDAO attrezzoDAO;
	
	@Autowired 
	private BirraDAO birraDAO; 
	
	@Autowired
	private RicettaDAO ricettaDAO;
	
	@PostMapping(value="/editUserIng")
	public ModelAndView editUserIng(@RequestBody String request) {
		request = request.replace("+", " ");
		String[] values = request.split("&");
		String autore = values[values.length-1].substring(values[values.length-1].lastIndexOf("=") + 1);
		String comp="";
		ingredienteDAO.deleteUserIng(autore);
		for (int i = 0; i < values.length-1; ++i)
		  {
		      if(values[i].contains("comp"))
		        comp = values[i].substring(values[i].lastIndexOf("=") + 1);
		      else
		    	  ingredienteDAO.saveUserIng(autore,comp, Double.parseDouble(values[i].substring(values[i].lastIndexOf("=") + 1)));
		  }
		return new ModelAndView("redirect:/homePage");
	}
	
	@PostMapping(value="/editUserTools")
	public ModelAndView editUserTools(@RequestBody String request) {
		String[] values = request.split("&");
		String autore = values[values.length-1].substring(values[values.length-1].lastIndexOf("=") + 1);
		String eqp="";
		attrezzoDAO.deleteUserTool(autore);
		for (int i = 0; i < values.length-1; ++i)
		  {
		      if(values[i].contains("eqp"))
		        eqp = values[i].substring(values[i].lastIndexOf("=") + 1);
		      else
		    	attrezzoDAO.saveUserTool(autore,eqp, Integer.parseInt(values[i].substring(values[i].lastIndexOf("=") + 1)));
		  }
		return new ModelAndView("redirect:/homePage");
	}
	
	
	@PostMapping(value="/makebeer")
	public ModelAndView makeBeer(@RequestBody String request){
		ModelAndView model = new ModelAndView("brewsPage"); 
		request = request.replace("+", " ");
		request = ControllerRicette.decodeRicerca(request);
		String[] values = request.split("&");
		
		double quantita = Double.parseDouble(values[0].split("=")[1]);
		String autore = values[1].split("=")[1];
		int iDRicetta = Integer.parseInt(values[2].split("=")[1]);
		String note = "";
		if(values[3].length() > 5)
			note = values[3].split("=")[1];
		note = note.replace("%26", "&");
		note = note.replace("%25", "%");
		note = note.replace("%3D", "=");
		
		List<String> spesa = birraDAO.controlloCreaBirra(iDRicetta, quantita, autore);
		if(spesa.isEmpty()) {
			Birra birra = new Birra(0, note, quantita, autore, iDRicetta); 
			birraDAO.save(birra); 
		}else {
			model.addObject("spesa", spesa);
		}
		List<String> listBirre = birraDAO.getBirre(autore);
		model.addObject("listBirre", listBirre);
		return model; 
	}
	

	@GetMapping(value="/makeBeerPage")
	public ModelAndView makeBeerPage(HttpSession session,HttpServletRequest request){
		int recipeID = Integer.parseInt(request.getParameter("id"));
		session.setAttribute("IDRicetta", recipeID);
		Ricetta r = ricettaDAO.get(recipeID);
		List<String> listRecComponents = ingredienteDAO.getComponents(recipeID);
		List<String> listRecTools = attrezzoDAO.getTools(recipeID);
		ModelAndView model = new ModelAndView("makeBeerPage");
		model.addObject("Ricetta", r);
		model.addObject("listRecComponents", listRecComponents);
		model.addObject("listRecTools", listRecTools);
		return model;
	}
	
	@GetMapping(value="/createBeer")
	public ModelAndView loadBeerPage(ModelAndView model){
		model.setViewName("showRecBeerPage");
		return model;
	}
	
	@GetMapping(value="/infoCreateBeer")
	public ModelAndView loadInfoCreateBeerPage(HttpSession session,ModelAndView model){
		int idRic = (int)session.getAttribute("IDRicetta");
		Ricetta r = ricettaDAO.get(idRic);
		model.setViewName("infoCreateBeerPage");
		model.addObject("Ricetta", r);
		return model;
	}
	
	

}
