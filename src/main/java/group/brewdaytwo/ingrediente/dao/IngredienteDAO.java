package group.brewdaytwo.ingrediente.dao;

import java.util.List;
import group.brewdaytwo.ingrediente.model.Ingrediente;

public interface IngredienteDAO {
	
	public List<Ingrediente> list(String tipo);
	
	public void saveComponent(String r, String i, double q);
	
	public void deleteComponent(String r);

}
