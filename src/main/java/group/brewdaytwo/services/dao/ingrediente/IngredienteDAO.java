package group.brewdaytwo.services.dao.ingrediente;

import java.util.List;

import group.brewdaytwo.domain.model.ingrediente.Ingrediente;

public interface IngredienteDAO {
	
	public List<Ingrediente> list(String tipo);
	
	public void saveComponent(String r, String i, double q);
	
	public void deleteComponent(String r);
	
	public List<String> getComponents(int ricettaID);
	
	public List<String> getUserIngredients(String utente);
	
	public void saveUserIng(String u, String i, double q);
	
	public void deleteUserIng(String u);
	
	public void deleteOneUserIng(String u, String ing);
	
	public String getTipo(String nome);

}
