package group.brewdaytwo.services.dao.attrezzo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import group.brewdaytwo.domain.model.attrezzo.Attrezzo;

public class AttrezzoDAOImpl implements AttrezzoDAO {
	
private JdbcTemplate jdbcTemplate;
	
	public AttrezzoDAOImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Attrezzo> list(boolean b) {
		String sql = "";
		if(b)
			sql = "SELECT * FROM tools where capacita_max = 0";
		else 
			sql = "SELECT * FROM tools where capacita_max > 0";
		List<Attrezzo> listAttrezzo = jdbcTemplate.query(sql, new RowMapper<Attrezzo>() {

			@Override
			public Attrezzo mapRow(ResultSet rs, int rowNum) throws SQLException {
				Attrezzo attrezzo = new Attrezzo(rs.getInt("ID"), rs.getString("nome"),rs.getDouble("capacita_max"));
				return attrezzo;
			}
		});
		
		return listAttrezzo;
	}

	@Override
	public void saveRecEquipment(String r, String id, int q) {
		String sql = "INSERT INTO progetto_brewday.recipes_equipments (ricetta, strumento, quantita)"
				+ " VALUES (?, ?, ?)";
		jdbcTemplate.update(sql, r, id,q);

	}
	
	@Override
	public void deleteRecTool(String r) {
		String sql = "DELETE FROM progetto_brewday.recipes_equipments WHERE ricetta = ?";
		jdbcTemplate.update(sql, r);
	}
	
	@Override
	public List<String> getUserTools(String utente)
	{
		String sql = "SELECT tools.ID,tools.nome,quantita,capacita_max as capMax FROM brewers_equipments join tools on strumento=tools.ID where birraio = \"" + utente + "\"";
		List<String> tools = jdbcTemplate.query(sql, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String t = rs.getInt("ID") + " - " + rs.getString("nome") + " - " + rs.getInt("quantita") + " - " + rs.getDouble("capMax");
				return t;
			}
		});
		
		return tools;
	}
	
	@Override
	public void saveUserTool(String u, String id, int q) {
		String sql = "INSERT INTO progetto_brewday.brewers_equipments (birraio, strumento, quantita)"
				+ " VALUES (?, ?, ?)";
		jdbcTemplate.update(sql, u, id,q);
		}
	
	@Override
	public void deleteUserTool(String u){
		String sql = "DELETE FROM progetto_brewday.brewers_equipments WHERE birraio = ?";
		jdbcTemplate.update(sql, u);
	}
	
	@Override
	public List<String> getTools(int ricettaID) {
		String sql = "SELECT tools.ID,tools.nome,quantita FROM recipes_equipments join tools on strumento=tools.ID where ricetta = \"" + ricettaID + "\"";
		List<String> tools = jdbcTemplate.query(sql, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String t = rs.getInt("ID") + " - " + rs.getString("nome") + " - " + rs.getInt("quantita");
				return t;
			}
		});
		
		return tools;
	}
	
	@Override
	public int getNumAtt(String attrezzo, double q) {
		String sql = "SELECT SUM(quantita) FROM tools JOIN brewers_equipments ON strumento=tools.id  WHERE nome= \""+attrezzo+"\" AND capacita_max >= \""+ q+ "\""; 
		
		int n; 
		
		if(jdbcTemplate.queryForObject(sql, Integer.class) == null) {
			n=0; 
		}else {
			n = jdbcTemplate.queryForObject(sql, Integer.class);
		}
			//int n = jdbcTemplate.queryForObject(sql, Integer.class); 
			return n; 
	}

}
