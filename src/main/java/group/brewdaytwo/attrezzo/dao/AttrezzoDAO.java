package group.brewdaytwo.attrezzo.dao;

import java.util.List;

import group.brewdaytwo.attrezzo.model.Attrezzo;

public interface AttrezzoDAO {

	public List<Attrezzo> list(boolean b);
	
	public void saveRecEquipment(String r, String id, int q);
	
	public void deleteRecTool(String r);
	
	public List<String> getUserTools(String utente);
	
	public void saveUserTool(String u, String id, int q);
	
	public void deleteUserTool(String u);
}
