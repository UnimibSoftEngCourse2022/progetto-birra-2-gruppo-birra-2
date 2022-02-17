package group.brewdaytwo.dao.birra;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import group.brewdaytwo.model.birra.Birra; 
import group.brewdaytwo.ingrediente.dao.IngredienteDAO;
import group.brewdaytwo.ricetta.dao.RicettaDAO;
import group.brewdaytwo.attrezzo.dao.AttrezzoDAO;

public class BirraDAOImpl implements BirraDAO{
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private RicettaDAO RicettaDAO; 
	
	@Autowired
	private IngredienteDAO IngredienteDAO; 
	
	@Autowired
	private AttrezzoDAO AttrezzoDAO; 
	
	public BirraDAOImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	
	@Override
	public void save(Birra b) {
		String sql = "INSERT INTO progetto_brewday.brews (note, quantita_prodotta, birraio, ricetta)"
				+ " VALUES (?, ?, ?,?)";
		jdbcTemplate.update(sql, b.getNote(), b.getQuantita(), b.getAutore(), b.getIDRicetta());
	}

	
	@Override
	public void delete(int IDBirra) {
		String sql = "DELETE FROM progetto_brewday.brews WHERE ID=?";
		jdbcTemplate.update(sql, IDBirra);
	}
	
	
	@Override
	public List<Birra> getBirre(String autore) {
		String sql = "SELECT * FROM progetto_brewday.brews WHERE birraio=\"" + autore + "\"";
		List<Birra> listBirre = jdbcTemplate.query(sql, new RowMapper<Birra>() {

			@Override
			public Birra mapRow(ResultSet rs, int rowNum) throws SQLException {
				Birra b = new Birra(rs.getInt("ID"), rs.getString("note"), rs.getDouble("quantita_prodotta"), autore ,rs.getInt("ricetta"));
				return b;
			}
		});
		
		return listBirre;
	}
	
	
	@Override
	public boolean controlloCreaBirra(int IDR, double q, String autore) {
		
		List<String>ingredienti = RicettaDAO.getComponents(IDR); 
		List<String>ingMagazzino = IngredienteDAO.getUserIngredients(autore);
		List<String> c = new ArrayList<String>(); 
		List<String> h = new ArrayList<String>();
		List<Double> quant = new ArrayList<Double>();
		List<Double> quantMag = new ArrayList<Double>();
		
		List<String>attrezziUtente = AttrezzoDAO.getUserTools(autore);
		List<String>attrezziRicetta = RicettaDAO.getTools(IDR); 
		List<String> au = new ArrayList<String>(); 
		List<String> am = new ArrayList<String>();
		List<Double> auQ = new ArrayList<Double>();
		List<Double> amQ = new ArrayList<Double>();
		
		//Lista della spesa
		List<String> attrezziSpesa = new ArrayList<String>();
		List<String> ingredientiMancanti = new ArrayList<String>();
		List<String> ingInsuff = new ArrayList<String>();
		List<Double> ingInsuffQ = new ArrayList<Double>();
		
		
		//GESTIONE ATTREZZI 
		for(int i=0; i<attrezziUtente.size(); i++) {
			au.add(attrezziUtente.get(i).split(" - ")[1]);
			auQ.add(Double.parseDouble(attrezziUtente.get(i).split("-")[2])); 
		}
	
		for(int i=0; i<attrezziRicetta.size(); i++) {
			am.add(attrezziRicetta.get(i).split(" - ")[1]);
			double tmp = Double.parseDouble(attrezziRicetta.get(i).split("-")[2]) * q; 
			amQ.add(tmp); 
		}
		
		
		//controllo
		for(int i=0; i<am.size(); i++) {
			System.out.println(!au.contains(am.get(i))); 
			if(!au.contains(am.get(i))) {
				System.out.println("Non devo stampareAttrezzi"); 
				attrezziSpesa.add(am.get(i)); 
			}
		}
		
		
		if(attrezziSpesa.size()==0) {
			for(int i=0; i<am.size(); i++) {
				for(int j=0; j<au.size(); j++) {
					if(am.get(i).equals(au.get(j)) && auQ.get(j)<amQ.get(i)) {
						attrezziSpesa.add(am.get(i));
					}
				}
			}
		}
	
		//GESTIONE INGREDIENTI
		//Inserisco solo il nome e la quantità che identifica gli ingredienti nell'arraylist.
		for(int i =0; i< ingredienti.size(); i++) {
			c.add(ingredienti.get(i).split(" - ")[0]); 
			Double tmp = Double.parseDouble(ingredienti.get(i).split("-")[1]) * q;
			quant.add(tmp);
		}
		
		
		//Inserisco Nome e Quantità dell'ingredienti in possesso dall'utente. 
		for(int j=0; j< ingMagazzino.size(); j++) {
			h.add(ingMagazzino.get(j).split(" - ")[0]); 
			quantMag.add(Double.parseDouble(ingMagazzino.get(j).split("-")[1]));
		}

		//Controllo
		for(int i=0; i<c.size(); i++) {
			if(!h.contains(c.get(i))) {
				System.out.println("Non devo stampare"); 
				ingredientiMancanti.add(c.get(i)); 
			}
		}
		
		
		if(ingredientiMancanti.size()==0) {
			for(int i=0; i<c.size(); i++) {
				for(int j=0; j<h.size(); j++) {
					if(c.get(i).equals(h.get(j)) && quantMag.get(j)<quant.get(i)) {
						System.out.println("NON DEVO STAMPARE"); 
						ingInsuff.add(c.get(i));
						ingInsuffQ.add(quant.get(i) - quantMag.get(j));
					}
				}
			}
		}

		if(attrezziSpesa.size()==0 && ingredientiMancanti.size()==0 && ingInsuff.size()==0) {
			
			System.out.println("TUTTO APPOSTO"); 
			
			for(int i=0; i<c.size(); i++) {
				for(int j=0; j<h.size(); j++) {
					if(c.get(i).equals(h.get(j))) {
						IngredienteDAO.deleteOneUserIng(autore, h.get(i));
						
						if(quantMag.get(j) - quant.get(i) > 0) {
							IngredienteDAO.saveUserIng(autore, c.get(i), quantMag.get(j) - quant.get(i));
						}
					}
				}
			}
			
			return true;
			
		}else {

			return false; 
		}
		

	}

	
	
	
}
