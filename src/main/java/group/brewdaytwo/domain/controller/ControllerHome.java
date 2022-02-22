package group.brewdaytwo.domain.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import group.brewdaytwo.domain.model.attrezzo.Attrezzo;
import group.brewdaytwo.domain.model.ingrediente.Ingrediente;
import group.brewdaytwo.services.dao.attrezzo.AttrezzoDAO;
import group.brewdaytwo.services.dao.birra.BirraDAO;
import group.brewdaytwo.services.dao.ingrediente.IngredienteDAO;

@Controller
public class ControllerHome {
	
	@Autowired
	private IngredienteDAO ingredienteDAO;
	
	@Autowired
	private AttrezzoDAO attrezzoDAO;
	
	@Autowired 
	private BirraDAO birraDAO;
	
	@GetMapping(value="/recipes")
	public ModelAndView loadRecipesPage(ModelAndView model){
		model.setViewName("recipesPage");
		return model;
	}
	
	@GetMapping(value="/brews")
	public ModelAndView loadBrewsPage(HttpSession session,ModelAndView model){
		model.setViewName("brewsPage");
		String autore = (String)session.getAttribute("autore");
		List<String> listBirre = birraDAO.getBirre(autore);
		model.addObject("listBirre", listBirre);
		return model;
	}
	
	@GetMapping(value="/editUserIng")
	public ModelAndView loadUserIngPage(ModelAndView model,HttpServletRequest request){
		model.setViewName("userIngPage");
		
		String autore = request.getParameter("nick");
		
		List<Ingrediente> listMalto = ingredienteDAO.list("Malto");
		List<Ingrediente> listZucchero = ingredienteDAO.list("Zucchero");
		List<Ingrediente> listLuppolo = ingredienteDAO.list("Luppolo");
		List<Ingrediente> listLievito = ingredienteDAO.list("Lievito");
		List<Ingrediente> listAdditivo = ingredienteDAO.list("Additivo");
		
		List<String> listUserIngredients = ingredienteDAO.getUserIngredients(autore);
		
		model.addObject("listMalto", listMalto);
		model.addObject("listZucchero", listZucchero);
		model.addObject("listLuppolo", listLuppolo);
		model.addObject("listLievito", listLievito);
		model.addObject("listAdditivo", listAdditivo);
		
		model.addObject("listUserIngredients", listUserIngredients);
		
		
		return model;
	}
	
	@GetMapping(value="/editUserEquip")
	public ModelAndView loadUserEquipPage(ModelAndView model,HttpServletRequest request){
		model.setViewName("userEquipPage");
		
		String autore = request.getParameter("nick");
		
		List<Attrezzo> listAttrezzi = attrezzoDAO.list(false);
		List<String> listUserTools = attrezzoDAO.getUserTools(autore);
		
		model.addObject("listUserTools", listUserTools);
		model.addObject("listAttrezzi", listAttrezzi);
		
		
		return model;
	}
	
	@GetMapping(value="/logout")
	public ModelAndView loadFirstPage(HttpSession session,ModelAndView model){
		session.invalidate();
		model.setViewName("firstPage");
		return model;
	}
}
