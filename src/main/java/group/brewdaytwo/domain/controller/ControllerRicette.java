package group.brewdaytwo.domain.controller;

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

import group.brewdaytwo.domain.model.attrezzo.Attrezzo;
import group.brewdaytwo.domain.model.ingrediente.Ingrediente;
import group.brewdaytwo.domain.model.ricetta.Ricetta;
import group.brewdaytwo.services.dao.attrezzo.AttrezzoDAO;
import group.brewdaytwo.services.dao.ingrediente.IngredienteDAO;
import group.brewdaytwo.services.dao.ricetta.RicettaDAO;

@Controller
public class ControllerRicette {
	
	@Autowired
	private RicettaDAO ricettaDAO;
	
	@Autowired
	private IngredienteDAO ingredienteDAO;
	
	@Autowired
	private AttrezzoDAO attrezzoDAO;
	
	public static String decodeRicerca(String input) 
	{
		input = input.replace("%27", "'");
		input = input.replace("%21", "!");
		input = input.replace("%3F", "?");
		input = input.replace("%28", "(");
		input = input.replace("%29", ")");
		input = input.replace("%C3%83%C2%AC", "ì");
		input = input.replace("%C3%83%C2%A8", "è");
		input = input.replace("%C3%83%C2%A9", "é");
		input = input.replace("%C3%83%C2%B9", "ù");
		input = input.replace("%C3%83%C2%A0", "à");
		input = input.replace("%C3%83%C2%B2", "ò");
		input = input.replace("%3A", ":");
		input = input.replace("%2C", ",");
		input = input.replace("%3B", ";");
		
		input = input.replace("%60", "`");
		input = input.replace("%7E", "~");
		input = input.replace("%7D", "}");
		input = input.replace("%7B", "{");
		input = input.replace("%5C", "\\");
		input = input.replace("%7C", "|");
		input = input.replace("%2F", "/");
		input = input.replace("%24", "$");
		input = input.replace("%C3%82%C2%A3", "£");
		input = input.replace("%5E", "^");
		input = input.replace("%5B", "[");
		input = input.replace("%5D", "]");
		input = input.replace("%2B", "+");
		input = input.replace("%3C", "<");
		input = input.replace("%3E", ">");
		input = input.replace("%C3%83%C2%A7", "ç");
		input = input.replace("%40", "@");
		input = input.replace("%C3%82%C2%B0", "°");
		input = input.replace("%23", "#");
		input = input.replace("%C3%82%C2%A7", "§");
		
		input = input.replace("%22", "");
		return input;
	}
	
	public String decodeInserimento(String input) 
	{
		input = input.replace("Ã¬", "ì");
		input = input.replace("Ã¨", "è");
		input = input.replace("Ã©", "é");
		input = input.replace("Ã¹", "ù");
		input = input.replace("Ã ", "à");
		input = input.replace("Ã²", "ò");
		input = input.replace("Â£", "£");
		input = input.replace("Ã§", "ç");
		input = input.replace("Â°", "°");
		input = input.replace("Â§", "§");
		input = input.replace("\"", "");
		return input;
	}

	@GetMapping(value="/Addrecipes")
	public ModelAndView loadRecipesPage(ModelAndView model){
		model.setViewName("recipesAddPage");
		return model;
	}
	
	@GetMapping(value="/editRecipe")
	public ModelAndView editRecipe(HttpServletRequest request){
		int recipeID = Integer.parseInt(request.getParameter("id"));
		Ricetta r = ricettaDAO.get(recipeID);
		List<String> listRecComponents = ingredienteDAO.getComponents(recipeID);
		List<String> listRecTools = attrezzoDAO.getTools(recipeID);
		ModelAndView model = new ModelAndView("editRecipePage");
		model.addObject("Ricetta", r);
		model.addObject("listRecComponents", listRecComponents);
		model.addObject("listRecTools", listRecTools);
		return model;
	}
	
	@GetMapping(value="/deleteRecipe")
	public ModelAndView deleteRecipe(HttpServletRequest request){
		int recipeID = Integer.parseInt(request.getParameter("id"));
		ricettaDAO.delete(recipeID);
		return new ModelAndView("recipesPage");
	}
	
	@GetMapping(value="/modifyRecipe")
	public ModelAndView modifyRecipe(HttpSession session,HttpServletRequest request){
		int recipeID = Integer.parseInt(request.getParameter("id"));
		session.setAttribute("ricettaID", recipeID);
		Ricetta r = ricettaDAO.get(recipeID);
		ModelAndView model = new ModelAndView("modifyInfoRecipePage");
		model.addObject("Ricetta", r);
		return model;
	}
	
