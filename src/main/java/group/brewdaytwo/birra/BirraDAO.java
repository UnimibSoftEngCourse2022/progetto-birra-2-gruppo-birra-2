package group.brewdaytwo.birra.dao;

import java.util.List; 

import group.brewdaytwo.birra.model.Birra;
import group.brewdaytwo.ricetta.model.Ricetta; 

public interface BirraDAO {
	
	public int save(Birra birra);
	
	public void update(Birra birra);
	
	public void delete(int IDBirra);
	
	public Birra getBirra(int IDBirra);
	
	public List<String> getIngredientiMagazzino(String autore); 
	
	public boolean creaBirra(int IDRicetta, int quantità, String autore);
	
}
