package group.brewdaytwo.services.dao.attrezzo;

import java.util.List;

import group.brewdaytwo.domain.model.attrezzo.Attrezzo;

public interface AttrezzoDAO {

	public List<Attrezzo> list(boolean b);
	
	public void saveRecEquipment(String r, String id, int q);
	
	public void deleteRecTool(String r);
	
	public List<String> getTools(int ricettaID);
	
	public List<String> getUserTools(String utente);
	
	public void saveUserTool(String u, String id, int q);
	
	public void deleteUserTool(String u);
	
	public int getNumAtt(String attrezzo, double q); 
	
}
