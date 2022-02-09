package group.brewdaytwo.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import group.brewdaytwo.attrezzo.dao.AttrezzoDAO;
import group.brewdaytwo.attrezzo.model.Attrezzo;
import group.brewdaytwo.ingrediente.dao.IngredienteDAO;
import group.brewdaytwo.ingrediente.model.Ingrediente;

@Controller
public class ControllerHome {
	
	@Autowired
	private IngredienteDAO IngredienteDAO;
	
	@Autowired
	private AttrezzoDAO AttrezzoDAO;
	
	@GetMapping(value="/recipes")
	public ModelAndView loadRecipesPage(ModelAndView model) throws IOException{
		model.setViewName("recipesPage");
		return model;
	}
	
	@GetMapping(value="/editUserIng")
	public ModelAndView loadUserIngPage(ModelAndView model,HttpServletRequest request) throws IOException{
		model.setViewName("userIngPage");
		
		String autore = request.getParameter("nick");
		
		List<Ingrediente> listMalto = IngredienteDAO.list("Malto");
		List<Ingrediente> listZucchero = IngredienteDAO.list("Zucchero");
		List<Ingrediente> listLuppolo = IngredienteDAO.list("Luppolo");
		List<Ingrediente> listLievito = IngredienteDAO.list("Lievito");
		List<Ingrediente> listAdditivo = IngredienteDAO.list("Additivo");
		
		List<String> listUserIngredients = IngredienteDAO.getUserIngredients(autore);
		
		model.addObject("listMalto", listMalto);
		model.addObject("listZucchero", listZucchero);
		model.addObject("listLuppolo", listLuppolo);
		model.addObject("listLievito", listLievito);
		model.addObject("listAdditivo", listAdditivo);
		
		model.addObject("listUserIngredients", listUserIngredients);
		
		
		return model;
	}
	
	@GetMapping(value="/editUserEquip")
	public ModelAndView loadUserEquipPage(ModelAndView model,HttpServletRequest request) throws IOException{
		model.setViewName("userEquipPage");
		
		String autore = request.getParameter("nick");
		
		List<Attrezzo> listAttrezzi = AttrezzoDAO.list();
		List<String> listUserTools = AttrezzoDAO.getUserTools(autore);
		
		model.addObject("listUserTools", listUserTools);
		model.addObject("listAttrezzi", listAttrezzi);
		
		
		return model;
	}
	
	@GetMapping(value="/logout")
	public ModelAndView loadFirstPage(HttpSession session,ModelAndView model) throws IOException{
		session.invalidate();
		model.setViewName("firstPage");
		return model;
	}
}
