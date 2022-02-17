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
		List<Integer> auQ = new ArrayList<Integer>();
		List<Integer> amQ = new ArrayList<Integer>();
		
		List<String> attrezziSpesa = new ArrayList<String>();
		List<Integer> num = new ArrayList<Integer>();
		List<String> ingredientiMancanti = new ArrayList<String>();
		List<String> ingInsuff = new ArrayList<String>();
		List<Double> ingInsuffQ = new ArrayList<Double>();
		
		
		for(int i=0; i<attrezziUtente.size(); i++) {
			au.add(attrezziUtente.get(i).split(" - ")[1]);
			auQ.add(Integer.parseInt(attrezziUtente.get(i).split(" - ")[2])); 
		}
	
		for(int i=0; i<attrezziRicetta.size(); i++) {
			am.add(attrezziRicetta.get(i).split(" - ")[1]);
			int tmp = Integer.parseInt(attrezziRicetta.get(i).split(" - ")[2]); 
			amQ.add(tmp); 
		}
		
		
		for(int i=0; i<am.size(); i++) {
			if(!au.contains(am.get(i))) {
				attrezziSpesa.add(am.get(i)); 
			}
		}
		
		if(attrezziSpesa.size()==0) {
			for(int i=0; i<am.size(); i++) {
				for(int j=0; j<au.size(); j++) {
					if(am.get(i).equals(au.get(j)) && auQ.get(j)<amQ.get(i)) {
						attrezziSpesa.add(am.get(i));
						num.add(amQ.get(i) - auQ.get(j));
					}
				}
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
			}
		}
		
		
		if(ingredientiMancanti.size()==0) {
			for(int i=0; i<c.size(); i++) {
				for(int j=0; j<h.size(); j++) {
					if(c.get(i).equals(h.get(j)) && quantMag.get(j)<quant.get(i)) {
						ingInsuff.add(c.get(i));
						ingInsuffQ.add(quant.get(i) - quantMag.get(j));
					}
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
				spesa.add(num.get(i) + " " + attrezziSpesa.get(i) + " che supporti almeno " + q + "l"); 
				
			}
			
			for(int i=0; i< ingredientiMancanti.size(); i++) {
				if(i == 0)
					{spesa.add("Ingredienti");
					flagIng=false;
					}
				if(ingredientiMancanti.get(i).contains("Acqua"))
					spesa.add(ingredientiMancanti.get(i) + " di almeno " + q + "l");
				else
					spesa.add(ingredientiMancanti.get(i) + " di almeno " + q + "g"); 
			}
			
			for(int i=0; i< ingInsuff.size(); i++) {
				if(i == 0 && flagIng)
					spesa.add("Ingredienti");
				if(ingInsuff.get(i).contains("Acqua"))
					spesa.add(ingInsuff.get(i) + " di almeno " + ingInsuffQ.get(i) + "l");
				else
					spesa.add(ingInsuff.get(i) + " di almeno " + ingInsuffQ.get(i) + "g"); 
			}
		}
		return spesa;

	}
}
