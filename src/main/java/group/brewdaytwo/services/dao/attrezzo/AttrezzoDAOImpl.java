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
		return jdbcTemplate.query(sql, new RowMapper<Attrezzo>() {

			@Override
			public Attrezzo mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Attrezzo(rs.getInt("ID"), rs.getString("nome"),rs.getDouble("capacita_max"));
			}
		});
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
		String[] args = {utente};
		String sql = "SELECT tools.ID,tools.nome,quantita,capacita_max as capMax FROM brewers_equipments join tools on strumento=tools.ID where birraio = ?";
		return jdbcTemplate.query(sql,args, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt("ID") + " - " + rs.getString("nome") + " - " + rs.getInt("quantita") + " - " + rs.getDouble("capMax");
			}
		});
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
		Integer[] args= {ricettaID};
		String sql = "SELECT tools.ID,tools.nome,quantita FROM recipes_equipments join tools on strumento=tools.ID where ricetta = ?";
		return jdbcTemplate.query(sql,args, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt("ID") + " - " + rs.getString("nome") + " - " + rs.getInt("quantita");
			}
		});
	}
	
	@Override
	public int getNumAtt(String attrezzo, double q, String autore) {
		String qstr = q +"";
		String[] args = {attrezzo, autore, qstr};
		String sql = "SELECT SUM(quantita) FROM tools JOIN brewers_equipments ON strumento=tools.id  WHERE nome= ? AND birraio=? AND capacita_max >= ?"; 
		int n;
		if(jdbcTemplate.queryForObject(sql, args, Integer.class) == null) {
			n=0; 
		}else {
			n = jdbcTemplate.queryForObject(sql,args, Integer.class);
		}
			return n; 
	}

}
