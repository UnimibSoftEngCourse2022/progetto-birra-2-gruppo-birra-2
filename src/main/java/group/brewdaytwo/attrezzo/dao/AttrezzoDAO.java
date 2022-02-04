package group.brewdaytwo.attrezzo.dao;

import java.util.List;

import group.brewdaytwo.attrezzo.model.Attrezzo;

public interface AttrezzoDAO {

	public List<Attrezzo> list();
	
	public void saveRecEquipment(String r, String id, int q);
	
}
