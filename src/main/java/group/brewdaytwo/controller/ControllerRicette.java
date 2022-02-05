package group.brewdaytwo.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import group.brewdaytwo.ricetta.dao.RicettaDAO;
import group.brewdaytwo.attrezzo.model.Attrezzo;
import group.brewdaytwo.ingrediente.dao.IngredienteDAO;
import group.brewdaytwo.ingrediente.model.Ingrediente;
import group.brewdaytwo.ricetta.model.Ricetta;
import group.brewdaytwo.attrezzo.dao.AttrezzoDAO;

@Controller
public class ControllerRicette {
	
	@Autowired
	private RicettaDAO RicettaDAO;
	
	@Autowired
	private IngredienteDAO IngredienteDAO;
	
	@Autowired
	private AttrezzoDAO AttrezzoDAO;
	
	@GetMapping(value="/Addrecipes")
	public ModelAndView loadRecipesPage(ModelAndView model) throws IOException{
		model.setViewName("recipesAddPage");
		return model;
	}
	
	@PostMapping(value="/Addrecipes")
	public ModelAndView saveRicetta(HttpSession session,@ModelAttribute Ricetta r) {
		int ricettaID = RicettaDAO.save(r);
		List<Ingrediente> listMalto = IngredienteDAO.list("Malto");
		List<Ingrediente> listZucchero = IngredienteDAO.list("Zucchero");
		List<Ingrediente> listLuppolo = IngredienteDAO.list("Luppolo");
		List<Ingrediente> listLievito = IngredienteDAO.list("Lievito");
		List<Ingrediente> listAdditivo = IngredienteDAO.list("Additivo");
		
		ModelAndView model = new ModelAndView("recipesAddComponents");
		model.addObject("listMalto", listMalto);
		model.addObject("listZucchero", listZucchero);
		model.addObject("listLuppolo", listLuppolo);
		model.addObject("listLievito", listLievito);
		model.addObject("listAdditivo", listAdditivo);
		session.setAttribute("ricettaID", ricettaID);
		return model;
	}
	
	@PostMapping(value="/addcomponents")
	public ModelAndView saveComponents(@RequestBody String request) {
		String[] values = request.split("&");
		String ricetta = values[values.length-1].substring(values[values.length-1].lastIndexOf("=") + 1);
		String comp="";
		for (int i = 0; i < values.length-1; ++i)
		  {
		      if(values[i].contains("comp"))
		        comp = values[i].substring(values[i].lastIndexOf("=") + 1);
		      else
		    	IngredienteDAO.saveComponent(ricetta,comp, Double.parseDouble(values[i].substring(values[i].lastIndexOf("=") + 1)));
		  }
		ModelAndView model = new ModelAndView("recipesAddEquipments");
		List<Attrezzo> listAttrezzi = AttrezzoDAO.list();
		model.addObject("listAttrezzi", listAttrezzi);
		return model;
	}
	
	@PostMapping(value="/addrecequips")
	public ModelAndView saveEquipments(@RequestBody String request) {
		String[] values = request.split("&");
		String ricetta = values[values.length-1].substring(values[values.length-1].lastIndexOf("=") + 1);
		String eqp="";
		for (int i = 0; i < values.length-1; ++i)
		  {
		      if(values[i].contains("eqp"))
		        eqp = values[i].substring(values[i].lastIndexOf("=") + 1);
		      else
		    	AttrezzoDAO.saveRecEquipment(ricetta,eqp, Integer.parseInt(values[i].substring(values[i].lastIndexOf("=") + 1)));
		  }
		ModelAndView model = new ModelAndView("redirect:/recipes");
		return model;
	}

}
