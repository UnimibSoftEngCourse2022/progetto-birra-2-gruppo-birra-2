package group.brewdaytwo.utente.dao;

import group.brewdaytwo.utente.model.Utente;

public interface UtenteDAO {
	
	public void save(Utente utente);
	
	public void update(Utente utente);
	
	public void delete(String utenteNick);
	
	public Utente check(String utenteNick,String utentePwd);
}