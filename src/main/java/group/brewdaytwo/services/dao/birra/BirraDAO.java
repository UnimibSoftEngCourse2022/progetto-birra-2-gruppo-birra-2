package group.brewdaytwo.services.dao.birra;

import java.util.List;

import group.brewdaytwo.domain.model.birra.Birra; 

public interface BirraDAO {
	
	public void save(Birra b);
	
	public List<String> getBirre(String autore);
	
	public List<String> controlloCreaBirra(int IDRicetta, double quantità, String autore);
	
}