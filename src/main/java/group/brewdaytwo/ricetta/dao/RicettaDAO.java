package group.brewdaytwo.ricetta.dao;

import group.brewdaytwo.ricetta.model.Ricetta;

public interface RicettaDAO {
	
	public int save(Ricetta ricetta);
	
	public void update(Ricetta ricetta);
	
	public void delete(int ricettaID);
	
	public Ricetta get(int ricettaID);

}
