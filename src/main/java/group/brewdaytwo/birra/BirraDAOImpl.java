package group.brewdaytwo.birra.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import group.brewdaytwo.birra.model.Birra;
import group.brewdaytwo.ingrediente.dao.IngredienteDAO;
import group.brewdaytwo.ricetta.dao.RicettaDAO;

public class BirraDAOImpl implements BirraDAO{
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private RicettaDAO RicettaDao; 
	
	@Autowired
	private IngredienteDAO IngredienteDao; 
	
	public BirraDAOImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public int save(Birra b) {
		String sql = "INSERT INTO progetto_brewday.brews (ID, note, quantita_prodotta, birraio, ricetta)"
				+ " VALUES (?, ?, ?, ?,?)";
		jdbcTemplate.update(sql, b.getID(), b.getNote(), b.getQuantità(), b.getAutore(), b.getIDRicetta());
		sql = "Select max(ID) as ID from brews where autore=\"" + b.getAutore() + "\"";
		return jdbcTemplate.query(sql, new ResultSetExtractor<Integer>() {

			@Override
			public Integer extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				if (rs.next()) {
					return rs.getInt("ID");
				}
				return 0;
			}
		});
	}
	
	
	
	@Override
	public void update(Birra b) {
		String sql = "UPDATE progetto_brewday.brews SET note=?, quantita_prodotta=?, birraio=? "
				+ "WHERE ID=?";
		jdbcTemplate.update(sql, b.getNote(), b.getQuantità(), b.getAutore(), b.getID());
	}
	
	 
	@Override
	public void delete(int IDBirra) {
		String sql = "DELETE FROM progetto_brewday.brews WHERE ID=?";
		jdbcTemplate.update(sql, IDBirra);
	}
	
	@Override
	public Birra getBirra(int IDBirra) {
		String sql = "SELECT * FROM progetto_brewday.recipes WHERE ID=\"" + IDBirra + "\"";
		return jdbcTemplate.query(sql, new ResultSetExtractor<Birra>() {

			@Override
			public Birra extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				if (rs.next()) {
					Birra b = new Birra(rs.getInt("ID"),rs.getString("note"),rs.getFloat("quantita_prodotta"), rs.getString("birraio"), rs.getInt("ricetta"));
					return b;
				}
				return null;
			}
			
		});
	}
	
	public List<String> getIngredientiMagazzino(String autore){
		String sql = "SELECT ingrediente, quantita FROM warehouses join users on nickname = birraio where nickname = \"" + autore + "\"";
		List<String> ingredienti = jdbcTemplate.query(sql, new RowMapper<String>() {
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String c = rs.getString("ingrediente") + " - " + rs.getDouble("quantita");
				return c;
			}
		});
		
		return ingredienti; 
	}
	
	
	
	@Override
	public boolean creaBirra(int IDR, int q, String autore) {
		
		List<String>ingredienti = RicettaDao.getComponents(IDR); 
		List<String>ingMagazzino = getIngredientiMagazzino(autore);
		List<String> c = new ArrayList<String>(); 
		List<String> h = new ArrayList<String>();
		List<Integer> quant = new ArrayList<Integer>();
		List<Integer> quantMag = new ArrayList<Integer>();
		
		
		//Inserisco solo il nome e la quantità che identifica gli ingredienti nell'arraylist.
		for(int i =0; i< ingredienti.size(); ++i) {
			c.add(ingredienti.get(i).split("-")[0]); 
			Integer tmp = Integer.parseInt(ingredienti.get(i).split("-")[1]) * q;
			quant.add(tmp);
		}
		
		//Inserisco Nome e Quantità dell'ingredienti in possesso dall'utente. 
		for(int j=0; j< ingMagazzino.size(); ++j) {
			h.add(ingMagazzino.get(j).split("-")[0]); 
			quantMag.add(Integer.parseInt(ingMagazzino.get(j).split("-")[1]));
		}
		
		
		//Controllo
		for(int k=0; k<ingMagazzino.size(); ++k) {
			for(int p=0; p<ingredienti.size(); ++p) {
				if(!((ingMagazzino.get(k).contains(ingredienti.get(p)))
						&&(quant.get(k) <= quantMag.get(p)) )) {
					return false; 
				}
			}
		}

		
		
		//rimuovere l'ingrediente dal magazzino e riaggiungerlo con la quantità aggiornata, se 0 non si rimette. 
		
		
		
		return true; 
	}

}
