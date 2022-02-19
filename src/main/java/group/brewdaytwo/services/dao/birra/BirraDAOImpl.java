package group.brewdaytwo.services.dao.birra;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import group.brewdaytwo.domain.model.birra.Birra; 
import group.brewdaytwo.services.dao.ingrediente.IngredienteDAO;
import group.brewdaytwo.services.dao.ricetta.RicettaDAO;
import group.brewdaytwo.services.dao.attrezzo.AttrezzoDAO;

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
	public List<String> getBirre(String autore) {
		String sql = "SELECT nome,quantita_prodotta as quantita,note FROM progetto_brewday.brews join recipes on ricetta=recipes.id WHERE birraio=\"" + autore + "\"";
		List<String> listBirre = jdbcTemplate.query(sql, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String b = rs.getString("nome") + " - " + rs.getString("quantita") + " - " + rs.getString("note");
				return b;
			}
		});
		
		return listBirre;
	}
	
	
	@Override
	public List<String> controlloCreaBirra(int IDR, double q, String autore) {
		
		List<String>ingredienti = IngredienteDAO.getComponents(IDR); 
		List<String>ingMagazzino = IngredienteDAO.getUserIngredients(autore);
		List<String> c = new ArrayList<String>(); 
		List<String> h = new ArrayList<String>();
		List<Double> quant = new ArrayList<Double>();
		List<Double> quantMag = new ArrayList<Double>();
		List<String>attrezziRicetta = AttrezzoDAO.getTools(IDR);  
		List<Integer> auQ = new ArrayList<Integer>();	
		List<String> attrezziSpesa = new ArrayList<String>();
		List<Integer> attrezziSpesaQ = new ArrayList<Integer>();
		List<String> am = new ArrayList<String>();
		List<Integer> amQ = new ArrayList<Integer>();
		
		List<String> ingredientiMancanti = new ArrayList<String>();
		List<Double> ingredientiMancantiQ = new ArrayList<Double>();
		List<String> ingInsuff = new ArrayList<String>();
		List<Double> ingInsuffQ = new ArrayList<Double>();
		
		
		for(int i=0; i<attrezziRicetta.size(); i++) {
			am.add(attrezziRicetta.get(i).split(" - ")[1]);
			int tmp = Integer.parseInt(attrezziRicetta.get(i).split(" - ")[2]); 
			amQ.add(tmp); 
			auQ.add(AttrezzoDAO.getNumAtt(am.get(i), q));
			if(amQ.get(i)> auQ.get(i) ) {
				attrezziSpesa.add(am.get(i)); 
				attrezziSpesaQ.add(amQ.get(i) - auQ.get(i)); 
			}
			
			
		}
		
		for(int i =0; i< ingredienti.size(); i++) {
			c.add(ingredienti.get(i).split(" - ")[0]); 
			
			if(ingredienti.get(i).split(" - ")[0].equals("Acqua")) {
				
				Double tmp = Double.parseDouble(ingredienti.get(i).split("-")[1]) * q;
				quant.add(tmp/100);
 
			}else {
				
				Double tmp = Double.parseDouble(ingredienti.get(i).split("-")[1]) * q;
				quant.add(tmp);
			}
		}
		
		
		for(int j=0; j< ingMagazzino.size(); j++) {
			h.add(ingMagazzino.get(j).split(" - ")[0]); 
			quantMag.add(Double.parseDouble(ingMagazzino.get(j).split("-")[1]));
		}

		
		for(int i=0; i<c.size(); i++) {
			if(!h.contains(c.get(i))) {
				ingredientiMancanti.add(c.get(i)); 
				ingredientiMancantiQ.add(quant.get(i)); 
			}
		}
		
			for(int i=0; i<c.size(); i++) {
				for(int j=0; j<h.size(); j++) {
					if(c.get(i).equals(h.get(j)) && quantMag.get(j)<quant.get(i)) {
						ingInsuff.add(c.get(i));
						ingInsuffQ.add(quant.get(i) - quantMag.get(j));
					}
				}
			}
			
		List<String> spesa = new ArrayList<String>();
		
		if(attrezziSpesa.size()==0 && ingredientiMancanti.size()==0 && ingInsuff.size()==0) {
			
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
			
		}else {
		
			
			boolean flagIng = true;
			for(int i=0; i< attrezziSpesa.size(); i++) {
				if(i == 0)
					spesa.add("Attrezzatura");
				spesa.add("Nome attrezzo mancante: " + attrezziSpesa.get(i) + " Numero minimo richiesto: " + attrezziSpesaQ.get(i)+ " Capacità minima richiesta:  " + q + "l"); 
				
			}
			
			for(int i=0; i< ingredientiMancanti.size(); i++) {
				if(i == 0)
					{
					spesa.add("Ingredienti");
					flagIng=false;
					}
				if(ingredientiMancanti.get(i).contains("Acqua"))
					spesa.add("Nome ingrediente mancante: " + ingredientiMancanti.get(i) + " Quantità minima richiesta: " + ingredientiMancantiQ.get(i) + " l");
				else
					spesa.add("Nome ingrediente mancante: " + ingredientiMancanti.get(i) + " Quantità minima richiesta: " + ingredientiMancantiQ.get(i) + " g"); 
			}
			
			for(int i=0; i< ingInsuff.size(); i++) {
				if(i == 0 && flagIng)
					spesa.add("Ingredienti");
				if(ingInsuff.get(i).contains("Acqua"))
					spesa.add("Nome ingrediente mancante: " + ingInsuff.get(i) + " Quantità minima richiesta: " + ingInsuffQ.get(i) + " l");
				else
					spesa.add("Nome ingrediente mancante: " + ingInsuff.get(i) + " Quantità minima richiesta: " + ingInsuffQ.get(i) + " g"); 
			}
		}
		return spesa;

	}
}
