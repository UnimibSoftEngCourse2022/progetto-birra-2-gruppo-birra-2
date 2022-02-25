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
import group.brewdaytwo.services.dao.attrezzo.AttrezzoDAO;

public class BirraDAOImpl implements BirraDAO{
	
	private JdbcTemplate jdbcTemplate; 
	
	@Autowired
	private IngredienteDAO ingredienteDAO; 
	
	@Autowired
	private AttrezzoDAO attrezzoDAO; 
	
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
		String[] args = {autore};
		String sql = "SELECT nome,quantita_prodotta as quantita,note FROM progetto_brewday.brews join recipes on ricetta=recipes.id WHERE birraio=?";
		return jdbcTemplate.query(sql,args, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("nome") + " - " + rs.getString("quantita") + " - " + rs.getString("note");
			}
		});
	}
	
	
	@Override
	public List<String> controlloCreaBirra(int iDR, double q, String autore) {
		
		List<String>ingredienti = ingredienteDAO.getComponents(iDR); 
		List<String>ingMagazzino = ingredienteDAO.getUserIngredients(autore);
		List<String> c = new ArrayList<>(); 
		List<String> h = new ArrayList<>();
		List<Double> quant = new ArrayList<>();
		List<Double> quantMag = new ArrayList<>();
		List<String>attrezziRicetta = attrezzoDAO.getTools(iDR);  
		List<Integer> auQ = new ArrayList<>();	
		List<String> attrezziSpesa = new ArrayList<>();
		List<Integer> attrezziSpesaQ = new ArrayList<>();
		List<String> am = new ArrayList<>();
		List<Integer> amQ = new ArrayList<>();
		
		List<String> ingredientiMancanti = new ArrayList<>();
		List<Double> ingredientiMancantiQ = new ArrayList<>();
		List<String> ingInsuff = new ArrayList<>();
		List<Double> ingInsuffQ = new ArrayList<>();
		
		
		for(int i=0; i<attrezziRicetta.size(); i++) {
			am.add(attrezziRicetta.get(i).split(" - ")[1]);
			int tmp = Integer.parseInt(attrezziRicetta.get(i).split(" - ")[2]); 
			amQ.add(tmp); 
			auQ.add(attrezzoDAO.getNumAtt(am.get(i), q,autore));
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
			
		List<String> spesa = new ArrayList<>();
		
		if(attrezziSpesa.isEmpty() && ingredientiMancanti.isEmpty() && ingInsuff.isEmpty()) {
			
			for(int i=0; i<c.size(); i++) {
				for(int j=0; j<h.size(); j++) {
					if(c.get(i).equals(h.get(j))) {
						ingredienteDAO.deleteOneUserIng(autore, h.get(i));
						
						if(quantMag.get(j) - quant.get(i) > 0) {
							ingredienteDAO.saveUserIng(autore, c.get(i), Math.round((quantMag.get(j) - quant.get(i)) * 100.0) / 100.0);
						}
					}
				}
			}
			
		}else {
		
			for(int i=0; i< attrezziSpesa.size(); i++) {
				spesa.add(attrezziSpesaQ.get(i)+ " " + attrezziSpesa.get(i)+ " da almeno " + q + "L"); 
			}
			
			for(int i=0; i< ingredientiMancanti.size(); i++) {
				if(ingredientiMancanti.get(i).contains("Acqua"))
					spesa.add(Math.round(ingredientiMancantiQ.get(i) * 100.0)/100.0 + "L di " + ingredientiMancanti.get(i));
				else
					spesa.add(Math.round(ingredientiMancantiQ.get(i) * 100.0)/100.0 + "g di "+ ingredienteDAO.getTipo(ingredientiMancanti.get(i)) + " " + ingredientiMancanti.get(i)); 
			}
			
			for(int i=0; i< ingInsuff.size(); i++) {
				if(ingInsuff.get(i).contains("Acqua"))
					spesa.add(Math.round(ingInsuffQ.get(i) * 100.0)/100.0 + "L di " + ingInsuff.get(i));
				else
					spesa.add(Math.round(ingInsuffQ.get(i) * 100.0)/100.0 + "g di " + ingredienteDAO.getTipo(ingInsuff.get(i)) + " " + ingInsuff.get(i)); 
			}
		}
		return spesa;

	}
}
