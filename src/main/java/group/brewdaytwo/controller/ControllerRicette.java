package group.brewdaytwo.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
	
	@GetMapping(value="/editRecipe")
	public ModelAndView editRecipe(HttpServletRequest request) throws IOException{
		int recipeID = Integer.parseInt(request.getParameter("id"));
		Ricetta r = RicettaDAO.get(recipeID);
		List<String> listRecComponents = RicettaDAO.getComponents(recipeID);
		List<String> listRecTools = RicettaDAO.getTools(recipeID);
		ModelAndView model = new ModelAndView("editRecipePage");
		model.addObject("Ricetta", r);
		model.addObject("listRecComponents", listRecComponents);
		model.addObject("listRecTools", listRecTools);
		return model;
	}
	
	@GetMapping(value="/deleteRecipe")
	public ModelAndView deleteRecipe(HttpServletRequest request) throws IOException{
		int recipeID = Integer.parseInt(request.getParameter("id"));
		RicettaDAO.delete(recipeID);
		ModelAndView model = new ModelAndView("recipesPage");
		return model;
	}
	
	@GetMapping(value="/modifyRecipe")
	public ModelAndView modifyRecipe(HttpSession session,HttpServletRequest request) throws IOException{
		int recipeID = Integer.parseInt(request.getParameter("id"));
		session.setAttribute("ricettaID", recipeID);
		Ricetta r = RicettaDAO.get(recipeID);
		ModelAndView model = new ModelAndView("modifyInfoRecipePage");
		model.addObject("Ricetta", r);
		return model;
	}
	
	@PostMapping(value="/modifyInfoRecipe")
	public ModelAndView modifyInfoRecipe(@ModelAttribute Ricetta r) {
		RicettaDAO.update(r);
		
		List<Ingrediente> listMalto = IngredienteDAO.list("Malto");
		List<Ingrediente> listZucchero = IngredienteDAO.list("Zucchero");
		List<Ingrediente> listLuppolo = IngredienteDAO.list("Luppolo");
		List<Ingrediente> listLievito = IngredienteDAO.list("Lievito");
		List<Ingrediente> listAdditivo = IngredienteDAO.list("Additivo");
		
		List<String> listRecComponents = RicettaDAO.getComponents(r.getID());
		
		ModelAndView model = new ModelAndView("modifyCompRecipePage");
		
		model.addObject("listMalto", listMalto);
		model.addObject("listZucchero", listZucchero);
		model.addObject("listLuppolo", listLuppolo);
		model.addObject("listLievito", listLievito);
		model.addObject("listAdditivo", listAdditivo);
		
		model.addObject("listRecComponents", listRecComponents);
		
		return model;
	}
	
	@PostMapping(value="/modifyCompRecipe")
	public ModelAndView modifyCompRecipe(@RequestBody String request) {
		String[] values = request.split("&");
		String ricetta = values[values.length-1].substring(values[values.length-1].lastIndexOf("=") + 1);
		String comp="";
		IngredienteDAO.deleteComponent(ricetta);
		for (int i = 0; i < values.length-1; ++i)
		  {
		      if(values[i].contains("comp"))
		        comp = values[i].substring(values[i].lastIndexOf("=") + 1);
		      else
		    	  IngredienteDAO.saveComponent(ricetta,comp, Double.parseDouble(values[i].substring(values[i].lastIndexOf("=") + 1)));
		  }
		ModelAndView model = new ModelAndView("modifyToolsRecipePage");
		
		List<Attrezzo> listAttrezzi = AttrezzoDAO.list();
		List<String> listRecTools = RicettaDAO.getTools(Integer.parseInt(ricetta));
		
		model.addObject("listRecTools", listRecTools);
		model.addObject("listAttrezzi", listAttrezzi);
		
		return model;
	}
	
	@PostMapping(value="/modifyToolsRecipe")
	public ModelAndView modifyToolsRecipe(@RequestBody String request) {
		String[] values = request.split("&");
		String ricetta = values[values.length-1].substring(values[values.length-1].lastIndexOf("=") + 1);
		String eqp="";
		AttrezzoDAO.deleteRecTool(ricetta);
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
	
	@PostMapping(value="/showrecipes")
	public ModelAndView showRecipes(@RequestBody String request) throws IOException{
		ModelAndView model = new ModelAndView("recipesPage");
		String[] values = request.split("&");
		String nome = values[0].split("=")[1];
		String autore = values[1].split("=")[1];
		List<Ricetta> listRicette = RicettaDAO.list(nome,autore);
		model.addObject("listRicette", listRicette);
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
