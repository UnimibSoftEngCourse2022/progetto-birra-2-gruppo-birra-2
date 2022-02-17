package group.brewdaytwo.dao.birra;

import java.util.List;

import group.brewdaytwo.model.birra.Birra; 

public interface BirraDAO {
	
	public void save(Birra b);
	
	public void delete(int IDBirra);
	
	public List<Birra> getBirre(String autore);
	
	public boolean controlloCreaBirra(int IDRicetta, double quantità, String autore);
	
}
