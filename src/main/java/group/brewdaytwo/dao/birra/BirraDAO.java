package group.brewdaytwo.dao.birra;

import java.util.List;

import group.brewdaytwo.model.birra.Birra; 

public interface BirraDAO {
	
	public void save(Birra b);
	
	public void delete(int IDBirra);
	
	public List<String> getBirre(String autore);
	
	public List<String> controlloCreaBirra(int IDRicetta, double quantità, String autore);
	
}
