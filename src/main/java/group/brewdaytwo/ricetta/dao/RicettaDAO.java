package group.brewdaytwo.ricetta.dao;

import java.util.List;

import group.brewdaytwo.ricetta.model.Ricetta;

public interface RicettaDAO {
	
	public int save(Ricetta ricetta);
	
	public void update(Ricetta ricetta);
	
	public void delete(int ricettaID);
	
	public Ricetta get(int ricettaID);
	
	public List<Ricetta> list(String nome,String autore);
	
	public List<String> getComponents(int ricettaID);
	
	public List<String> getTools(int ricettaID);

}
