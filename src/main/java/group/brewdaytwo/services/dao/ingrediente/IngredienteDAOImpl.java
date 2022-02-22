package group.brewdaytwo.services.dao.ingrediente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import group.brewdaytwo.domain.model.ingrediente.Ingrediente;

public class IngredienteDAOImpl implements IngredienteDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public IngredienteDAOImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Ingrediente> list(String tipo) {
		String[] args = {tipo};
		String sql = "SELECT * FROM ingredients where tipo=?";
		List<Ingrediente> listIngrediente = jdbcTemplate.query(sql,args, new RowMapper<Ingrediente>() {

			@Override
			public Ingrediente mapRow(ResultSet rs, int rowNum) throws SQLException {
				Ingrediente ingrediente = new Ingrediente(rs.getString("nome"),rs.getString("tipo"));
				return ingrediente;
			}
		});
		return listIngrediente;
	}
	
	@Override
	public void saveComponent(String r, String i, double q){
		String sql = "INSERT INTO progetto_brewday.components (ricetta, ingrediente, quantita)"
					+ " VALUES (?, ?, ?)";
		jdbcTemplate.update(sql, r, i,q);
	}
	@Override
	public void deleteComponent(String r) {
		String sql = "DELETE FROM progetto_brewday.components WHERE ricetta = ?";
		jdbcTemplate.update(sql, r);
	}
	
	@Override
	public List<String> getUserIngredients(String utente)
		{	
			String[] args = {utente};
			String sql = "SELECT ingrediente,quantita,tipo FROM warehouses join ingredients on ingrediente=nome where birraio = ?";
			List<String> ingredients = jdbcTemplate.query(sql,args, new RowMapper<String>() {
	
				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					String i = rs.getString("ingrediente") + " - " + rs.getDouble("quantita") + " - " + rs.getString("tipo");
					return i;
				}
			});
			return ingredients;
		}
	
	@Override
	public void saveUserIng(String u, String i, double q)
		{
		String sql = "INSERT INTO progetto_brewday.warehouses (birraio, ingrediente, quantita)"
				+ " VALUES (?, ?, ?)";
		jdbcTemplate.update(sql, u, i,q);
		}
	
	@Override
	public void deleteUserIng(String u){
		String sql = "DELETE FROM progetto_brewday.warehouses WHERE birraio = ?";
		jdbcTemplate.update(sql, u);
	}
	
	@Override
	public List<String> getComponents(int ricettaID) {
		String[] args = {ricettaID+""};
		String sql = "SELECT ingrediente,quantita,tipo FROM components join ingredients on ingrediente=nome where ricetta = ?";
		List<String> components = jdbcTemplate.query(sql,args, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String c = rs.getString("ingrediente") + " - " + rs.getDouble("quantita") + " - " + rs.getString("tipo");
				return c;
			}
		});
		return components;
	}
	
	@Override
	public void deleteOneUserIng(String u, String ing) {
		String sql = "DELETE FROM progetto_brewday.warehouses WHERE birraio=? AND ingrediente=?";
		jdbcTemplate.update(sql, u, ing);
	}
	
	@Override
	public String getTipo(String nome) {
		String[] args = {nome};
		String sql ="select tipo from ingredients where nome=?";
		return jdbcTemplate.query(sql,args, new ResultSetExtractor<String>() {

			@Override
			public String extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				if (rs.next()) {
					String t = rs.getString("tipo");
					return t;
				}
				return "";
			}
		});
	}
	
	
}
