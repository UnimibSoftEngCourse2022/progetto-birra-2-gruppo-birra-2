package group.brewdaytwo.services.dao.ricetta;

import java.util.List;

import group.brewdaytwo.domain.model.ricetta.Ricetta;

public interface RicettaDAO {
	
	public int save(Ricetta ricetta);
	
	public void update(Ricetta ricetta);
	
	public void delete(int ricettaID);
	
	public Ricetta get(int ricettaID);
	
	public List<Ricetta> list(String nome,String autore);
	
	public Ricetta getCDPO(String autore);

}
