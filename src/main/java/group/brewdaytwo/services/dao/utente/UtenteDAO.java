package group.brewdaytwo.services.dao.utente;

import group.brewdaytwo.domain.model.utente.Utente;

public interface UtenteDAO {
	
	public void save(Utente utente);
	
	public void update(Utente utente);
	
	public void delete(String utenteNick);
	
	public Utente check(String utenteNick,String utentePwd);
}