	@PostMapping(value="/modifyInfoRecipe")
	public ModelAndView modifyInfoRecipe(@ModelAttribute Ricetta r) {
		
		r.setNome(decodeInserimento(r.getNome()));
		r.setProcedimento(decodeInserimento(r.getProcedimento()));
		r.setDescrizione(decodeInserimento(r.getDescrizione()));
		
		ricettaDAO.update(r);
		
		List<Ingrediente> listMalto = ingredienteDAO.list("Malto");
		List<Ingrediente> listZucchero = ingredienteDAO.list("Zucchero");
		List<Ingrediente> listLuppolo = ingredienteDAO.list("Luppolo");
		List<Ingrediente> listLievito = ingredienteDAO.list("Lievito");
		List<Ingrediente> listAdditivo = ingredienteDAO.list("Additivo");
		
		List<String> listRecComponents = ingredienteDAO.getComponents(r.getID());
		
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
		request = request.replace("+", " ");
		String[] values = request.split("&");
		String ricetta = values[values.length-1].substring(values[values.length-1].lastIndexOf("=") + 1);
		String comp="";
		ingredienteDAO.deleteComponent(ricetta);
		for (int i = 0; i < values.length-1; ++i)
		  {
		      if(values[i].contains("comp"))
		        comp = values[i].substring(values[i].lastIndexOf("=") + 1);
		      else
		    	  ingredienteDAO.saveComponent(ricetta,comp, Double.parseDouble(values[i].substring(values[i].lastIndexOf("=") + 1)));
		  }
		ModelAndView model = new ModelAndView("modifyToolsRecipePage");
		
		List<Attrezzo> listAttrezzi = attrezzoDAO.list(true);
		List<String> listRecTools = attrezzoDAO.getTools(Integer.parseInt(ricetta));
		
		model.addObject("listRecTools", listRecTools);
		model.addObject("listAttrezzi", listAttrezzi);
		
		return model;
	}
	
	@PostMapping(value="/modifyToolsRecipe")
	public ModelAndView modifyToolsRecipe(@RequestBody String request) {
		String[] values = request.split("&");
		String ricetta = values[values.length-1].substring(values[values.length-1].lastIndexOf("=") + 1);
		String eqp="";
		attrezzoDAO.deleteRecTool(ricetta);
		for (int i = 0; i < values.length-1; ++i)
		  {
		      if(values[i].contains("eqp"))
		        eqp = values[i].substring(values[i].lastIndexOf("=") + 1);
		      else
		    	attrezzoDAO.saveRecEquipment(ricetta,eqp, Integer.parseInt(values[i].substring(values[i].lastIndexOf("=") + 1)));
		  }
		return new ModelAndView("redirect:/recipes");
	}
	
	@PostMapping(value="/showrecipes")
	public ModelAndView showRecipes(@RequestBody String request){
		ModelAndView model = new ModelAndView("recipesPage");
		request = request.replace("+", " ");
		request = decodeRicerca(request);
		String[] values = request.split("&");
		String nome = values[0].split("=")[1].replace("%26","&");
		String autore = values[1].split("=")[1];
		List<Ricetta> listRicette = ricettaDAO.list(nome,autore);
		model.addObject("listRicette", listRicette);
		return model;
	}
	
	@PostMapping(value="/showrecipesMake")
	public ModelAndView showRecipesMake(@RequestBody String request){
		ModelAndView model = new ModelAndView("showRecBeerPage");
		request = request.replace("+", " ");
		request = decodeRicerca(request);
		String[] values = request.split("&");
		String nome = values[0].split("=")[1].replace("%26","&");
		String autore = values[1].split("=")[1];
		List<Ricetta> listRicette = ricettaDAO.list(nome,autore);
		model.addObject("listRicette", listRicette);
		return model;
	}
	
	@PostMapping(value="/Addrecipes")
	public ModelAndView saveRicetta(HttpSession session,@ModelAttribute Ricetta r) {
		
		r.setNome(decodeInserimento(r.getNome()));
		r.setProcedimento(decodeInserimento(r.getProcedimento()));
		r.setDescrizione(decodeInserimento(r.getDescrizione()));
		
		int ricettaID = ricettaDAO.save(r);
		
		List<Ingrediente> listMalto = ingredienteDAO.list("Malto");
		List<Ingrediente> listZucchero = ingredienteDAO.list("Zucchero");
		List<Ingrediente> listLuppolo = ingredienteDAO.list("Luppolo");
		List<Ingrediente> listLievito = ingredienteDAO.list("Lievito");
		List<Ingrediente> listAdditivo = ingredienteDAO.list("Additivo");
		
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
		request = request.replace("+", " ");
		String[] values = request.split("&");
		String ricetta = values[values.length-1].substring(values[values.length-1].lastIndexOf("=") + 1);
		String comp="";
		for (int i = 0; i < values.length-1; ++i)
		  {
		      if(values[i].contains("comp"))
		        comp = values[i].substring(values[i].lastIndexOf("=") + 1);
		      else
		    	ingredienteDAO.saveComponent(ricetta,comp, Double.parseDouble(values[i].substring(values[i].lastIndexOf("=") + 1)));
		  }
		ModelAndView model = new ModelAndView("recipesAddEquipments");
		List<Attrezzo> listAttrezzi = attrezzoDAO.list(true);
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
		    	attrezzoDAO.saveRecEquipment(ricetta,eqp, Integer.parseInt(values[i].substring(values[i].lastIndexOf("=") + 1)));
		  }
		return new ModelAndView("redirect:/recipes");
	}

}
